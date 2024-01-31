package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.entity.UseageMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UseageMedicineRepository extends JpaRepository<UseageMedicine,Long> {
}
