package lk.ijse.gdse.aad68.pos_system.DAO;

import lk.ijse.gdse.aad68.pos_system.DTO.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public final class OrderDAOIMPL implements OrderDAO{

    public static String SAVE_ORDER = "INSERT INTO orders (orderId,cusId,orderDate,orderTotal,discount,subTotal,cash,balance) VALUES(?,?,?,?,?,?,?,?)";

    public static String GET_ORDER = "SELECT * FROM orders WHERE orderId=?";
    public static String UPDATE_ORDER = "UPDATE orders SET orderTotal =?,discount =?,subTotal=?,cash=?,balance=?  WHERE orderId =?";

    @Override
    public boolean saveOrder(OrderDTO order, Connection connection) throws Exception {
        boolean passed = false;
        try {
            var ps = connection.prepareStatement(SAVE_ORDER);
            ps.setString(1, order.getOrderId());
            ps.setString(2, order.getCusId());
            ps.setString(3, order.getOrderDate());
            ps.setInt(4, order.getOrderTotal());
            ps.setInt(5, order.getDiscount());
            ps.setInt(6, order.getSubTotal());
            ps.setInt(7, order.getCash());
            ps.setInt(8, order.getBalance());


            if(ps.executeUpdate() != 0){
                passed= true;
            }else {
                passed=false;
            }
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        return passed;
    }

    @Override
    public OrderDTO getOrder(String id, Connection connection) throws Exception {
        try {
            OrderDTO order = new OrderDTO();
            var ps = connection.prepareStatement(GET_ORDER);
            ps.setString(1, id);
            var rst = ps.executeQuery();
            while (rst.next()){
                order.setOrderId(rst.getString(1));
                order.setCusId(rst.getString(2));
                order.setOrderDate(rst.getString(3));
                order.setOrderTotal(rst.getInt(4));
                order.setDiscount(rst.getInt(5));
                order.setSubTotal(rst.getInt(6));
                order.setCash(rst.getInt(7));
                order.setBalance(rst.getInt(8));

            }
            return order;
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public boolean updateOrder(String id, OrderDTO order, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(UPDATE_ORDER);
            ps.setInt(1, order.getOrderTotal());
            ps.setInt(2, order.getDiscount());
            ps.setInt(3, order.getSubTotal());
            ps.setInt(4, order.getCash());
            ps.setInt(5, order.getBalance());
            ps.setString(6, id);

            return ps.executeUpdate() != 0;
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

}
