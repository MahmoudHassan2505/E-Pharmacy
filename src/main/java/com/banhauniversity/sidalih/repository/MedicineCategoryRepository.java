package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.entity.MedicineCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineCategoryRepository extends JpaRepository<MedicineCategory,Long> {
}
