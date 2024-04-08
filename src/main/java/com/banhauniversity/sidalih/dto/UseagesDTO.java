package com.banhauniversity.sidalih.dto;

import com.banhauniversity.sidalih.entity.Prescription;
import com.banhauniversity.sidalih.entity.UseageMedicine;
import lombok.*;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Data
public class UseagesDTO {
    private long id;
    private Date date;
    private Prescription prescription;
    private List<UseageMedicine> useageMedicines;
    private long totalPrice;
}
