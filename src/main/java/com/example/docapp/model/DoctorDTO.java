package com.example.docapp.model;

import lombok.Data;

@Data
public class DoctorDTO {

    private Long id;
    private String name;
    private String specification;
    private String fromTime;
    private String toTime;
    private int maxAppointmentPerDay;
}
