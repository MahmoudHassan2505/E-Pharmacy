package com.banhauniversity.sidalih.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long supplyrequest;
    private long deliveryrequest;
    private Date dateofsupply;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderMedicine> orderMedicines;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSupplyrequest() {
        return supplyrequest;
    }

    public void setSupplyrequest(long supplyrequest) {
        this.supplyrequest = supplyrequest;
    }

    public long getDeliveryrequest() {
        return deliveryrequest;
    }

    public void setDeliveryrequest(long deliveryrequest) {
        this.deliveryrequest = deliveryrequest;
    }

    public Date getDateofsupply() {
        return dateofsupply;
    }

    public void setDateofsupply(Date dateofsupply) {
        this.dateofsupply = dateofsupply;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<OrderMedicine> getOrderMedicines() {
        return orderMedicines;
    }

    public void setOrderMedicines(List<OrderMedicine> orderMedicines) {
        this.orderMedicines = orderMedicines;
    }
}
