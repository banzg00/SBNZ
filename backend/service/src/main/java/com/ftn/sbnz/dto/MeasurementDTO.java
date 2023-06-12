package com.ftn.sbnz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDTO {

    private double waterLvl;
    private double waterTemp;
    private double waterSpeed;
    private double windSpeed;
    private double waterFlow;
    private double pressure;
}
