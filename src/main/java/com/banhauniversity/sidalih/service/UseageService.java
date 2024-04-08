package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.dto.AddUsage;
import com.banhauniversity.sidalih.dto.InventoryDto;
import com.banhauniversity.sidalih.dto.UseagesDTO;
import com.banhauniversity.sidalih.entity.*;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.InventoryRepository;
import com.banhauniversity.sidalih.repository.NotificationRepository;
import com.banhauniversity.sidalih.repository.UseageMedicineRepository;
import com.banhauniversity.sidalih.repository.UseageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UseageService {

    @Autowired private UseageRepository useageRepository;
    @Autowired private UseageMedicineRepository useageMedicineRepository;
    @Autowired private InventoryService inventoryService;
    @Autowired private InventoryRepository inventoryRepository;

    public List<UseagesDTO> findAll(){

        List<Useage> useages = useageRepository.findAll();
        int totalPrice;
        List<UseagesDTO> useagesDTOS = new ArrayList<>();

        for (Useage useage:useages) {
            totalPrice=0;
            for (UseageMedicine useageMedicine:useage.getUseageMedicines()) {
                totalPrice+= useageMedicine.getPrice()* useageMedicine.getAmount();
            }
            useagesDTOS.add(new UseagesDTO(useage.getId(),useage.getDate(),useage.getPrescription(),useage.getUseageMedicines(),totalPrice));
        }
        return useagesDTOS;
    }

    public UseagesDTO findById(long id){
        int totalPrice=0;
        Useage useage = useageRepository.findById(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        for (UseageMedicine useageMedicine:useage.getUseageMedicines()) {
            totalPrice+= useageMedicine.getPrice()* useageMedicine.getAmount();
        }
        return new UseagesDTO(useage.getId(),useage.getDate(),useage.getPrescription(),useage.getUseageMedicines(),totalPrice);
    }

    public Useage add(AddUsage addUsage){


        boolean isChronic = addUsage.getPrescription().getPrsPrescriptionCategory().getId()==1;


        ValidateMedicine(addUsage);
        ValidatePrescription(addUsage);
        if (!ValidatePrescriptionTimes(addUsage,isChronic)){
            throw new CustomException(ExceptionMessage.Patient_Exceeded_Times);
        }

        List<UseageMedicine> useageMedicines = new ArrayList<>();

        Useage savedUseage = useageRepository.saveAndFlush(new Useage(addUsage.getDate(),addUsage.getPrescription(), useageMedicines));

        addUsage.getInventoryDto().forEach(inventoryDto -> {
            useageMedicineRepository.saveAndFlush(new UseageMedicine(inventoryDto.getAmountNeeded(),inventoryDto.getInventory().getOrderMedicine().getPrice(),savedUseage,inventoryDto.getInventory().getOrderMedicine().getMedicine(),inventoryDto.getInventory()));
            updateInventory(addUsage.getInventoryDto());

        });

        return useageRepository.findById(savedUseage.getId()).orElseThrow(()-> new CustomException(ExceptionMessage.ID_Not_Found));
    }


    public Useage update(Useage useage){
        useageRepository.findById(useage.getId()).ifPresent((x)-> new CustomException(ExceptionMessage.ID_Not_Found));
        return useageRepository.save(useage);
    }

    public boolean delete(long id){
        Useage useage = useageRepository.findById(id).orElseThrow(()-> new CustomException(ExceptionMessage.ID_Not_Found));

        useage.getUseageMedicines().forEach(useageMedicine -> {
            //update inventory before deleting
            Inventory inventory = inventoryRepository.findById(useageMedicine.getInventory().getId()).get();
            inventory.setAmount(inventory.getAmount()- useageMedicine.getAmount());
            inventoryRepository.save(inventory);

            useageMedicineRepository.deleteById(useageMedicine.getId());
        });

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
        List<Useage> patientUseageDetails = useageRepository.findAllByPrescriptionPatientNationalid(addUsage.getPrescription().getPatient().getNationalid());

        if(isChronic){
            //for chronic
            int useageTime = (int)patientUseageDetails.stream().filter(useage -> useage.getPrescription().getPrsPrescriptionCategory().getId()==1).filter(useage -> {
                LocalDate useageDate = useage.getDate().toLocalDate();
                YearMonth currentYearMonth = YearMonth.now();
                YearMonth useageYearMonth = YearMonth.of(useageDate.getYear(), useageDate.getMonth());
                return currentYearMonth.equals(useageYearMonth);
            }).count();

            if (useageTime!=0)
                return false;
            else
                return true;
        }else {
            //for non-chronic
            int useageTime = (int)patientUseageDetails.stream().filter(useage -> useage.getPrescription().getPrsPrescriptionCategory().getId()==2).filter(useage -> {
                LocalDate useageDate = useage.getDate().toLocalDate();
                YearMonth currentYearMonth = YearMonth.now();
                YearMonth useageYearMonth = YearMonth.of(useageDate.getYear(), useageDate.getMonth());
                return currentYearMonth.equals(useageYearMonth);
            }).count();

            if (useageTime>2)
                return false;
            else
                return true;
        }
    }

    public List<Useage> findByMedicineName(String medicineName) {
        return useageRepository.findAllByUseageMedicinesMedicineName(medicineName);
    }

    public List<Useage> findAllByCollegeNameAndDate(String collegeName,int month,int year){



           return useageRepository.findAllByCollegeNameAndMonthAndYear(collegeName,month,year);

    }





}