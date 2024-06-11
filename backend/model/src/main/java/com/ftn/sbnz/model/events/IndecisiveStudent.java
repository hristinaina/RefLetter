package com.ftn.sbnz.model.events;

import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Role(Role.Type.EVENT)
@Timestamp("timestamp")
@Expires("240h")
public class IndecisiveStudent {
    private Long studentId;
    private boolean isIndecisive;
    private Date timestamp;

    public IndecisiveStudent(Long sId) {
        this.studentId = sId;
        this.isIndecisive = true;
        this.timestamp = new Date();
    }
}



