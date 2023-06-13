package com.ftn.sbnz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDTO {
    private String description;
    private String severity;
    private LocalTime time;
}
