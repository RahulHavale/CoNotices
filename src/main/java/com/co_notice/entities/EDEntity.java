package com.co_notice.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="eligibility_dtls1")
public class EDEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ED_ID")
    private Long edId;

    @Column(name="CASE_NUM")
    private Long caseNum;

    @Column(name="PLAN_NAME")
    private String planName;

    @Column(name="PLAN_STATUS")
    private String planStatus; // AP / DN

    @Column(name="DENIAL_REASON")
    private String denialReason;
}