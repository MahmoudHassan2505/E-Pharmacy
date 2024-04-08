package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.entity.CollegeUseageMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeUseagesMedicineRepository extends JpaRepository<CollegeUseageMedicine,Long> {
}
