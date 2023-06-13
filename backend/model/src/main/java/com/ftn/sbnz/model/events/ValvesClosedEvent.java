package com.ftn.sbnz.model.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kie.api.definition.type.Role;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Role(Role.Type.EVENT)
public class ValvesClosedEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private long lakeId;
}
