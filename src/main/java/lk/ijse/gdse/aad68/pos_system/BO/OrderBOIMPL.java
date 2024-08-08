package lk.ijse.gdse.aad68.pos_system.BO;

import lk.ijse.gdse.aad68.pos_system.DAO.OrderDAOIMPL;
import lk.ijse.gdse.aad68.pos_system.DTO.OrderDTO;

import java.sql.Connection;

public class OrderBOIMPL implements OrderBO{
    @Override
    public boolean SaveOrder(OrderDTO order, Connection connection) throws Exception {
        var orderDaoImpl = new OrderDAOIMPL();
        return orderDaoImpl.saveOrder(order,connection);
    }

    @Override
    public boolean updateOrder(String id, OrderDTO order, Connection connection) throws Exception {
        var orderDaoImpl = new OrderDAOIMPL();
        return orderDaoImpl.updateOrder(id,order,connection);
    }

    @Override
    public OrderDTO getOrder(String id, Connection connection) throws Exception {
        var orderDaoImpl = new OrderDAOIMPL();
        return orderDaoImpl.getOrder(id,connection);
    }
}
