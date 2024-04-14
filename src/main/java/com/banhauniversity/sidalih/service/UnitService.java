package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.entity.Unit;
import com.banhauniversity.sidalih.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;


    public List<Unit> findAllByType(long id){
        return unitRepository.findAllByType(id);
    }

    public Unit add(String name, long type) {
        return unitRepository.save(Unit.builder().name(name).type(type).build());
    }

    public void deleteById(long id) {
        unitRepository.deleteById(id);
    }
}
