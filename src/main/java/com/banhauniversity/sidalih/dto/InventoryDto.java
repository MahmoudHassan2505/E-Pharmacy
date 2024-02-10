package com.banhauniversity.sidalih.dto;

import com.banhauniversity.sidalih.entity.Inventory;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Data
public class InventoryDto {
    private long amount;
    private Inventory inventory;
}
