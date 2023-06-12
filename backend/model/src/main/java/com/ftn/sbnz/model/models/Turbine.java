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
    private boolean overheatingDanger;
    private double pressure;    // 0 - 100

    public Turbine(long id) {
        this.id = id;
    }
}
