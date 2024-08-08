package lk.ijse.gdse.aad68.pos_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrderDetailsDTO {
    private String orderId;
    private String itemCode;
    private int buyQty;
    private int itemTotal;
}
