package com.banhauniversity.sidalih.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String message;
    private long orderid;

    @ManyToOne()
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    public Notification(String message, long orderid, Medicine medicine) {
        this.message = message;
        this.orderid = orderid;
        this.medicine = medicine;
    }
}
