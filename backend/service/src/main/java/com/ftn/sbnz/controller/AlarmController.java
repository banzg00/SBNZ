package com.ftn.sbnz.controller;

import com.ftn.sbnz.dto.AlarmDTO;
import com.ftn.sbnz.dto.MeasurementDTO;
import com.ftn.sbnz.repository.Database;
import com.ftn.sbnz.service.RulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class AlarmController {
    private final RulesService rulesService;

    @Autowired
    private Database database;

    @Autowired
    public AlarmController(RulesService rulesService) {
        this.rulesService = rulesService;
    }

    @GetMapping("/alarms/all")
    public ResponseEntity<List<AlarmDTO>> getMeasurements() {
        return ResponseEntity.ok(database.getAlarms());
    }
}
