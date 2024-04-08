package com.banhauniversity.sidalih.dto;

import com.banhauniversity.sidalih.entity.CollegeUseageMedicine;
import lombok.*;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Data
public class CollegeUseagesDto {
    private long id;
    private Date date;
    private String collegeName;
    private List<CollegeUseageMedicine> collegeUseageMedicines;
    private double totalPrice;
}
