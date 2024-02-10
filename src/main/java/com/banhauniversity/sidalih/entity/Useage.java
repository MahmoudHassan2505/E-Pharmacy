package com.banhauniversity.sidalih.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "useage")
public class Useage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @OneToMany(mappedBy = "useage")
    private List<UseageMedicine> useageMedicines;

    public Useage(Date date, Prescription prescription, List<UseageMedicine> useageMedicines) {
        this.date = date;
        this.prescription = prescription;
        this.useageMedicines = useageMedicines;
    }
}
