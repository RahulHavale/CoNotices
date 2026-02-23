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
@Table(name="elig_tbl")
public class EDEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long edId;

//    @ManyToOne
//    @JoinColumn(name="caseNum")
    private Long caseNum;

    private String planName;

    private String planStatus;

    private LocalDate eligStartDate;

    private LocalDate eligEndDate;

    private Double benifitAmt;

    private String denialReason;

    private LocalDate createdDate;

}
