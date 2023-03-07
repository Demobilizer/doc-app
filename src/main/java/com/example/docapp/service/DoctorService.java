package com.example.docapp.service;

import com.example.docapp.entity.Doctor;
import com.example.docapp.exception.NotFoundException;
import com.example.docapp.model.DoctorDTO;
import com.example.docapp.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = dtoToEntity(doctorDTO);
        Doctor createdDoctor = doctorRepository.save(doctor);
        return entityToDTO(createdDoctor);
    }

    public DoctorDTO updateDoctor(DoctorDTO doctorDTO) {
        if (doctorDTO.getId() == null)
            throw new RuntimeException("Doctor id must not be null"); // can use custom exception
        else {
            DoctorDTO existingDoctor = findById(doctorDTO.getId());
            existingDoctor.setName(doctorDTO.getName());
            existingDoctor.setSpecification(doctorDTO.getSpecification());
            existingDoctor.setFromTime(doctorDTO.getFromTime());
            existingDoctor.setToTime(doctorDTO.getToTime());
            existingDoctor.setMaxAppointmentPerDay(doctorDTO.getMaxAppointmentPerDay());
            Doctor updatedDoctor = doctorRepository.save(dtoToEntity(existingDoctor));
            return entityToDTO(updatedDoctor);
        }
    }

    public List<DoctorDTO> findAll() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.isEmpty() ? null : entityToDTOs(doctors);
    }

    public DoctorDTO findById(Long id) {
        Optional<Doctor> doctorOp = doctorRepository.findById(id);
        if (doctorOp.isPresent())
            return entityToDTO(doctorOp.get());
        else
            throw new NotFoundException("Doctor not found with id " + id);
    }

    public void deleteDoctor(Long id) {
        try {
            // Need to remove all associated appointments for the doctorId, Skipping for now
            doctorRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("Doctor not found with id " + id);
        }
    }

    public Integer findMaxAppointmentPerDayByDoctorId(Long doctorId) {
        return doctorRepository.findMaxAppointmentPerDayById(doctorId);
    }

    private DoctorDTO entityToDTO(Doctor doctor) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setName(doctor.getName());
        doctorDTO.setSpecification(doctor.getSpecification());
        doctorDTO.setFromTime(doctor.getFromTime());
        doctorDTO.setToTime(doctor.getToTime());
        doctorDTO.setMaxAppointmentPerDay(doctor.getMaxAppointmentPerDay());
        return doctorDTO;
    }

    private List<DoctorDTO> entityToDTOs(List<Doctor> doctors) {
        return doctors.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public Doctor dtoToEntity(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        if (doctorDTO.getId() != null)
            doctor.setId(doctorDTO.getId());
        doctor.setName(doctorDTO.getName());
        doctor.setSpecification(doctorDTO.getSpecification());
        doctor.setFromTime(doctorDTO.getFromTime());
        doctor.setToTime(doctorDTO.getToTime());
        doctor.setMaxAppointmentPerDay(doctorDTO.getMaxAppointmentPerDay());
        return doctor;
    }

    private List<Doctor> dtoToEntities(List<DoctorDTO> doctorDTOs) {
        return doctorDTOs.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

}
