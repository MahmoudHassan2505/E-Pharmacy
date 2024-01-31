package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.dto.MedicineStatus;
import com.banhauniversity.sidalih.entity.Inventory;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service
public class InventoryService {

    @Autowired private InventoryRepository inventoryRepository;

    public List<Inventory> findALl(){
        return inventoryRepository.findAll();
    }

    public Inventory add(Inventory inventory){
        return inventoryRepository.save(inventory);
    }
    public List<Inventory>findByMedicineId(long id){
        return inventoryRepository.findByMedicineId(id);
    }

    public Inventory findByIdAndExpireDate(long id, Date expireDate){
        return inventoryRepository.findByMedicineIdAndExpireDate(id,expireDate);
    }

    public MedicineStatus Status(long id){
        return inventoryRepository.medicineStatus(id);
    }

    public void update(long id,long amount){
        if (amount<0){
            throw new CustomException(ExceptionMessage.Not_Enough_Amount);
        }

        List<Inventory> inventoryList = inventoryRepository.findByMedicineId(id);
        Collections.sort(inventoryList, new Comparator<Inventory>() {
            public int compare(Inventory o1, Inventory o2) {
                return o1.getExpireDate().compareTo(o2.getExpireDate());
            }
        });

        Inventory inventory = inventoryList.get(0);
        inventory.setAmount(inventory.getAmount()-amount);
        inventoryRepository.save(inventory);

    }
}
