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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public List<UseageMedicine> getUseageMedicines() {
        return useageMedicines;
    }

    public void setUseageMedicines(List<UseageMedicine> useageMedicines) {
        this.useageMedicines = useageMedicines;
    }
}
