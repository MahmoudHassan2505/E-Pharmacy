package com.banhauniversity.sidalih.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "collegeuseage_medicine")
public class CollegeUseageMedicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long amount;

    @ManyToOne
    @JoinColumn(name = "college_useages_id")
    @JsonIgnore
    private CollegeUseages collegeUseages;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    public CollegeUseageMedicine(long amount, CollegeUseages collegeUseages, Inventory inventory) {
        this.amount = amount;
        this.collegeUseages = collegeUseages;
        this.inventory = inventory;
    }
}
