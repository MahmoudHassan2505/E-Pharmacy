package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.entity.Patient;
import com.banhauniversity.sidalih.repository.PatientRepository;
import com.banhauniversity.sidalih.service.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/patients")
public class PatientController {

    @Autowired
    private PatientServices patientServices;

    @GetMapping
    public List<Patient> findAll(){
        return patientServices.findAll();
    }

    @GetMapping("/type")
    public List<Patient> findChronic(@RequestParam boolean isChronic){
        return patientServices.findByType(isChronic);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient add(@RequestBody Patient patient){
        return patientServices.add(patient);
    }

    @GetMapping("/{id}")
    public Patient findById(@PathVariable long id){
        return patientServices.findById(id);
    }

    @PutMapping
    public Patient update(@RequestBody Patient patient){
        return patientServices.update(patient);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable long id){
        patientServices.delete(id);
        return true;
    }

    @GetMapping("/{collegeName}")
    public List<Patient> collegeName(@PathVariable String collegeName){
        return patientServices.findByCollegeName(collegeName);
    }
}
