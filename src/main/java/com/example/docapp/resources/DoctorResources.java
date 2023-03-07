package com.example.docapp.resources;

import com.example.docapp.model.DoctorDTO;
import com.example.docapp.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/doctor")
public class DoctorResources {

    Logger log = LoggerFactory.getLogger(DoctorResources.class);

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        log.info("Request to create doctor with {}", doctorDTO);
        return new ResponseEntity<>(doctorService.createDoctor(doctorDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<DoctorDTO> updateDoctor(@RequestBody DoctorDTO doctorDTO) {
        log.info("Request to update doctor with id {}", doctorDTO.getId());
        return new ResponseEntity<>(doctorService.updateDoctor(doctorDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        log.info("Request to get all doctors");
        List<DoctorDTO> doctorDTOS = doctorService.findAll();
        if (!doctorDTOS.isEmpty())
            return new ResponseEntity<>(doctorDTOS, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> findById(@PathVariable Long id) {
        log.info("Request to get doctor with id {}", id);
        DoctorDTO doctorDTO = doctorService.findById(id);
        if (doctorDTO != null)
            return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        log.info("Request to delete doctor with id {}", id);
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

}
