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
    private long price;
    private Date expireDate;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

}
