package com.ftn.sbnz.service;

import com.ftn.sbnz.dto.MeasurementDTO;
import com.ftn.sbnz.model.events.AlarmDeactivatedEvent;
import com.ftn.sbnz.model.events.DecreasedRainEvent;
import com.ftn.sbnz.model.events.MeasuringEvent;
import com.ftn.sbnz.model.events.SeriousMalfunctionAlarm;
import com.ftn.sbnz.model.models.HydroelectricPowerPlant;
import com.ftn.sbnz.model.models.Lake;
import com.ftn.sbnz.repository.Database;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RulesService {

    private Database database;
    private KieSession ksessionCep;

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
        KieSession ksession = kc.newKieSession("cepKsession");

        for (var t: database.getTurbines()) {
            t.setPressure(measurement.getPressure());
            t.setWaterFlow(measurement.getWaterFlow());
            t.setOverheatingDanger(false);
            ksession.insert(t);
        }
        Lake lake = database.getLake();
        lake.setWaterLvl(measurement.getWaterLvl());
        lake.setWaterTemp(measurement.getWaterTemp());
        lake.setWaterSpeed(measurement.getWaterSpeed());
        lake.setWindSpeed(measurement.getWindSpeed());
        ksession.insert(lake);

        HydroelectricPowerPlant h = database.getHydroelectricPowerPlant();
        h.setValvesOpened(false);
        h.setElectricityProduction(100);
        h.setGeneratorsOn(false);
        h.setTurbines(database.getTurbines());
        h.setLake(database.getLake());
        ksession.insert(h);

        long k1 = ksession.fireAllRules();

        System.out.println(k1 == 4);
        System.out.println(database.getHydroelectricPowerPlant().isGeneratorsOn());

        ksession.dispose();
    }

    public void fireCEPRules(MeasurementDTO measurement) {

        for (var t: database.getTurbines()) {
            t.setPressure(measurement.getPressure());
            t.setWaterFlow(measurement.getWaterFlow());
            t.setOverheatingDanger(false);
            ksessionCep.insert(t);
        }
        Lake lake = database.getLake();
        lake.setWaterLvl(measurement.getWaterLvl());
        lake.setWaterTemp(measurement.getWaterTemp());
        lake.setWaterSpeed(measurement.getWaterSpeed());
        lake.setWindSpeed(measurement.getWindSpeed());
        ksessionCep.insert(lake);

        HydroelectricPowerPlant h = database.getHydroelectricPowerPlant();
        h.setValvesOpened(false);
        h.setElectricityProduction(100);
        h.setGeneratorsOn(false);
        h.setTurbines(database.getTurbines());
        h.setLake(database.getLake());
        ksessionCep.insert(h);

        MeasuringEvent measuringEvent = new MeasuringEvent(measurement.getWindSpeed(), measurement.getWaterSpeed(), measurement.getWaterTemp(), measurement.getWaterLvl(), lake.getId());
        database.getMeasuringEvents().add(measuringEvent);
        ksessionCep.insert(measuringEvent);

        if (measuringEvent.getWaterLvl() < 10 && measuringEvent.getWindSpeed() < 10)  {
            ksessionCep.insert(new DecreasedRainEvent(lake.getId()));
        }
        long k1 = ksessionCep.fireAllRules();
        System.out.println(k1);
    }

    public void alarmResolved() {
        ksessionCep.insert(new SeriousMalfunctionAlarm(database.getHydroelectricPowerPlant().getId()));
        ksessionCep.insert(new AlarmDeactivatedEvent(database.getHydroelectricPowerPlant().getId()));
        long k1 = ksessionCep.fireAllRules();
        ksessionCep.dispose();
        ksessionCep = KieServices.Factory.get().newKieClasspathContainer().newKieSession("CEPKS");
    }
}
