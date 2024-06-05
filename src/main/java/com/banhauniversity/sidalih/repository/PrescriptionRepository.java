package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.dto.MedicineStatus;
import com.banhauniversity.sidalih.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {

    List<Prescription> findAllByPrsPrescriptionCategoryId(long id);

}
