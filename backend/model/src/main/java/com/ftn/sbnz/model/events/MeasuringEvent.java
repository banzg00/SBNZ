package com.ftn.sbnz.model.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Role(Role.Type.EVENT)
@Timestamp("executionTime")
public class MeasuringEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private double windSpeed;
    private double waterLvl;
    private double waterTemp;
    private double waterSpeed;
    private long lakeId;

    private Date executionTime;

    public MeasuringEvent(double windSpeed, double waterSpeed, double waterTemp, double waterLvl, long lakeId) {
        super();
        this.executionTime = new Date();
        this.windSpeed = windSpeed;
        this.waterLvl = waterLvl;
        this.waterTemp = waterTemp;
        this.waterSpeed = waterSpeed;
        this.lakeId = lakeId;
    }
}
