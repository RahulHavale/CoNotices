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
@Table(name="co_notice_tbl")
public class COEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticId;

//    @ManyToOne
//    @JoinColumn(name="caseNum")
    private Long caseNum;

    @ManyToOne
    @JoinColumn(name="edId")
    private EDEntity edId;

    private String noticeUrl;

    private String coPdf;

    private LocalDate printDate;

    private String noticeStatus;

    private LocalDate createdDate;
}
