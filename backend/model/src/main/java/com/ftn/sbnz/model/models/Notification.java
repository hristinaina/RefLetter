package com.ftn.sbnz.model.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String message;
    private Date notifiedAt;
    private Long financialAidId;
    private String programName;

    public Notification(Long userId, String message, Long financialAidId) {
        this.userId = userId;
        this.message = message;
        this.notifiedAt = new Date();
        this.financialAidId = financialAidId;
    }
}
