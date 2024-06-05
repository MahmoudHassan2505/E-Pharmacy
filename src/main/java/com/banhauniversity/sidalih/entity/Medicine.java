package com.banhauniversity.sidalih.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "medicine")
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long barcode;
    private String name;
    private String arabicname;
    private String dosageform;
    private String strength;
    private String activeingredient;
    private String manufacturer;
    private long alertamount;
    private long alertexpired;
    private String unit;


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private MedicineCategory medicineCategory;

    @OneToMany(mappedBy = "medicine",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderMedicine> orderMedicines;

    @OneToMany(mappedBy = "medicine",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UseageMedicine> useageMedicines;

    public Medicine(long id, long barcode, String name, String arabicname, String dosageform, String strength, String activeingredient, String manufacturer, long alertamount, long alertexpired, String unit, MedicineCategory medicineCategory, List<OrderMedicine> orderMedicines, List<UseageMedicine> useageMedicines) {
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.arabicname = arabicname;
        this.dosageform = dosageform;
        this.strength = strength;
        this.activeingredient = activeingredient;
        this.manufacturer = manufacturer;
        this.alertamount = alertamount;
        this.alertexpired = alertexpired;
        this.unit = unit;
        this.medicineCategory = medicineCategory;
        this.orderMedicines = orderMedicines;
        this.useageMedicines = useageMedicines;
    }
    public Medicine(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArabicname() {
        return arabicname;
    }

    public void setArabicname(String arabicname) {
        this.arabicname = arabicname;
    }

    public String getDosageform() {
        return dosageform;
    }

    public void setDosageform(String dosageform) {
        this.dosageform = dosageform;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getActiveingredient() {
        return activeingredient;
    }

    public void setActiveingredient(String activeingredient) {
        this.activeingredient = activeingredient;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public long getAlertamount() {
        return alertamount;
    }

    public void setAlertamount(long alertamount) {
        this.alertamount = alertamount;
    }

    public MedicineCategory getMedicineCategory() {
        return medicineCategory;
    }

    public void setMedicineCategory(MedicineCategory medicineCategory) {
        this.medicineCategory = medicineCategory;
    }

    public long getAlertexpired() {
        return alertexpired;
    }

    public void setAlertexpired(long alertexpired) {
        this.alertexpired = alertexpired;
    }

    public List<OrderMedicine> getOrderMedicines() {
        return orderMedicines;
    }

    public void setOrderMedicines(List<OrderMedicine> orderMedicines) {
        this.orderMedicines = orderMedicines;
    }

    public List<UseageMedicine> getUseageMedicines() {
        return useageMedicines;
    }

    public void setUseageMedicines(List<UseageMedicine> useageMedicines) {
        this.useageMedicines = useageMedicines;
    }
}
