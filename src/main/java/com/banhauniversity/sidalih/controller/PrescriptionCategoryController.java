package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.entity.PrescriptionCategory;
import com.banhauniversity.sidalih.service.PrescriptionCategoryService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/prescriptioncategories")
public class PrescriptionCategoryController {

    @Autowired
    private PrescriptionCategoryService prescriptionCategoryService;

    @GetMapping
    public List<PrescriptionCategory> findAll(){
        return prescriptionCategoryService.findAll();
    }

    @PostMapping
    public PrescriptionCategory add(@RequestBody  PrescriptionCategory prescriptionCategory){
        return prescriptionCategoryService.add(prescriptionCategory);
    }

    @PutMapping
    public PrescriptionCategory update(@RequestBody  PrescriptionCategory prescriptionCategory){
        return prescriptionCategoryService.update(prescriptionCategory);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable long id){
        prescriptionCategoryService.delete(id);
        return true;
    }
}
