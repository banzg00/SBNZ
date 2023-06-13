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

    public HydroelectricPowerPlant(long id, Lake lake, List<Turbine> turbines) {
        this.id = id;
        this.lake = lake;
        this.turbines = turbines;
    }

    public Turbine getAvailableTurbine() {
        for (var t: turbines) {
            if (!t.isOn()) {
                return t;
            }
        }
        return null;
    }

    public void turnOffTurbine() {
        if (turbines.get(2).isOn()) {
            turbines.get(2).setOn(false);
        } else if (turbines.get(1).isOn()) {
            turbines.get(1).setOn(false);
        }
    }
}
