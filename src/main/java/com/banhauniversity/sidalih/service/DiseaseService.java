package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.entity.Disease;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiseaseService {

    @Autowired
    private DiseaseRepository diseaseRepository;

    public Disease add(Disease disease){
        return diseaseRepository.save(disease);
    }

    public List<Disease> findAll(){
        return diseaseRepository.findAll();
    }

    public Disease update(Disease disease) {
        diseaseRepository.findById(disease.getId()).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        return diseaseRepository.save(disease);
    }
}
