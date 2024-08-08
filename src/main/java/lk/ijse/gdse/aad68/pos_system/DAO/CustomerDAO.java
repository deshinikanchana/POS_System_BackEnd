package lk.ijse.gdse.aad68.pos_system.DAO;

import lk.ijse.gdse.aad68.pos_system.DTO.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public sealed interface CustomerDAO permits CustomerDAOIMPL {
    String saveCustomer(CustomerDTO customer, Connection connection)throws Exception;
    boolean deleteCustomer(String id, Connection connection)throws Exception;
    boolean updateCustomer(String id,CustomerDTO customer,Connection connection)throws Exception;
    CustomerDTO getCustomer(String id,Connection connection)throws Exception;
    List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException;
}
