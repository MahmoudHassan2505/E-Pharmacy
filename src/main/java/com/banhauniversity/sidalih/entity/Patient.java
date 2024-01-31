package com.banhauniversity.sidalih.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class Patient {

    @Id
    private long national_id;
    private String phone_number;
    private long student_id;
    private String name;
    private String gender;
    private boolean is_chronic;
    private String level;

    @ManyToMany
    @JoinTable(
            name = "patient_disease",
            joinColumns = @JoinColumn(name = "patient_id",referencedColumnName = "national_id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id",referencedColumnName = "id")
    )
    private List<Disease> disease;
}
