package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.entity.MedicineCategory;
import com.banhauniversity.sidalih.service.MedicineCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/medicinecategories")
public class MedicineCategoryController {

    @Autowired
    MedicineCategoryService medicineCategoryService;

    @GetMapping
    public List<MedicineCategory> findAll(){
        return medicineCategoryService.findAll();
    }

    @GetMapping("/{id}")
    public MedicineCategory findById(@PathVariable long id){
        return medicineCategoryService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicineCategory add(@RequestBody MedicineCategory medicineCategory){
        return medicineCategoryService.add(medicineCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id){
        medicineCategoryService.delete(id);
    }

    @PutMapping()
    public MedicineCategory update(@RequestBody MedicineCategory medicineCategory){
        return medicineCategoryService.update(medicineCategory);
    }


}
