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
public class Alarm implements Serializable {

    private static final long serialVersionUID = 1L;
    public long hydroPowerPlantId;
    public String reason;
    private Date executionTime;

    public Alarm(long hydroPowerPlantId, String reason) {
        super();
        this.executionTime = new Date();
        this.hydroPowerPlantId = hydroPowerPlantId;
        this.reason = reason;
    }
}
