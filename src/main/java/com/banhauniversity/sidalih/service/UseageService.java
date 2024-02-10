package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.dto.AddUsage;
import com.banhauniversity.sidalih.dto.InventoryDto;
import com.banhauniversity.sidalih.entity.*;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.InventoryRepository;
import com.banhauniversity.sidalih.repository.NotificationRepository;
import com.banhauniversity.sidalih.repository.UseageMedicineRepository;
import com.banhauniversity.sidalih.repository.UseageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class UseageService {

    @Autowired private UseageRepository useageRepository;
    @Autowired private UseageMedicineRepository useageMedicineRepository;
    @Autowired private InventoryService inventoryService;
    @Autowired private NotificationRepository notificationRepository;
    @Autowired private InventoryRepository inventoryRepository;

    public List<Useage> findAll(){
        return useageRepository.findAll();
    }

    public Useage findById(long id){
        return useageRepository.findById(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
    }

    public Useage add(AddUsage addUsage){


        boolean isChronic = addUsage.getPrescription().getPrsPrescriptionCategory().getId()==1;


        ValidateMedicine(addUsage);
        ValidatePrescription(addUsage);
        ValidatePrescriptionTimes(addUsage,isChronic);

        List<UseageMedicine> useageMedicines = new ArrayList<>();

        Useage savedUseage = useageRepository.save(new Useage(addUsage.getDate(),addUsage.getPrescription(), useageMedicines));

        addUsage.getInventoryDto().forEach(inventoryDto -> {
            useageMedicineRepository.save(new UseageMedicine(inventoryDto.getAmountNeeded(),inventoryDto.getInventory().getOrderMedicine().getPrice(),savedUseage,inventoryDto.getInventory().getOrderMedicine().getMedicine()));
            updateInventory(addUsage.getInventoryDto());

        });


        updateNotifications(savedUseage.getId());

        return useageRepository.findById(savedUseage.getId()).orElseThrow(()-> new CustomException(ExceptionMessage.ID_Not_Found));
    }


    public Useage update(Useage useage){
        useageRepository.findById(useage.getId()).ifPresent((x)-> new CustomException(ExceptionMessage.ID_Not_Found));
        return useageRepository.save(useage);
    }

    public boolean delete(long id){
        useageRepository.deleteById(id);
        return true;
    }


    private void ValidateMedicine(AddUsage addUsage) {
        addUsage.getInventoryDto().forEach((inventoryDto)-> {
            Inventory inventory = inventoryService.findById(inventoryDto.getInventory().getId());
        if (inventoryDto.getAmountNeeded() > (inventory.getOrderMedicine().getAmount()-inventory.getAmount())) {
            throw new CustomException(ExceptionMessage.Not_Enough_Amount);
        }
    });

    }

    private void ValidatePrescription(AddUsage addUsage) {
        long total=0;

        for (InventoryDto inventoryDto:addUsage.getInventoryDto()) {
            total+=inventoryDto.getAmountNeeded()*inventoryDto.getInventory().getOrderMedicine().getPrice();
        }
        if(total>350){
            throw new CustomException(ExceptionMessage.Patient_Exceeded_Price_Limit);
        }
    }

    private void updateInventory(List<InventoryDto> inventoryDtos) {
        inventoryDtos.forEach( inventoryDto -> {
            inventoryService.update(inventoryDto.getInventory(),inventoryDto.getAmountNeeded());
        });
    }

    private boolean ValidatePrescriptionTimes(AddUsage addUsage,boolean isChronic) {
        String currentDate = String.valueOf(java.time.LocalDate.now().getYear());
        currentDate+="-";
        currentDate+=String.valueOf(java.time.LocalDate.now().getMonthValue());

        if(isChronic && useageRepository.useageTimes(addUsage.getPrescription().getId(),currentDate)==0){
            return true;
        }
        if (!isChronic&&useageRepository.useageTimes(addUsage.getPrescription().getId(),currentDate)<2) {
            return true;
        }
        throw new CustomException(ExceptionMessage.Patient_Exceeded_Useage_Limit);
    }

    public List<Useage> findByMedicineName(String medicineName) {
        return useageRepository.findAllByUseageMedicinesMedicineName(medicineName);
    }

    private void updateNotifications(Long id) {
        Useage useage = useageRepository.findById(id).orElseThrow(()-> new CustomException(ExceptionMessage.ID_Not_Found));
        System.out.println("hello3");
        useage.getUseageMedicines().forEach((medicine)->{
            if (medicine.getMedicine().getAlertamount() >= inventoryService.Status(medicine.getMedicine().getId()).getAmount()){
                notificationRepository.save(new Notification("أوشك علي النفاد",medicine.getMedicine()));
            }
        });
    }

}