package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.entity.Inventory;
import com.banhauniversity.sidalih.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<Inventory> findAll(){
        return inventoryService.findALl();
    }

    @GetMapping("/{id}")
    public List<Inventory> findByMedicineId(@PathVariable long id){
        return inventoryService.findByMedicineId(id);
    }
}
