package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.dto.MedicineStatus;
import com.banhauniversity.sidalih.dto.Sales;
import com.banhauniversity.sidalih.entity.Inventory;
import com.banhauniversity.sidalih.entity.Medicine;
import com.banhauniversity.sidalih.repository.UseageRepository;
import com.banhauniversity.sidalih.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/pharmacy/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired private UseageRepository useageRepository;

    @GetMapping
    public List<Inventory> findAll(){
        return inventoryService.findALl();
    }

    @GetMapping("/all")
    public List<MedicineStatus> inventoryOfItems(){
        return inventoryService.inventoryOfItems();
    }

    @GetMapping("/{id}")
    public List<Inventory> findByMedicineId(@PathVariable long id){
        return inventoryService.findByMedicineId(id);
    }

    @GetMapping("/sales")
    public List<Sales> sales(@RequestParam int year, @RequestParam int month){
        return useageRepository.findMedicineAmountByMonthAndYear(month,year);
    }
}
