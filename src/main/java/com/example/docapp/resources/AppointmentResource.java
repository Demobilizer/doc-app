package com.example.docapp.resources;

import com.example.docapp.model.AppointmentDTO;
import com.example.docapp.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/appointment")
public class AppointmentResource {

    Logger log = LoggerFactory.getLogger(AppointmentResource.class);

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        log.info("Request to create appointment with {}", appointmentDTO);
        return new ResponseEntity<>(appointmentService.createAppointment(appointmentDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AppointmentDTO> updateDoctor(@RequestBody AppointmentDTO appointmentDTO) {
        log.info("Request to update appointment with id {}", appointmentDTO.getId());
        return new ResponseEntity<>(appointmentService.updateAppointment(appointmentDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        log.info("Request to get all appointments");
        List<AppointmentDTO> appointmentDTOs = appointmentService.findAll();
        if (!appointmentDTOs.isEmpty())
            return new ResponseEntity<>(appointmentDTOs, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable Long id) {
        log.info("Request to get appointment with id {}", id);
        AppointmentDTO appointmentDTO = appointmentService.findById(id);
        if (appointmentDTO != null)
            return new ResponseEntity<>(appointmentDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        log.info("Request to delete appointment with id {}", id);
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

}
