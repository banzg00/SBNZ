package com.ftn.sbnz.controller;

import com.ftn.sbnz.dto.MeasurementDTO;
import com.ftn.sbnz.repository.Database;
import com.ftn.sbnz.service.RulesService;
import com.ftn.sbnz.websocket.MessageType;
import com.ftn.sbnz.websocket.WSMeasurement;
import com.ftn.sbnz.websocket.WSMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/api")
public class MeasurementController {
    private final RulesService rulesService;
    private final SimpMessagingTemplate template;

    @Autowired
    private Database database;

    @Autowired
    public MeasurementController(RulesService rulesService, SimpMessagingTemplate template) {
        this.rulesService = rulesService;
        this.template = template;
    }

    @PostMapping("/measurements")
    public ResponseEntity<?> getMeasurements(@RequestBody MeasurementDTO measurement) {
        measurement.setElectricityGenerated(1200);
        measurement.setTurbines(database.getHydroelectricPowerPlant().getActiveTurbines());
        database.getMeassurements().add(measurement);
        rulesService.fireChainRules(measurement);
        rulesService.fireCEPRules(measurement);
        WSMeasurement newMessage = WSMeasurement.builder()
                .waterLvl(measurement.getWaterLvl())
                .waterTemp(measurement.getWaterTemp())
                .waterSpeed(measurement.getWaterSpeed())
                .windSpeed(measurement.getWindSpeed())
                .electricityGenerated(1200)
                .turbinesActive(database.getHydroelectricPowerPlant().getActiveTurbines())
                .build();
        this.template.convertAndSend("/topic/measurement", newMessage);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/measurements/all")
    public ResponseEntity<List<MeasurementDTO>> getMeasurements() {
        return ResponseEntity.ok(database.getMeassurements());
    }
}
