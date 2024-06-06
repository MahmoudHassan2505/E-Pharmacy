package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.dto.*;
import com.banhauniversity.sidalih.entity.*;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.CollegeUseagesMedicineRepository;
import com.banhauniversity.sidalih.repository.CollegeUseagesRepository;
import com.banhauniversity.sidalih.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollegeUseageService {

    @Autowired private CollegeUseagesRepository collegeUseagesRepository;
    @Autowired private CollegeUseagesMedicineRepository collegeUseagesMedicineRepository;
    @Autowired private InventoryService inventoryService;
    @Autowired private InventoryRepository inventoryRepository;


    public List<CollegeUseagesDto> findAll(){

        List<CollegeUseages> collegeUseagesList = collegeUseagesRepository.findAll();
        int totalPrice;
        List<CollegeUseagesDto> collegeUseagesDtos = new ArrayList<>();

        for (CollegeUseages collegeUseages:collegeUseagesList) {
            totalPrice=0;
            for (CollegeUseageMedicine collegeUseageMedicine:collegeUseages.getCollegeUseagesMedicines()) {
                totalPrice+= collegeUseageMedicine.getInventory().getOrderMedicine().getPrice()* collegeUseageMedicine.getAmount();
            }
            collegeUseagesDtos.add(new CollegeUseagesDto(collegeUseages.getId(),collegeUseages.getDate(),collegeUseages.getCollegeName(),collegeUseages.getCollegeUseagesMedicines(),totalPrice));
        }
        return collegeUseagesDtos;
    }

    public CollegeUseagesDto findById(long id){
        int totalPrice=0;
        CollegeUseages collegeUseages = collegeUseagesRepository.findById(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        for (CollegeUseageMedicine collegeUseageMedicine:collegeUseages.getCollegeUseagesMedicines()) {
            totalPrice+= collegeUseageMedicine.getInventory().getOrderMedicine().getPrice()* collegeUseageMedicine.getAmount();
        }
        return new CollegeUseagesDto(collegeUseages.getId(),collegeUseages.getDate(),collegeUseages.getCollegeName(),collegeUseages.getCollegeUseagesMedicines(),totalPrice);
    }

    public CollegeUseages add(CollegeUseages collegeUseages){
        System.out.println(collegeUseages.getCollegeUseagesMedicines().size());
        ValidateMedicine(collegeUseages.getCollegeUseagesMedicines());

        collegeUseagesRepository.save(collegeUseages);
        System.out.println(collegeUseages.getCollegeUseagesMedicines().size());
        List<CollegeUseageMedicine> collegeUseageMedicines = collegeUseages.getCollegeUseagesMedicines();

        collegeUseageMedicines.forEach(inventoryDto -> {

            collegeUseagesMedicineRepository.save(new CollegeUseageMedicine(inventoryDto.getAmount(),collegeUseages,inventoryDto.getInventory()));
            updateInventory(collegeUseages.getCollegeUseagesMedicines());

        });

        return collegeUseagesRepository.findById(collegeUseages.getId()).orElseThrow(()-> new CustomException(ExceptionMessage.ID_Not_Found));
    }



    public boolean delete(long id) {
    CollegeUseages collegeUseages = collegeUseagesRepository.findById(id).orElseThrow(()-> new CustomException(ExceptionMessage.ID_Not_Found));

        collegeUseages.getCollegeUseagesMedicines().forEach(collegeUseageMedicine -> {
            //update inventory before deleting
            Inventory inventory = inventoryService.findById(collegeUseageMedicine.getInventory().getId());
            inventory.setAmount(inventory.getAmount()- collegeUseageMedicine.getAmount());
            inventoryRepository.save(inventory);

            collegeUseagesMedicineRepository.deleteById(collegeUseageMedicine.getId());
        });

        collegeUseagesRepository.deleteById(id);
        return true;
    }


    private void ValidateMedicine(List<CollegeUseageMedicine> collegeUseageMedicine) {
        collegeUseageMedicine.forEach((x)-> {
            Inventory inventory = inventoryService.findById(x.getInventory().getId());
            if (x.getAmount() > (inventory.getOrderMedicine().getAmount()-inventory.getAmount())) {
                throw new CustomException(ExceptionMessage.Not_Enough_Amount);
            }
        });

    }


    private void updateInventory(List<CollegeUseageMedicine> collegeUseageMedicines) {
        collegeUseageMedicines.forEach( useageMedicine -> {
            inventoryService.update(useageMedicine.getInventory(),useageMedicine.getAmount());
        });
    }
}
