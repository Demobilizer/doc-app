package com.example.docapp.service;

import com.example.docapp.entity.Appointment;
import com.example.docapp.exception.NotAvailableException;
import com.example.docapp.exception.NotFoundException;
import com.example.docapp.model.AppointmentDTO;
import com.example.docapp.model.DoctorDTO;
import com.example.docapp.repository.AppointmentRepository;
import com.example.docapp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorService doctorService;

    private final String DOCTOR_NOT_AVAIL = "Selected doctor not available for selected date ";
    private final String APP_NOT_FOUND = "Appointment not found with id ";

    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        if (isDoctorAvailable(appointmentDTO)) {
            Appointment appointment = dtoToEntity(appointmentDTO);
            Appointment createdAppointment = appointmentRepository.save(appointment);
            return entityToDTO(createdAppointment);
        } else {
            throw new NotAvailableException(DOCTOR_NOT_AVAIL + appointmentDTO.getAppointmentDate());
        }
    }

    private boolean isDoctorAvailable(AppointmentDTO appointmentDTO) {
        Integer appByDocByDate = appointmentRepository.findByDoctorByAppointmentsDate(DateUtil.stringToDate(appointmentDTO.getAppointmentDate()), appointmentDTO.getDoctorId());
        Integer maxAppByDoc = doctorService.findMaxAppointmentPerDayByDoctorId(appointmentDTO.getDoctorId());
        return appByDocByDate < maxAppByDoc;
    }

    public AppointmentDTO updateAppointment(AppointmentDTO appointmentDTO) {
        if (appointmentDTO.getId() == null)
            throw new RuntimeException("Appointment id must not be null"); // can use custom exception
        else {
            if (isDoctorAvailable(appointmentDTO)) {
                AppointmentDTO exAppointment = findById(appointmentDTO.getId());
                exAppointment.setPatientName(appointmentDTO.getPatientName());
                exAppointment.setPatientEmail(appointmentDTO.getPatientEmail());
                exAppointment.setPatientMobile(appointmentDTO.getPatientMobile());
                exAppointment.setDob(appointmentDTO.getDob());
                exAppointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
                exAppointment.setDoctorId(appointmentDTO.getDoctorId());
                Appointment updatedAppointment = appointmentRepository.save(dtoToEntity(exAppointment));
                return entityToDTO(updatedAppointment);
            } else {
                throw new NotAvailableException(DOCTOR_NOT_AVAIL + appointmentDTO.getAppointmentDate());
            }
        }
    }

    public List<AppointmentDTO> findAll() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.isEmpty() ? null : entityToDTOs(appointments);
    }

    public AppointmentDTO findById(Long id) {
        Optional<Appointment> appointmentOp = appointmentRepository.findById(id);
        if (appointmentOp.isPresent())
            return entityToDTO(appointmentOp.get());
        else
            throw new NotFoundException(APP_NOT_FOUND + id);
    }

    public void deleteAppointment(Long id) {
        try {
            appointmentRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(APP_NOT_FOUND + id);
        }
    }

    // following mapping can be implemented by map.struct library for better readability

    private AppointmentDTO entityToDTO(Appointment appointment) {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setPatientName(appointment.getPatientName());
        appointmentDTO.setPatientEmail(appointment.getPatientEmail());
        appointmentDTO.setPatientMobile(appointment.getPatientMobile());
        appointmentDTO.setDob(DateUtil.dateToString(appointment.getDob()));
        appointmentDTO.setAppointmentDate(DateUtil.dateToString(appointment.getAppointmentDate()));
        appointmentDTO.setDoctorId(appointment.getDoctor().getId());
        return appointmentDTO;
    }

    private List<AppointmentDTO> entityToDTOs(List<Appointment> appointments) {
        return appointments.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    private Appointment dtoToEntity(AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        if (appointmentDTO.getId() != null)
            appointment.setId(appointmentDTO.getId());
        appointment.setPatientName(appointmentDTO.getPatientName());
        appointment.setPatientEmail(appointmentDTO.getPatientEmail());
        appointment.setPatientMobile(appointmentDTO.getPatientMobile());
        appointment.setDob(DateUtil.stringToDate(appointmentDTO.getDob()));
        appointment.setAppointmentDate(DateUtil.stringToDate(appointmentDTO.getAppointmentDate()));
        DoctorDTO doctorDTO = doctorService.findById(appointmentDTO.getDoctorId());
        appointment.setDoctor(doctorService.dtoToEntity(doctorDTO));
        return appointment;
    }

    private List<Appointment> dtoToEntities(List<AppointmentDTO> appointmentDTOS) {
        return appointmentDTOS.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

}
