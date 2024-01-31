package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.entity.Prescription;
import com.banhauniversity.sidalih.service.PrescriptionCategoryService;
import com.banhauniversity.sidalih.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/prescriptions")
public class PrescriptionController {

    @Autowired private PrescriptionService prescriptionService;

    @GetMapping
    public List<Prescription> findAll(){
        return prescriptionService.findAll();
    }

    @GetMapping("/{id}")
    public Prescription findById(@PathVariable long id){
        return prescriptionService.findById(id);
    }

    @PostMapping
    public Prescription add(@RequestBody Prescription prescription){
        return prescriptionService.add(prescription);
    }

    @PutMapping
    public Prescription update(@RequestBody Prescription prescription){
        return prescriptionService.update(prescription);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable long id){
        return prescriptionService.delete(id);
    }
}
