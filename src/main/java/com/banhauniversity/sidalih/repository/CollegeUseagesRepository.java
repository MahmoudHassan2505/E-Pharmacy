package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.entity.CollegeUseages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeUseagesRepository extends JpaRepository<CollegeUseages,Long> {
}
