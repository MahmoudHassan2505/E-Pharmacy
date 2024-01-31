package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.repository.UseageMedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UseageMedicineService {

    @Autowired private UseageMedicineRepository useageMedicineRepository;

}
