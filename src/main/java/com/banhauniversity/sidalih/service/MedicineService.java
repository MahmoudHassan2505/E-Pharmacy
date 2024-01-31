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
        medicineRepository.findById(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        return medicineRepository.findById(id).get();
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
        medicineRepository.findById(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        medicineRepository.deleteById(id);
    }

    public Medicine update(Medicine medicine){
        medicineRepository.findById(medicine.getId()).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        return medicineRepository.save(medicine);

    }
}
