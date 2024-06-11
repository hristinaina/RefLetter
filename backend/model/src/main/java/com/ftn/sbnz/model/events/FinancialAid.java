package com.ftn.sbnz.model.events;

import java.util.Date;

import com.ftn.sbnz.model.enums.FinancialAidType;
import com.ftn.sbnz.model.models.Requirement;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Role(Role.Type.EVENT)
@Timestamp("deadline")
@Entity
public class FinancialAid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private FinancialAidType type;
    private double amount;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Requirement requirement;
    private Date deadline;

    public FinancialAid(FinancialAidType type, double amount, Requirement requirement, Date deadline) {
        this.type = type;
        this.amount = amount;
        this.requirement = requirement;
        this.deadline = deadline;
    }
    public FinancialAid(Long id, Requirement requirement, Date date) {
        this.id = id;
        this.requirement = requirement;
        this.deadline = date;
    }
}
