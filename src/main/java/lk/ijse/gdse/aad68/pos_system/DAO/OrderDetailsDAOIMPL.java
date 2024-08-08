package lk.ijse.gdse.aad68.pos_system.DAO;
import lk.ijse.gdse.aad68.pos_system.DTO.OrderDetailsDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class OrderDetailsDAOIMPL implements OrderDetailsDAO{

    public static String SAVE_ORDER_DETAILS = "INSERT INTO orderDetails (orderId,itemCode,buyQty,itemTotal) VALUES(?,?,?,?)";
    public static String GET_ORDER_DETAILS = "SELECT * FROM orderDetails WHERE orderId=?";
    public static String DELETE_ORDER_DETAILS = "DELETE FROM orderDetails WHERE orderId=?";

    @Override
    public boolean saveOrderDetails(OrderDetailsDTO dto, Connection connection) throws Exception {
        boolean isPass = false;
            try {
                var ps = connection.prepareStatement(SAVE_ORDER_DETAILS);
                ps.setString(1, dto.getOrderId());
                ps.setString(2, dto.getItemCode());
                ps.setInt(3, dto.getBuyQty());
                ps.setInt(4, dto.getItemTotal());
                if (ps.executeUpdate() != 0) {
                    isPass = true;
                } else {
                    isPass = false;
                }
            } catch (SQLException e) {
                throw new SQLException(e.getMessage());
            }
        return isPass;
    }

    @Override
    public boolean deleteOrderDetails(String id, Connection connection) throws Exception {
        var ps = connection.prepareStatement(DELETE_ORDER_DETAILS);
        ps.setString(1, id);
        return ps.executeUpdate() != 0;
    }

    @Override
    public List<OrderDetailsDTO> getOrderDetails(String id, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(GET_ORDER_DETAILS);
            ps.setString(1,id);
            var rst = ps.executeQuery();

            List<OrderDetailsDTO> dtoList = new ArrayList<>();

            while (rst.next()) {
                String orderId = rst.getString(1);
                String itemCode = rst.getString(2);
                int buyQty = rst.getInt(3);
                int itemTotal = rst.getInt(4);


                var Dto = new OrderDetailsDTO(orderId,itemCode,buyQty,itemTotal);

                dtoList.add(Dto);
            }
            return dtoList;
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }
}
