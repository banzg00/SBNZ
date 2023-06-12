package com.ftn.sbnz.controller;

import com.ftn.sbnz.websocket.MessageType;
import com.ftn.sbnz.websocket.WSMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalTime;
import java.util.List;

@Controller
public class WSController {

    private final SimpMessagingTemplate template;

    @Autowired
    public WSController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/alarm")
    @SendTo("/topic/alarms")
    public void sendMessage(String message) {
//        this.template.convertAndSend("/topic/", message);
        System.out.println("Message received: " + message);
        WSMessage newMessage = WSMessage.builder()
                .type(MessageType.ALARM)
                .id(1)
                .messages(List.of("Ovo je opasan alarm", "medium"))
                .sentTime(LocalTime.now())
                .build();
        this.template.convertAndSend("/topic/alarms", newMessage);
    }

    @MessageMapping("/values")
    @SendTo("/topic/values")
    public void sendMessage(@Payload final WSMessage message) {
        System.out.println("Message received: " + message);
    }
}
