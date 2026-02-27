package com.co_notice.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="dc_cases1")
public class DcCaseEntity {

    @Id
    @Column(name="CASE_NUM")
    private Long caseNum;

    @Column(name="APP_ID")
    private Long appId;

    @Column(name="PLAN_ID")
    private Long planId;
}