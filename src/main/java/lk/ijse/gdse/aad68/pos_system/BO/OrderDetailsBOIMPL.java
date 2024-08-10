package lk.ijse.gdse.aad68.pos_system.BO;

import lk.ijse.gdse.aad68.pos_system.DAO.OrderDetailsDAOIMPL;
import lk.ijse.gdse.aad68.pos_system.DTO.OrderDetailsDTO;

import java.sql.Connection;
import java.util.List;

public class OrderDetailsBOIMPL implements OrderDetailsBO{
    @Override
    public boolean saveOrderDetails(OrderDetailsDTO orderDetails, Connection connection) throws Exception {
        var orderDetailsDaoImpl = new OrderDetailsDAOIMPL();
        return orderDetailsDaoImpl.saveOrderDetails(orderDetails,connection);
    }

    @Override
    public List<OrderDetailsDTO> getOrderDetails(String id, Connection connection) throws Exception {
        var orderDetailsDaoImpl = new OrderDetailsDAOIMPL();
        return orderDetailsDaoImpl.getOrderDetails(id,connection);
    }
}
