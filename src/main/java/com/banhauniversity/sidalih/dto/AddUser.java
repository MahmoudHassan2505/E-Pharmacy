package com.banhauniversity.sidalih.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Data
public class AddUser {
    private String username;
    private String password;
    private short enabled;
    private long phone;
    private String authority;

}
