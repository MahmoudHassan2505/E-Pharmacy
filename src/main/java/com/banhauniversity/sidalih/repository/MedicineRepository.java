package com.banhauniversity.sidalih.repository;
import com.banhauniversity.sidalih.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine,Long> {

    List<Medicine> findAllByMedicineCategoryId(long id);

    Optional<Medicine> findByBarcode(long id);


}
