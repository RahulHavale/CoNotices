package com.co_notice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="co_trigger_tbl")
public class CoTriggerEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long triggerId;

    private Long caseNum;

    private Long edId;

    private String noticeUrl;

    private String coPdf;

    private LocalDate printDate;

    private String noticeStatus;

    private LocalDate createdDate;
}
