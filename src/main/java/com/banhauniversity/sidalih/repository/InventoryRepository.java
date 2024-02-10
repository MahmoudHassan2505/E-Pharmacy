package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.dto.MedicineStatus;
import com.banhauniversity.sidalih.entity.Inventory;
import com.banhauniversity.sidalih.entity.OrderMedicine;
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

    List<Inventory> findAllByOrderMedicineMedicineBarcode(long barcode);

    @Query("select new com.banhauniversity.sidalih.dto.MedicineStatus(x.orderMedicine.medicine  , sum(x.amount)) from Inventory as x where x.orderMedicine.medicine.id =?1 group by x.orderMedicine.medicine")
    MedicineStatus medicineStatus(long id);

    @Query("select new com.banhauniversity.sidalih.dto.MedicineStatus(x.orderMedicine.medicine  , sum(x.amount)) from Inventory as x group by x.orderMedicine.medicine")
    List<MedicineStatus> inventoryOfItems();

    Inventory findByOrderMedicine(OrderMedicine orderMedicine);

    void deleteByOrderMedicine(OrderMedicine orderMedicine);
}
