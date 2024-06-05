package com.banhauniversity.sidalih.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
public class StudentPerCollageDTO {
    private String collage;
    private long count;

}
