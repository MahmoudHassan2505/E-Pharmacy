package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.entity.Prescription;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public List<Prescription> findAll(){
        return prescriptionRepository.findAll();
    }

    public Prescription findById(long id){
        return prescriptionRepository.findById(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
    }

    public Prescription add(Prescription prescription){
        return prescriptionRepository.save(prescription);
    }

    public Prescription update(Prescription prescription){
        prescriptionRepository.findById(prescription.getId()).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        return prescriptionRepository.save(prescription);
    }

    public boolean delete(long id){
        prescriptionRepository.deleteById(id);
        return true;
    }

    public List<Prescription> findByType(boolean isChronic) {
        return prescriptionRepository.findAllByPrsPrescriptionCategoryId(isChronic? 1:2);
    }
}
