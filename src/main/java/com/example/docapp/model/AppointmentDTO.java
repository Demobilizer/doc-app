package com.example.docapp.model;

import lombok.Data;

@Data
public class AppointmentDTO {

    private Long id;
    private String patientName;
    private String patientEmail;
    private String patientMobile;
    private String dob;
    private String appointmentDate;
    private Long doctorId;
}
