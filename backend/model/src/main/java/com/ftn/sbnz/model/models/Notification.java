package com.ftn.sbnz.model.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private Long userId;
    private String message;
    private Date notifiedAt;
    private Long financialAidId;

    public Notification(Long userId, String message, Long financialAidId) {
        this.userId = userId;
        this.message = message;
        this.notifiedAt = new Date();
        this.financialAidId = financialAidId;
    }
}
