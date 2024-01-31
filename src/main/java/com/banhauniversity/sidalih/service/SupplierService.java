package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.entity.Medicine;
import com.banhauniversity.sidalih.entity.MedicineCategory;
import com.banhauniversity.sidalih.entity.Supplier;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    public List<Supplier> findAll(){
        return supplierRepository.findAll();
    }

    public Supplier findById(long id){
        supplierRepository.findById(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        return supplierRepository.findById(id).get();
    }

    public Supplier add(Supplier supplier){
        supplierRepository.findById(supplier.getId()).ifPresent((a)->{
            throw new CustomException(ExceptionMessage.ID_is_Exist);
        });

        return supplierRepository.save(supplier);
    }

    public void delete(long id){
        supplierRepository.findById(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        supplierRepository.deleteById(id);
    }

    public Supplier update(Supplier supplier){
        supplierRepository.findById(supplier.getId()).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        return supplierRepository.save(supplier);

    }
}
