package com.banhauniversity.sidalih.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class StatisticsDTO {
   private int studentCount;
   private int prescriptionCount;
   private int runningOut;
   private int aboutToExpire;
   private List<StudentPerCollageDTO> studentPerCollage;
   private List<Reports> prescriptionPerCollage;
   private List<SalesDTO> sales;

}
