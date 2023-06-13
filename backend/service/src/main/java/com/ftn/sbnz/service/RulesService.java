package com.ftn.sbnz.service;

import com.ftn.sbnz.dto.MeasurementDTO;
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

    @Autowired
    public RulesService(Database database) {
        this.database = database;
    }

    public void fireChainRules(MeasurementDTO measurement) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        KieSession ksession = kc.newKieSession("forwardKS");

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
        h.setElectricityProduction(100);
        h.setTurbines(database.getTurbines());
        h.setLake(database.getLake());
        ksession.insert(h);

        long k1 = ksession.fireAllRules();

        System.out.println(k1 == 3);

        ksession.dispose();
    }
}
