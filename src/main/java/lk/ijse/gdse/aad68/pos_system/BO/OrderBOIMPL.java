package lk.ijse.gdse.aad68.pos_system.BO;

import lk.ijse.gdse.aad68.pos_system.DAO.*;
import lk.ijse.gdse.aad68.pos_system.DTO.ItemDTO;
import lk.ijse.gdse.aad68.pos_system.DTO.OrderDTO;
import lk.ijse.gdse.aad68.pos_system.DTO.OrderDetailsDTO;
import lk.ijse.gdse.aad68.pos_system.DTO.purchaseOrderDTO;

import java.sql.Connection;

public class OrderBOIMPL implements OrderBO{

    OrderDAO orderDao = new OrderDAOIMPL();
    OrderDetailsDAO orderDetailsDao = new OrderDetailsDAOIMPL();
    ItemDAO itemDao = new ItemDAOIMPL();

    @Override
    public boolean PurchaseOrder(purchaseOrderDTO order, Connection connection) throws Exception {
       connection.setAutoCommit(false);
        boolean saveOrder = orderDao.saveOrder(order.getOrderDto(),connection);
        if (!saveOrder){
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for(OrderDetailsDTO ord:order.getOrderList()){
            boolean isSaved = orderDetailsDao.saveOrderDetails(new OrderDetailsDTO(ord.getOrderId(),ord.getItemCode(),ord.getBuyQty(),ord.getItemTotal()),connection);
            if(!isSaved){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            ItemDTO item = itemDao.getItem(ord.getItemCode(),connection);
            item.setItemQty(item.getItemQty() - ord.getBuyQty());
            boolean IsSet = itemDao.updateItem(ord.getItemCode(),item,connection);
            if(!IsSet){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public OrderDTO getOrder(String id, Connection connection) throws Exception {
        var orderDaoImpl = new OrderDAOIMPL();
        return orderDaoImpl.getOrder(id,connection);
    }
}
