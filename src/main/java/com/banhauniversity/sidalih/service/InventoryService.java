package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.dto.MedicineStatus;
import com.banhauniversity.sidalih.entity.Inventory;
import com.banhauniversity.sidalih.entity.Notification;
import com.banhauniversity.sidalih.entity.OrderMedicine;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.InventoryRepository;
import com.banhauniversity.sidalih.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class InventoryService {

    @Autowired private InventoryRepository inventoryRepository;



    public List<Inventory> findALl(){
        return inventoryRepository.findAll();
    }

    public Inventory add(Inventory inventory){
        return inventoryRepository.save(inventory);
    }




    public MedicineStatus Status(long id){
        return inventoryRepository.medicineStatus(id);
    }

    public void update(Inventory inventory,long amount){
        if (amount<0 || inventoryRepository.findById(inventory.getId()).get().getAmount()+amount>inventory.getOrderMedicine().getAmount()){
            throw new CustomException(ExceptionMessage.Not_Enough_Amount);
        }

        inventory.setAmount(inventoryRepository.findById(inventory.getId()).get().getAmount()+amount);

        inventoryRepository.save(inventory);

    }

    public List<MedicineStatus> inventoryOfItems(){
        return inventoryRepository.inventoryOfItems();
    }

    public Inventory findById(long id) {
        return inventoryRepository.findById(id).orElseThrow(()-> new CustomException(ExceptionMessage.ID_Not_Found));
    }

    public List<Inventory> findByMedicineId(long id) {
        return inventoryRepository.findAllByOrderMedicineMedicineBarcode(id);
    }

    public void deleteByOrderMedicineId(long id){
        deleteById(inventoryRepository.findByOrderMedicineId(id).get().getId());
    }

    public void deleteById(long id){
        inventoryRepository.deleteById(id);
    }

    public Optional<Inventory> findByOrderMedicineId(long id){
        return inventoryRepository.findByOrderMedicineId(id);
    }
}
