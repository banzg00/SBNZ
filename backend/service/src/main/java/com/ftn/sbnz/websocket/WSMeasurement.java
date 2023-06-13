package com.ftn.sbnz.websocket;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Builder
@ToString
public class WSMeasurement {
    @Getter
    private double waterLvl;
    @Getter
    private double waterTemp;
    @Getter
    private double waterSpeed;
    @Getter
    private double windSpeed;
    @Getter
    private double electricityGenerated;
    @Getter
    private int turbinesActive;
}