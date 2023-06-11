package com.ftn.sbnz.service.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.ftn.sbnz.model.events.DecreasedRainEvent;
import com.ftn.sbnz.model.events.MeasuringEvent;
import com.ftn.sbnz.model.models.HydroelectricPowerPlant;
import com.ftn.sbnz.model.models.Lake;
import com.ftn.sbnz.model.models.Turbine;

public class CEPTest {
    
    @Test
    public void testCEP() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        KieSession ksession = kc.newKieSession("CEPKS");

        SessionPseudoClock clock = ksession.getSessionClock();
        clock.advanceTime(1, TimeUnit.MILLISECONDS);

        doTest(ksession);
    }

    private void doTest(KieSession ksession){
        SessionPseudoClock clock = ksession.getSessionClock();

        Turbine t1 = new Turbine(1, 30, false, 30);
        Turbine t2 = new Turbine(2, 30, false, 35);
        Turbine t3 = new Turbine(3, 50, false, 39);

        Lake lake = new Lake(1, 15, 30, 15, 40);

        HydroelectricPowerPlant hydroelectricPowerPlant = new HydroelectricPowerPlant(1, 100, lake, Arrays.asList(t1, t2, t3), false, false);

        ksession.insert(t1);
        ksession.insert(t2);
        ksession.insert(t3);

        ksession.insert(lake);

        ksession.insert(hydroelectricPowerPlant);

        int n = 0;

        ksession.insert(new DecreasedRainEvent(1L));

        for (int i = 0; i < 6; i++) {
            ksession.insert(new MeasuringEvent(40, 15, 15, 15, 1));
            clock.advanceTime(1, TimeUnit.MINUTES);
        }
        for (int i = 0; i < 6; i++) {
            ksession.insert(new MeasuringEvent(40, 50, 25, 15, 1));
            clock.advanceTime(1, TimeUnit.MINUTES);
        }
        
        // ksession.insert(new AlarmDeactivatedEvent(1L));
        n = ksession.fireAllRules();

        assertEquals(6, n);

    }
}
