package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.dto.MedicineStatus;
import com.banhauniversity.sidalih.entity.OrderMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface OrderMedicineRepository  extends JpaRepository<OrderMedicine,Long> {
    @Query("select new com.banhauniversity.sidalih.dto.MedicineStatus(x.medicine  , sum(x.amount)) from OrderMedicine as x group by x.medicine")
    List<MedicineStatus> medicineStatus();
}

