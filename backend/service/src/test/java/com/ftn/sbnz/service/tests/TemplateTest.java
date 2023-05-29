package com.ftn.sbnz.service.tests;

import com.ftn.sbnz.model.events.MeasuringEvent;
import com.ftn.sbnz.model.models.HydroelectricPowerPlant;
import com.ftn.sbnz.model.models.Lake;
import com.ftn.sbnz.model.models.Turbine;
import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TemplateTest {

    @Test
    public void testTemplate() {

//        InputStream template = TemplateTest.class.getResourceAsStream("/rules/template/water_flow.drt");
//        InputStream data = TemplateTest.class.getResourceAsStream("/rules/template/data.xls");
//
//        ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
//        String drl = converter.compile(data, template, 3, 1);

//        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
//                new String[]{"0", "50", "0", "30", "0", "20", "1.15"},
//                new String[]{"0", "50", "0", "30", "20", "99", "1.10"},
//                new String[]{"0", "50", "70", "100", "0", "99", "0.90"},
//                new String[]{"50", "250", "0", "30", "0", "99", "1.20"},
//                new String[]{"50", "250", "70", "100", "0", "20", "0.80"},
//                new String[]{"50", "250", "70", "100", "20", "99", "0.70"},
//        });

//        DataProviderCompiler converter = new DataProviderCompiler();
//        String drl = converter.compile(dataProvider, template);

//        System.out.println(drl);
//
//        KieSession ksession = createKieSessionFromDRL(drl);

        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        KieSession ksession = kc.newKieSession("templateKS");

        SessionPseudoClock clock = ksession.getSessionClock();
        clock.advanceTime(1, TimeUnit.MILLISECONDS);

        doTest(ksession);
    }

    private void doTest(KieSession ksession){
        SessionPseudoClock clock = ksession.getSessionClock();

        Turbine t1 = new Turbine(1, 30, false, 30);
        Turbine t2 = new Turbine(2, 30, false, 35);
        Turbine t3 = new Turbine(3, 50, false, 39);

        Lake lake = new Lake(1, 20, 30, 20, 40);

        HydroelectricPowerPlant hydroelectricPowerPlant = new HydroelectricPowerPlant(1, 100, lake, Arrays.asList(t1, t2, t3), false, false);

        ksession.insert(t1);
        ksession.insert(t2);
        ksession.insert(t3);

        ksession.insert(lake);

        ksession.insert(hydroelectricPowerPlant);

        int n = 0;

        for (int i = 0; i < 5; i++) {
            ksession.insert(new MeasuringEvent(40, 20, 30));
            clock.advanceTime(10, TimeUnit.MINUTES);
            n = ksession.fireAllRules();
        }

        assertEquals(1, n);
        assertTrue(t1.getWaterFlow() > 30);

    }

}
