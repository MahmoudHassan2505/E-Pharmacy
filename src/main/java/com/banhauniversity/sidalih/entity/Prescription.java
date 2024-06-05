package com.banhauniversity.sidalih.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prescription")
public class Prescription {
    @Id
    private long id;
    private boolean is_allowed;
    private String diagnosis;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private PrescriptionCategory prsPrescriptionCategory;

    @ManyToOne
    @JoinColumn(name = "pateint_id")
    private Patient patient;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "prescription_medicine",
            joinColumns = @JoinColumn(name = "prescription_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id",referencedColumnName = "id")
    )
    private List<Medicine> medicines;

}
