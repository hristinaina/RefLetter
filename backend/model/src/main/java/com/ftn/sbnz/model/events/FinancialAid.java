package com.ftn.sbnz.model.events;

import java.util.Date;

import com.ftn.sbnz.model.enums.FinancialAidType;
import com.ftn.sbnz.model.models.Requirement;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Role(Role.Type.EVENT)
@Timestamp("deadline")
public class FinancialAid {
    private FinancialAidType type;
    private double amount;
    private Requirement requrement;
    private Date deadline;
}
