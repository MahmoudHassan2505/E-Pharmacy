package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.entity.Useage;
import com.banhauniversity.sidalih.repository.UseageRepository;
import com.banhauniversity.sidalih.service.UseageService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/useages")
public class UseageController {

    @Autowired private UseageService useageService;

    @GetMapping
    public List<Useage> findAll(){
        return useageService.findAll();
    }

    @GetMapping("/{id}")
    public Useage findById(@PathVariable long id){
        return useageService.findById(id);
    }

    @PostMapping()
    public Useage add(@RequestBody Useage useage,@RequestParam("isChronic") boolean type){
        return useageService.add(useage,type);
    }

    @PutMapping
    public Useage update(@RequestBody Useage useage){
        return useageService.update(useage);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable long id){
        useageService.delete(id);
        return true;
    }
}
