package com.banhauniversity.sidalih.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long amount;


    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private OrderMedicine orderMedicine;

}
