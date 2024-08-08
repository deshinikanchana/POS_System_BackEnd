package lk.ijse.gdse.aad68.pos_system.BO;

import lk.ijse.gdse.aad68.pos_system.DTO.OrderDetailsDTO;

import java.sql.Connection;
import java.util.List;

public interface OrderDetailsBO {
    boolean saveOrderDetails(OrderDetailsDTO orderDetails, Connection connection)throws Exception;
    boolean deleteOrderDetails(String id, Connection connection)throws Exception;
    List<OrderDetailsDTO> getOrderDetails(String id,Connection connection)throws Exception;

}
