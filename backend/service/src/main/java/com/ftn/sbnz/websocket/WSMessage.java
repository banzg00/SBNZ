package com.ftn.sbnz.websocket;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Builder
@ToString
public class WSMessage {
    @Getter
    private MessageType type;
    @Getter
    private Integer id;
    @Getter
    private List<String> messages;
    @Getter
    private LocalTime sentTime;
}