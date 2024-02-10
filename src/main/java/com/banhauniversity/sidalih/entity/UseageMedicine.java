package com.banhauniversity.sidalih.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "useage_medicine")
public class UseageMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long amount;
    private long price;

    @ManyToOne
    @JoinColumn(name = "useage_id")
    @JsonIgnore
    private Useage useage;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    public UseageMedicine(long amount, long price, Useage useage, Medicine medicine) {
        this.amount = amount;
        this.price = price;
        this.useage = useage;
        this.medicine = medicine;
    }
}
