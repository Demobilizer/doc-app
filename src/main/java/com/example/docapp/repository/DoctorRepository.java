package com.example.docapp.repository;

import com.example.docapp.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT d.maxAppointmentPerDay FROM Doctor d WHERE d.id=:id")
    Integer findMaxAppointmentPerDayById(@Param("id") Long id);
}
