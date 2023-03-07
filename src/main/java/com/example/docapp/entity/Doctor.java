package com.example.docapp.entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "doctor")
@Data
public class Doctor {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String specification;
    private String fromTime;
    private String toTime;
    @Column(name = "max_appointment")
    private int maxAppointmentPerDay;
}
