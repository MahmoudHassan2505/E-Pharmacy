package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.entity.Medicine;
import com.banhauniversity.sidalih.entity.MedicineCategory;
import com.banhauniversity.sidalih.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public List<Medicine> findAll(){
        return medicineService.findAll();
    }

    @GetMapping("/{id}")
    public Medicine findById(@PathVariable long id){
        return medicineService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Medicine add(@RequestBody Medicine medicine){
        return medicineService.add(medicine);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        medicineService.delete(id);
    }

    @PutMapping
    public Medicine update(@RequestBody Medicine medicine){
        return medicineService.update(medicine);
    }

    @GetMapping("/category/{id}")
    public List<Medicine> findByCategory(@PathVariable Long id){
        return medicineService.findByCategory(id);
    }

}
