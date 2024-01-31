package com.banhauniversity.sidalih.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Data
public class Token {
    private String token;
    private String username;
}
