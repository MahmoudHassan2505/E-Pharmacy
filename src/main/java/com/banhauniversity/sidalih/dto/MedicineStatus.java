package com.banhauniversity.sidalih.dto;

import com.banhauniversity.sidalih.entity.Medicine;

public class MedicineStatus {
    private Medicine medicine;
    private long sum;

    public MedicineStatus(){}

    public MedicineStatus(Medicine medicine, long sum) {
        this.medicine = medicine;
        this.sum = sum;
    }


    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public long getAmount() {
        return sum;
    }

    public void setAmount(long amount) {
        this.sum = amount;
    }
}
