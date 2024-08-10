package lk.ijse.gdse.aad68.pos_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class purchaseOrderDTO {
    private OrderDTO orderDto;
    private List<OrderDetailsDTO> orderList;
}
