package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.entity.Medicine;
import com.banhauniversity.sidalih.entity.Supplier;
import com.banhauniversity.sidalih.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/suppliers")
public class SupplierController {
    @Autowired
    SupplierService supplierService;

    @GetMapping
    public List<Supplier> findAll(){
        return supplierService.findAll();
    }

    @GetMapping("/{id}")
    public Supplier findById(@PathVariable long id){
        return supplierService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Supplier add(@RequestBody Supplier supplier){
        return supplierService.add(supplier);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id){
        supplierService.delete(id);
    }

    @PutMapping()
    public Supplier update(@RequestBody Supplier supplier){
        return supplierService.update(supplier);
    }
}
