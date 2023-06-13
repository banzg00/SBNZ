package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Turbine {

    private long id;
    private double waterFlow;   // m3/s
    private boolean on;

    public double getPowerGenerated() {
        return waterFlow * 2 * Math.PI / Math.sqrt(5);
    }

}
