package lk.ijse.gdse.aad68.pos_system.BO;

import lk.ijse.gdse.aad68.pos_system.DAO.ItemDAOIMPL;
import lk.ijse.gdse.aad68.pos_system.DTO.ItemDTO;

import java.sql.Connection;
import java.util.List;

public class ItemBOIMPL implements ItemBO{
    @Override
    public String saveItem(ItemDTO item, Connection connection) throws Exception {
        var itemDaoImpl = new ItemDAOIMPL();
        return itemDaoImpl.saveItem(item,connection);
    }

    @Override
    public boolean deleteItem(String id, Connection connection) throws Exception {
        var itemDaoImpl = new ItemDAOIMPL();
        return itemDaoImpl.deleteItem(id,connection);
    }

    @Override
    public boolean updateItem(String id, ItemDTO item, Connection connection) throws Exception {
        var itemDaoImpl = new ItemDAOIMPL();
        return itemDaoImpl.updateItem(id,item,connection);
    }

    @Override
    public ItemDTO getItem(String id, Connection connection) throws Exception {
        var itemDaoImpl = new ItemDAOIMPL();
        return itemDaoImpl.getItem(id,connection);
    }

    @Override
    public List<ItemDTO> getAllItems(Connection connection) throws Exception {
        var itemDaoImpl = new ItemDAOIMPL();
        return itemDaoImpl.getAllItems(connection);
    }
}
