package com.ftn.sbnz.service;

import com.ftn.sbnz.dto.MeasurementDTO;
import com.ftn.sbnz.model.events.*;
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
    private KieSession ksessionCep;
    private KieSession ksessionTemplate;

    @Autowired
    public RulesService(Database database) {
        this.database = database;
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        ksessionCep = kc.newKieSession("CEPKS");
        ksessionTemplate = kc.newKieSession("templateKS");
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

        long k1 = ksessionCep.fireAllRules();
        System.out.println(k1);
    }

    public void fireTemplateRules(MeasurementDTO measurement) {
        setMeasurements(measurement, ksessionTemplate);
        Lake lake = database.getLake();

        MeasuringEvent measuringEvent = new MeasuringEvent(measurement.getWindSpeed(), measurement.getWaterSpeed(), measurement.getWaterTemp(), measurement.getWaterLvl(), lake.getId());
        database.getMeasuringEvents().add(measuringEvent);
        ksessionTemplate.insert(measuringEvent);

        long k1 = ksessionTemplate.fireAllRules();
        System.out.println(k1);
    }

    private void setMeasurements(MeasurementDTO measurement, KieSession kieSession) {
        for (var t : database.getTurbines()) {
            t.setWaterFlow(measurement.getWaterLvl() * measurement.getWaterSpeed() * 0.8);
            kieSession.insert(t);
        }
        Lake lake = database.getLake();
        lake.setWaterLvl(measurement.getWaterLvl());
        lake.setWaterTemp(measurement.getWaterTemp());
        lake.setWaterSpeed(measurement.getWaterSpeed());
        lake.setWindSpeed(measurement.getWindSpeed());
        kieSession.insert(lake);

        HydroelectricPowerPlant h = database.getHydroelectricPowerPlant();
        h.setTurbines(database.getTurbines());
        h.setLake(database.getLake());
        kieSession.insert(h);
    }

    public void alarmResolved() {
//        ksessionCep.insert(new SeriousMalfunctionAlarm(database.getHydroelectricPowerPlant().getId()));
        ksessionCep.insert(new AlarmDeactivatedEvent(database.getHydroelectricPowerPlant().getId()));
        ksessionCep.fireAllRules();
        ksessionCep.dispose();
        ksessionCep = KieServices.Factory.get().newKieClasspathContainer().newKieSession("CEPKS");
    }

    public void alarmNotResolved() {
        ksessionCep.insert(new AlarmNotDeactivated(database.getHydroelectricPowerPlant().getId()));
        long k1 = ksessionCep.fireAllRules();
        ksessionCep.dispose();
        ksessionCep = KieServices.Factory.get().newKieClasspathContainer().newKieSession("CEPKS");
    }
}
