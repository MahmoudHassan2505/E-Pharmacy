package com.banhauniversity.sidalih.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Data
public class Reports {

    private String collegeName;
    private long count;
}
