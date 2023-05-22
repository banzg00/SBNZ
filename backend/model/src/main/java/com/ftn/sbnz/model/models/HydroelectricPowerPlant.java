package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HydroelectricPowerPlant {

    private long id;
    private double electricityProduction;
    private Lake lake;
    private List<Turbine> turbines;
    private boolean generatorsOn;
    private boolean valvesOpened;
}
