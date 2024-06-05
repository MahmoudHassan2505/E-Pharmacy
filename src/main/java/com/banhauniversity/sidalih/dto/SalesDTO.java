package com.banhauniversity.sidalih.dto;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
public class SalesDTO {
    private int month;
    private double price;
}
