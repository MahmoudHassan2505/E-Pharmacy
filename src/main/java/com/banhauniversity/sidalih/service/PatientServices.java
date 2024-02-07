package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.entity.Patient;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServices {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> findAll(){
        return patientRepository.findAll();
    }

    public Patient add(Patient patient){
        return patientRepository.save(patient);
    }

    public Patient update(Patient patient){
        patientRepository.findById(patient.getNational_id()).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));

        return patientRepository.save(patient);
    }

    public Patient findById(long id){
        return patientRepository.findById(id).orElseThrow(()-> new CustomException(ExceptionMessage.ID_Not_Found));
    }

    public void delete(long id){
        patientRepository.deleteById(id);
    }

    public List<Patient> findByType(boolean isChronic) {
        return patientRepository.findAllByChronic(isChronic);
    }
}
