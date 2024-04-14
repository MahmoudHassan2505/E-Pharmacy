package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.entity.Unit;
import com.banhauniversity.sidalih.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/unit")
public class UnitController {

    @Autowired private UnitService unitService;

    @GetMapping("/max")
    public List<Unit> findAllMax(){
        return unitService.findAllByType(1);
    }

    @GetMapping("/min")
    public List<Unit> findAllMin(){
        return unitService.findAllByType(0);
    }

    @PostMapping("/max")
    public Unit addMax(@RequestParam("name") String name){
        return unitService.add(name,1);
    }

    @PostMapping("/min")
    public Unit addMin(@RequestParam("name") String name){
        return unitService.add(name,0);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        unitService.deleteById(id);
    }
}
