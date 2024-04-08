package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.dto.CollegeUseagesDto;
import com.banhauniversity.sidalih.entity.CollegeUseages;
import com.banhauniversity.sidalih.service.CollegeUseageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/collegeuseages")
public class CollegeUseageController {

    @Autowired private CollegeUseageService collegeUseageService;

    @GetMapping
    public List<CollegeUseagesDto> findAll(){
        return collegeUseageService.findAll();
    }

    @PostMapping
    public CollegeUseages add(@RequestBody CollegeUseages collegeUseages){
        System.out.println(collegeUseages.getId());
        return collegeUseageService.add(collegeUseages);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        collegeUseageService.delete(id);
    }
}
