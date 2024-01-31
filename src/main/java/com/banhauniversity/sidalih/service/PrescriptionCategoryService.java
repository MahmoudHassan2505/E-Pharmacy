package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.entity.Disease;
import com.banhauniversity.sidalih.entity.PrescriptionCategory;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.PrescriptionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionCategoryService {

    @Autowired
    private PrescriptionCategoryRepository prescriptionCategoryRepository;


    public List<PrescriptionCategory> findAll(){
        return prescriptionCategoryRepository.findAll();
    }

    public PrescriptionCategory add(PrescriptionCategory prescriptionCategory){
        return prescriptionCategoryRepository.save(prescriptionCategory);
    }

    public PrescriptionCategory update(PrescriptionCategory prescriptionCategory) {
        prescriptionCategoryRepository.findById(prescriptionCategory.getId()).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        return prescriptionCategoryRepository.save(prescriptionCategory);
    }

    public void delete(long id) {
        prescriptionCategoryRepository.deleteById(id);
    }
}
