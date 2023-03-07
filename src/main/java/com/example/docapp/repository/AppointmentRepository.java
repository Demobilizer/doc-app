package com.example.docapp.repository;

import com.example.docapp.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.appointmentDate=:appointmentDate AND a.doctor.id=:doctorId")
    Integer findByDoctorByAppointmentsDate(@Param("appointmentDate") Date appointmentDate, @Param("doctorId") Long doctorId);
}
