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
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class DailyTask {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private NotificationRepository notificationRepository;

//    @Scheduled(cron = "0 0 5 * * ?")
    @Scheduled(fixedDelay = 10000)
    public void executeDailyTask() {
        List<Inventory> inventoryList = inventoryService.findALl();

        inventoryList.forEach(inventory -> {
            
            if(notificationRepository.findByOrderid(inventory.getOrderMedicine().getId()).isEmpty() && calculateDateDifferenceFromToday(inventory.getOrderMedicine().getExpirydate().toString()) <0 ) {
                notificationRepository.save(new Notification("انتهت صلاحية الدواء", inventory.getOrderMedicine().getId(), inventory.getOrderMedicine().getMedicine()));
            } else if (notificationRepository.findByOrderid(inventory.getOrderMedicine().getId()).isEmpty() && calculateDateDifferenceFromToday(inventory.getOrderMedicine().getExpirydate().toString()) < inventory.getOrderMedicine().getMedicine().getAlertexpired()) {
                notificationRepository.save(new Notification("أوشك علي الانتهاء", inventory.getOrderMedicine().getId(), inventory.getOrderMedicine().getMedicine()));
            }

        });
    }


    @Scheduled(fixedDelay = 10000)
    public void updateNotifications() {

        inventoryService.inventoryOfItems().forEach(medicineStatus -> {
            if (medicineStatus.getAmount() <= medicineStatus.getMedicine().getAlertamount() && notificationRepository.findByMedicineAndMessage(medicineStatus.getMedicine(),"أوشك علي النفاد").isEmpty()) {
                notificationRepository.save(new Notification("أوشك علي النفاد", 0,medicineStatus.getMedicine()));
            }
        });


    }



        public static long calculateDateDifferenceFromToday(String date) {
            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Parse the input date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate specifiedDate = LocalDate.parse(date, formatter);

            // Calculate the difference in days
            long difference = ChronoUnit.DAYS.between(currentDate, specifiedDate);

            return difference;
        }

        public static void main(String[] args) {
            String specifiedDate = "2024-03-10"; // Example date
            long difference = calculateDateDifferenceFromToday(specifiedDate);
            System.out.println("Difference in days: " + difference);
        }


}
