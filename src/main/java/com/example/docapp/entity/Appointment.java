package com.example.docapp.entity;

import javax.persistence.*;

import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "appointment")
@Data
public class Appointment {

    @Id
    @GeneratedValue
    private Long id;
    private String patientName;
    private String patientEmail;
    private String patientMobile;
    private Date dob;
    private Date appointmentDate;
    @ManyToOne
    private Doctor doctor;
}
