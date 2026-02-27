package com.co_notice.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "citizen_apps1")
public class CitizenAppEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="APP_ID")
    private Long appId;

    @Column(name="EMAIL")
    private String email;

    @Column(name="FULLNAME")
    private String fullName;

    @Column(name="SSN")
    private Long ssn;
}