package com.ftn.sbnz.model.models.dto;

import com.ftn.sbnz.model.events.FinancialAid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialAidDTO {
    private FinancialAid financialAid;
    private Long programId;

}
