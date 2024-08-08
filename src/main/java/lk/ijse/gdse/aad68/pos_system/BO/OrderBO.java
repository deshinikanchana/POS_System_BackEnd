package lk.ijse.gdse.aad68.pos_system.BO;
import lk.ijse.gdse.aad68.pos_system.DTO.OrderDTO;

import java.sql.Connection;

public interface OrderBO {
    boolean SaveOrder(OrderDTO order, Connection connection)throws Exception;
    boolean updateOrder(String id,OrderDTO order,Connection connection)throws Exception;
    OrderDTO getOrder(String id,Connection connection)throws Exception;

}
