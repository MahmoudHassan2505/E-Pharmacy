package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.dto.MedicineStatus;
import com.banhauniversity.sidalih.dto.StatisticsDTO;
import com.banhauniversity.sidalih.repository.InventoryRepository;
import com.banhauniversity.sidalih.repository.UseageMedicineRepository;
import com.banhauniversity.sidalih.repository.UseageRepository;
import com.banhauniversity.sidalih.service.DashboardService;
import com.banhauniversity.sidalih.service.InventoryService;
import com.banhauniversity.sidalih.service.OrderMedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/dashboard")
public class DashboardController {

    @Autowired
    OrderMedicineService orderMedicineService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/status/{id}")
    public MedicineStatus getMedicineStatus(@PathVariable long id){
//        return orderMedicineService.getMedicineStatus();
        return inventoryService.Status(id);
    }

    @GetMapping("/statistics")
    public StatisticsDTO getStatistics(@RequestParam("year") int year){
        return dashboardService.getStatistics(year);
    }

}
