package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.dto.MedicineStatus;
import com.banhauniversity.sidalih.repository.OrderMedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMedicineService {
    @Autowired
    OrderMedicineRepository orderMedicineRepository;

    public List<MedicineStatus> getMedicineStatus(){
        return orderMedicineRepository.medicineStatus();
    }
}
