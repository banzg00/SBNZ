package com.ftn.sbnz.controller;

import com.ftn.sbnz.dto.MeasurementDTO;
import com.ftn.sbnz.service.RulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class MeasurementController {

    private final RulesService rulesService;

    @Autowired
    public MeasurementController(RulesService rulesService) {
        this.rulesService = rulesService;
    }

    @PostMapping("/measurements")
    public ResponseEntity<?> getMeasurements(@RequestBody MeasurementDTO measurement) {
        System.out.println(measurement);
        rulesService.fireChainRules(measurement);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
