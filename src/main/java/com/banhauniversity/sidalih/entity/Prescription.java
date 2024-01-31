package com.banhauniversity.sidalih.entity;

import jakarta.persistence.*;
import lombok.*;


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
    @ManyToOne
    @JoinColumn(name = "category_id")
    private PrescriptionCategory prsPrescriptionCategory;

    @ManyToOne
    @JoinColumn(name = "pateint_id")
    private Patient patient;

}
