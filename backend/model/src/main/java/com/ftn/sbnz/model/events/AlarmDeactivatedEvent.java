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
public class AlarmDeactivatedEvent implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private long hydroPowerPlantId;
    private Date executionTime;

    public AlarmDeactivatedEvent(Long hydroPowerPlantId) {
        super();
        this.executionTime = new Date();
        this.hydroPowerPlantId = hydroPowerPlantId;
    }
}
