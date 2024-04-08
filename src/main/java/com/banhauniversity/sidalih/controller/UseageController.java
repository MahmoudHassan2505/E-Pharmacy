package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.dto.AddUsage;
import com.banhauniversity.sidalih.dto.UseagesDTO;
import com.banhauniversity.sidalih.entity.Medicine;
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
    public List<UseagesDTO> findAll(){
        return useageService.findAll();
    }

    @GetMapping("/{id}")
    public UseagesDTO findById(@PathVariable long id){
        return useageService.findById(id);
    }

    @GetMapping("/medicine")
    public List<Useage> findByMedicineName(@RequestParam String medicineName){
        return useageService.findByMedicineName(medicineName);
    }

    @PostMapping()
    public Useage add(@RequestBody AddUsage addUsage){
        return useageService.add(addUsage);
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

    @GetMapping("/college")
    public List<Useage> findAllByCollegeNameAndDate(@RequestParam("collegeName") String collegeName,@RequestParam("month") int month,@RequestParam("year") int year){
        return useageService.findAllByCollegeNameAndDate(collegeName,month,year);
    }
}
