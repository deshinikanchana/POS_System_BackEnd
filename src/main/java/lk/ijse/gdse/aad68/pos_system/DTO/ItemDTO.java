package lk.ijse.gdse.aad68.pos_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDTO implements Serializable {
    private String itemCode;
    private String itemName;
    private String itemQty;
    private String itemPrice;
}
