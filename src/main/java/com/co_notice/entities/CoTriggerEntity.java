package com.co_notice.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="co_triggers1")
public class CoTriggerEntity {

    @Id
    @Column(name="TRG_ID")
    private Long triggerId;

    @Column(name="CASE_NUM")
    private Long caseNum;

    @Column(name="TRG_STATUS")
    private String triggerStatus; // P / C

    private String co_pdf;

    private String notice_url;
}