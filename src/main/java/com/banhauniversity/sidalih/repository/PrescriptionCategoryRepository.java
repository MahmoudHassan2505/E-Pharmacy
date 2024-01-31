package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.entity.PrescriptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionCategoryRepository extends JpaRepository<PrescriptionCategory,Long> {

}
