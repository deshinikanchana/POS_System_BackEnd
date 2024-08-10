package lk.ijse.gdse.aad68.pos_system.DAO;

import lk.ijse.gdse.aad68.pos_system.DTO.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ItemDAOIMPL implements ItemDAO {

    public static String SAVE_ITEM = "INSERT INTO item (itemCode,itemName,itemQty,itemPrice) VALUES(?,?,?,?)";
    public static String GET_ITEM = "SELECT * FROM item WHERE itemCode=?";

    public static String GET_ALL_ITEMS = "SELECT * FROM item";
    public static String UPDATE_ITEM = "UPDATE item SET itemName =?,itemQty =?,itemPrice=? WHERE itemCode =?";
    public static String DELETE_ITEM = "DELETE FROM item WHERE itemCode=?";
    @Override
    public String saveItem(ItemDTO item, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(SAVE_ITEM);
            ps.setString(1, item.getItemCode());
            ps.setString(2, item.getItemName());
            ps.setInt(3, item.getItemQty());
            ps.setInt(4, item.getItemPrice());
            if(ps.executeUpdate() != 0){
                return "Item Save Successfully";
            }else {
                return "Failed to Save Item";
            }
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public boolean deleteItem(String id, Connection connection) throws Exception {
        var ps = connection.prepareStatement(DELETE_ITEM);
        ps.setString(1, id);
        return ps.executeUpdate() != 0;
    }

    @Override
    public boolean updateItem(String id, ItemDTO item, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(UPDATE_ITEM);
            ps.setString(1, item.getItemName());
            ps.setInt(2, item.getItemQty());
            ps.setInt(3, item.getItemPrice());
            ps.setString(4, id);
            return ps.executeUpdate() != 0;
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public ItemDTO getItem(String id, Connection connection) throws Exception {
        try {
            ItemDTO itemDto = new ItemDTO();
            var ps = connection.prepareStatement(GET_ITEM);
            ps.setString(1, id);
            var rst = ps.executeQuery();
            while (rst.next()){
                itemDto.setItemCode(rst.getString(1));
                itemDto.setItemName(rst.getString(2));
                itemDto.setItemQty(rst.getInt(3));
                itemDto.setItemPrice(rst.getInt(4));
            }
            return itemDto;
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public List<ItemDTO> getAllItems(Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(GET_ALL_ITEMS);
            var rst = ps.executeQuery();

            List<ItemDTO> dtoList = new ArrayList<>();

            while (rst.next()) {
                String code = rst.getString(1);
                String name = rst.getString(2);
                int qty = rst.getInt(3);
                int price = rst.getInt(4);


                var Dto = new ItemDTO(code,name,qty,price);

                dtoList.add(Dto);
            }
            return dtoList;
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }
}
