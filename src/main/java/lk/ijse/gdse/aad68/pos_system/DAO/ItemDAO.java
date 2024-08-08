package lk.ijse.gdse.aad68.pos_system.DAO;

import lk.ijse.gdse.aad68.pos_system.DTO.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public sealed interface  ItemDAO permits ItemDAOIMPL {

    String saveItem(ItemDTO item, Connection connection)throws Exception;
    boolean deleteItem(String id, Connection connection)throws Exception;
    boolean updateItem(String id,ItemDTO item,Connection connection)throws Exception;
    ItemDTO getItem(String id,Connection connection)throws Exception;
    List<ItemDTO> getAllItems(Connection connection) throws SQLException;
}
