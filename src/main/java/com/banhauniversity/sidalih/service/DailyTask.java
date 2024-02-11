package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.entity.Inventory;
import com.banhauniversity.sidalih.entity.Notification;
import com.banhauniversity.sidalih.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class DailyTask {

    @Autowired private InventoryService inventoryService;
    @Autowired private NotificationRepository notificationRepository;

    @Scheduled(cron = "0 30 17 * * ?")
    public void executeDailyTask() {
        List<Inventory> inventoryList = inventoryService.findALl();

        LocalDate currentDate = LocalDate.now();

        // Format the date into "yyyy-MM-dd" format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateString = currentDate.format(formatter);
        Date formattedDate = java.sql.Date.valueOf(formattedDateString);

        inventoryList.forEach(inventory -> {
            if(inventory.getOrderMedicine().getAmount()-inventory.getAmount()>0){
                if(formattedDate.compareTo(inventory.getOrderMedicine().getMedicine().getAlertexpired())>=0){
                    notificationRepository.save(new Notification("أوشك علي الانتهاء",inventory.getOrderMedicine().getMedicine()));
                }else if(formattedDate.compareTo(inventory.getOrderMedicine().getExpirydate())>=0){
                    notificationRepository.save(new Notification("انتهت صلاحية الدواء",inventory.getOrderMedicine().getMedicine()));
                }
            }
        });
    }
}
