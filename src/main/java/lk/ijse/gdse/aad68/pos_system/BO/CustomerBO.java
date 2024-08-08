package lk.ijse.gdse.aad68.pos_system.BO;

import lk.ijse.gdse.aad68.pos_system.DTO.CustomerDTO;

import java.sql.Connection;
import java.util.List;

public interface CustomerBO {
    String saveCustomer(CustomerDTO customer, Connection connection)throws Exception;
    boolean deleteCustomer(String id, Connection connection)throws Exception;
    boolean updateCustomer(String id,CustomerDTO customer,Connection connection)throws Exception;
    CustomerDTO getCustomer(String id,Connection connection)throws Exception;

    List<CustomerDTO> getAllCustomers(Connection connection) throws Exception;
}
