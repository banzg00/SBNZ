package com.ftn.sbnz.service;

import com.ftn.sbnz.dto.MeasurementDTO;
import com.ftn.sbnz.model.events.AlarmDeactivatedEvent;
import com.ftn.sbnz.model.events.DecreasedRainEvent;
import com.ftn.sbnz.model.events.MeasuringEvent;
import com.ftn.sbnz.model.events.SeriousMalfunctionAlarm;
import com.ftn.sbnz.model.models.HydroelectricPowerPlant;
import com.ftn.sbnz.model.models.Lake;
import com.ftn.sbnz.repository.Database;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RulesService {

    private final Database database;
    private final KieSession ksessionCep;

    @Autowired
    public RulesService(Database database) {
        this.database = database;
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        ksessionCep = kc.newKieSession("CEPKS");
    }

    public void fireChainRules(MeasurementDTO measurement) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        KieSession ksession = kc.newKieSession("forwardKS");

        setMeasurements(measurement, ksession);

        long k1 = ksession.fireAllRules();

        System.out.println(k1 == 3);

        ksession.dispose();
    }

    public void fireCEPRules(MeasurementDTO measurement) {
        setMeasurements(measurement, ksessionCep);
        Lake lake = database.getLake();

        MeasuringEvent measuringEvent = new MeasuringEvent(measurement.getWindSpeed(), measurement.getWaterSpeed(), measurement.getWaterTemp(), measurement.getWaterLvl(), lake.getId());
        database.getMeasuringEvents().add(measuringEvent);
        ksessionCep.insert(measuringEvent);

        if (measuringEvent.getWaterLvl() < 10 && measuringEvent.getWindSpeed() < 10) {
            ksessionCep.insert(new DecreasedRainEvent(lake.getId()));
        }
        long k1 = ksessionCep.fireAllRules();
        System.out.println(k1);
    }

    private void setMeasurements(MeasurementDTO measurement, KieSession kieSession) {
        for (var t : database.getTurbines()) {
            t.setPressure(measurement.getPressure());
            t.setWaterFlow(measurement.getWaterFlow());
            t.setOverheatingDanger(false);
            kieSession.insert(t);
        }
        Lake lake = database.getLake();
        lake.setWaterLvl(measurement.getWaterLvl());
        lake.setWaterTemp(measurement.getWaterTemp());
        lake.setWaterSpeed(measurement.getWaterSpeed());
        lake.setWindSpeed(measurement.getWindSpeed());
        kieSession.insert(lake);

        HydroelectricPowerPlant h = database.getHydroelectricPowerPlant();
        h.setElectricityProduction(100);
        h.setTurbines(database.getTurbines());
        h.setLake(database.getLake());
        kieSession.insert(h);
    }

    public void alarmResolved() {
        ksessionCep.insert(new SeriousMalfunctionAlarm(database.getHydroelectricPowerPlant().getId()));
        ksessionCep.insert(new AlarmDeactivatedEvent(database.getHydroelectricPowerPlant().getId()));
        long k1 = ksessionCep.fireAllRules();
        ksessionCep.dispose();
        ksessionCep = KieServices.Factory.get().newKieClasspathContainer().newKieSession("CEPKS");
    }
}
