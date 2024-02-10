package com.banhauniversity.sidalih.dto;

import com.banhauniversity.sidalih.entity.Inventory;
import com.banhauniversity.sidalih.entity.Prescription;
import lombok.*;
import org.w3c.dom.ls.LSInput;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Data
public class AddUsage {
    private Date date;
    private Prescription prescription;
    private List<InventoryDto> inventoryDto;
}
