package com.ftn.sbnz.model.events;

import java.util.Date;
import java.util.List;

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
    private List<String> oldInterests;
    private List<String> currentInterests;
    private Date timestamp;
    private Long studentId;
}
