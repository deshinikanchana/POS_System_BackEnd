package lk.ijse.gdse.aad68.pos_system.DAO;

import lk.ijse.gdse.aad68.pos_system.DTO.OrderDTO;

import java.sql.Connection;

public sealed interface OrderDAO permits OrderDAOIMPL {
    boolean saveOrder(OrderDTO order, Connection connection)throws Exception;
    OrderDTO getOrder(String id,Connection connection)throws Exception;
}
