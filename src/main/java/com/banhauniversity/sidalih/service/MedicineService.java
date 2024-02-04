package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.entity.Medicine;
import com.banhauniversity.sidalih.entity.MedicineCategory;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {
    @Autowired
    MedicineRepository medicineRepository;

    public List<Medicine> findAll(){
        return medicineRepository.findAll();
    }

    public Medicine findById(long id){
        medicineRepository.findByBarcode(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        return medicineRepository.findByBarcode(id).get();
    }

    public List<Medicine> findByCategory(Long id){
        return medicineRepository.findAllByMedicineCategoryId(id);
    }

    public Medicine add(Medicine medicine){
        medicineRepository.findById(medicine.getId()).ifPresent((a)->{
            throw new CustomException(ExceptionMessage.ID_is_Exist);
        });

        return medicineRepository.save(medicine);
    }

    public void delete(long id){
        long deleteMedicine = medicineRepository.findByBarcode(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found)).getId();
        medicineRepository.deleteById(deleteMedicine);
    }

    public Medicine update(Medicine medicine){
        long updateMedicine = medicineRepository.findByBarcode(medicine.getBarcode()).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found)).getId();
        medicine.setId(updateMedicine);
        return medicineRepository.save(medicine);

    }
}
