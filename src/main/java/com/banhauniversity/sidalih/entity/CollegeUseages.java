package com.banhauniversity.sidalih.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "college_useages")
public class CollegeUseages {

    @Id
    private long id;

    private Date date;
    @Column(name = "collegename")
    private String collegeName;

    @OneToMany(mappedBy = "collegeUseages")
    private List<CollegeUseageMedicine> collegeUseagesMedicines;
}
