package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.dto.MedicineStatus;
import com.banhauniversity.sidalih.dto.Reports;
import com.banhauniversity.sidalih.dto.Sales;
import com.banhauniversity.sidalih.entity.Prescription;
import com.banhauniversity.sidalih.entity.Useage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface UseageRepository extends JpaRepository<Useage,Long> {

    @Query("select count(x.prescription.id) from Useage as x where x.prescription.id =?1 and function('to_char',x.date,'yyyy-mm') =?2")
    int useageTimes(long id, String currentDate);

    @Query("SELECT new com.banhauniversity.sidalih.dto.Sales(m.name,m.barcode ,SUM(um.amount))" +
            "FROM UseageMedicine um " +
            "LEFT JOIN um.useage u " +
            "LEFT JOIN um.medicine m " +
            "WHERE EXTRACT(MONTH FROM u.date) = :month AND EXTRACT(YEAR FROM u.date) = :year " +
            "GROUP BY m.name,m.barcode")
    List<Sales> findMedicineAmountByMonthAndYear(@Param("month") int month, @Param("year") int year);

    List<Useage> findAllByUseageMedicinesMedicineName(String name);

    @Query("SELECT new com.banhauniversity.sidalih.dto.Reports(pa.collegeName, COUNT(u.id)) " +
            "FROM Useage u " +
            "LEFT JOIN u.prescription pr " +
            "LEFT JOIN pr.patient pa " +
            "WHERE EXTRACT(MONTH FROM u.date) = :month AND EXTRACT(YEAR FROM u.date) = :year " +
            "GROUP BY pa.collegeName")
    List<Reports>findCollegeSales(@Param("month") int month, @Param("year") int year);

    List<Useage> findAllByPrescriptionPatientNational_id(long id);
}
