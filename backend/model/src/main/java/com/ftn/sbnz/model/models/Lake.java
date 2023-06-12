package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lake {

    private long id;
    private double waterLvl;
    private double waterTemp;
    private double waterSpeed;
    private double windSpeed;

    public Lake(long id) {
        this.id = id;
    }
}
