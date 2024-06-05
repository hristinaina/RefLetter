package com.ftn.sbnz.model.events;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
public class InterestChange {
    private Set<String> oldInterests;
    private Set<String> currentInterests;
    private Date timestamp;
    private Long studentId;

    public InterestChange(Set<String> oldinterests, Set<String> newinterests, Long sId) {
        this.studentId = sId;
        this.oldInterests = oldinterests;
        this.currentInterests = newinterests;
        this.timestamp = new Date();
    }
}

