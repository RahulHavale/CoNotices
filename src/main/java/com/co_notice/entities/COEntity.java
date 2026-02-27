package com.co_notice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="co_notice_tbl1")
public class COEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    private Long caseNum;

    private Long edId;

    private String noticeUrl;

    private String pdfName;

    private LocalDate printDate;

    private String noticeStatus;

    private LocalDate createdDate;
}