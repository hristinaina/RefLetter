package com.ftn.sbnz.model.events;

import java.util.Date;

import com.ftn.sbnz.model.models.Requirement;
import org.kie.api.definition.type.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Role(Role.Type.EVENT)
//TODO definisati ovaj datum
public class FinancialAid {
    private String type;
    private double amount;
    private Requirement requrement;
    private Date deadline;
}
