package com.banhauniversity.sidalih.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Data
public class Sales {

    private String name;
    private long barcode;
    private long amount;
}
