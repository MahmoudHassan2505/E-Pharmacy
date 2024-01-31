package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.entity.Disease;
import com.banhauniversity.sidalih.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/diseases")
public class DiseaseController {

    @Autowired
    private DiseaseService diseaseService;

    @GetMapping
    public List<Disease> findAll(){
        return diseaseService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Disease add(@RequestBody Disease disease){
        return diseaseService.add(disease);
    }

    @PutMapping
    public Disease update(@RequestBody Disease disease){
        return diseaseService.update(disease);
    }
}
