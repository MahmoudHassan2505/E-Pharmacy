package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.dto.MedicineStatus;
import com.banhauniversity.sidalih.entity.Inventory;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    List<Inventory>findByMedicineId(long id);
    List<Inventory>findByMedicineBarcode(long id);
    Inventory findByMedicineIdAndExpireDate(long id, Date expireDate);

    @Query("select new com.banhauniversity.sidalih.dto.MedicineStatus(x.medicine  , sum(x.amount)) from Inventory as x where x.medicine.id =?1 group by x.medicine")
    MedicineStatus medicineStatus(long id);

    @Query("select new com.banhauniversity.sidalih.dto.MedicineStatus(x.medicine  , sum(x.amount)) from Inventory as x group by x.medicine")
    List<MedicineStatus> inventoryOfItems();
}
