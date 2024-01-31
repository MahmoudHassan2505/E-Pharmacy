package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.entity.MedicineCategory;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.MedicineCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineCategoryService {

    @Autowired
    MedicineCategoryRepository medicineCategoryRepository;

    public List<MedicineCategory> findAll(){
       return medicineCategoryRepository.findAll();
    }

    public MedicineCategory findById(long id){
        medicineCategoryRepository.findById(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        return medicineCategoryRepository.findById(id).get();
    }

    public MedicineCategory add(MedicineCategory medicineCategory){
        medicineCategoryRepository.findById(medicineCategory.getId()).ifPresent((a)->{
            throw new CustomException(ExceptionMessage.ID_is_Exist);
        });

        return medicineCategoryRepository.save(medicineCategory);
    }

    public void delete(long id){
        medicineCategoryRepository.findById(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        medicineCategoryRepository.deleteById(id);
    }

    public MedicineCategory update(MedicineCategory medicineCategory){
        medicineCategoryRepository.findById(medicineCategory.getId()).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        return medicineCategoryRepository.save(medicineCategory);

    }
}
