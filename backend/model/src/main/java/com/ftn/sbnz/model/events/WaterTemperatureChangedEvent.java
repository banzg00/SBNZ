package com.ftn.sbnz.model.events;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Role(Role.Type.EVENT)
@Timestamp("executionTime")
public class WaterTemperatureChangedEvent implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private long lakeId;
    private double waterTemperature;
    private Date executionTime;

    public WaterTemperatureChangedEvent(Long lakeId, double waterTemperature) {
        super();
        this.executionTime = new Date();
        this.lakeId = lakeId;
        this.waterTemperature = waterTemperature;
    }
}
