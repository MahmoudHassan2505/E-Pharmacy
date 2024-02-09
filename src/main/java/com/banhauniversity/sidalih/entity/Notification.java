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

    @ManyToOne()
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    public Notification(String message, Medicine medicine) {
        this.message = message;
        this.medicine = medicine;
    }
}
