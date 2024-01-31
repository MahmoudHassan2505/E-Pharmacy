package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.dto.MedicineStatus;
import com.banhauniversity.sidalih.entity.Useage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface UseageRepository extends JpaRepository<Useage,Long> {

    @Query("select count(x.prescription.id) from Useage as x where x.prescription.id =?1 and function('to_char',x.date,'yyyy-mm') =?2")
    int useageTimes(long id, String currentDate);
}
