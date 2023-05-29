package com.ftn.sbnz.service.tests;

import com.ftn.sbnz.model.models.HydroelectricPowerPlant;
import com.ftn.sbnz.model.models.Lake;
import com.ftn.sbnz.model.models.Turbine;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChainTest {

    @Test
    public void testTempChain() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        KieSession ksession = kc.newKieSession("cepKsession");

        Turbine t1 = new Turbine(1, 30, false, 30);
        Turbine t2 = new Turbine(2, 30, false, 35);
        Turbine t3 = new Turbine(3, 50, false, 39);

        Lake lake = new Lake(1, 80, 30, 20, 40);

        HydroelectricPowerPlant hydroelectricPowerPlant = new HydroelectricPowerPlant(1, 100, lake, Arrays.asList(t1, t2, t3), false, false);

        ksession.insert(t1);
        ksession.insert(t2);
        ksession.insert(t3);
        ksession.insert(lake);
        ksession.insert(hydroelectricPowerPlant);

        long k1 = ksession.fireAllRules();

        assertEquals(4, k1);
        assertTrue(hydroelectricPowerPlant.isGeneratorsOn());
    }

    @Test
    public void testWaterLevelChain() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        KieSession ksession = kc.newKieSession("cepKsession");

        Turbine t1 = new Turbine(1, 30, false, 30);
        Turbine t2 = new Turbine(2, 30, false, 35);
        Turbine t3 = new Turbine(3, 50, false, 39);

        Lake lake = new Lake(1, 90, 15, 20, 40);

        HydroelectricPowerPlant hydroelectricPowerPlant = new HydroelectricPowerPlant(1, 100, lake, Arrays.asList(t1, t2, t3), false, false);

        ksession.insert(t1);
        ksession.insert(t2);
        ksession.insert(t3);
        ksession.insert(lake);
        ksession.insert(hydroelectricPowerPlant);

        long k1 = ksession.fireAllRules();

        assertEquals(5, k1);
        assertTrue(hydroelectricPowerPlant.isGeneratorsOn());
    }
}
