package com.ftn.sbnz.service;

import com.ftn.sbnz.dto.AlarmDTO;
import com.ftn.sbnz.model.events.Alarm;
import com.ftn.sbnz.repository.Database;
import com.ftn.sbnz.websocket.MessageType;
import com.ftn.sbnz.websocket.WSMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class AlarmService {
    private static Database database;
    private static SimpMessagingTemplate template;

    @Autowired
    public AlarmService(Database database, SimpMessagingTemplate template) {
        this.database = database;
        AlarmService.template = template;
    }

    public static void addAlarm(String description, String severity) {
        LocalTime timeNow = LocalTime.now();
        System.out.println("Sending alarm");
        database.getAlarms().add(new AlarmDTO(description, severity, timeNow));
        WSMessage newMessage = WSMessage.builder()
                .type(MessageType.ALARM)
                .description(description)
                .severity(severity)
                .time(timeNow)
                .build();
        AlarmService.template.convertAndSend("/topic/alarm", newMessage);
    }

}
