package lk.ijse.gdse.aad68.pos_system.BO;

import lk.ijse.gdse.aad68.pos_system.DAO.CustomerDAOIMPL;
import lk.ijse.gdse.aad68.pos_system.DTO.CustomerDTO;

import java.sql.Connection;
import java.util.List;

public class CustomerBOIMPL implements CustomerBO{
    @Override
    public String saveCustomer(CustomerDTO customer, Connection connection) throws Exception {
        var customerDAOImpl = new CustomerDAOIMPL();
        return customerDAOImpl.saveCustomer(customer,connection);
    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) throws Exception {
        var customerDAOImpl = new CustomerDAOIMPL();
        return customerDAOImpl.deleteCustomer(id,connection);
    }

    @Override
    public boolean updateCustomer(String id, CustomerDTO customer, Connection connection) throws Exception {
        var customerDAOIMPL = new CustomerDAOIMPL();
        return customerDAOIMPL.updateCustomer(id,customer,connection);
    }

    @Override
    public CustomerDTO getCustomer(String id, Connection connection) throws Exception {
        var customerDAOimpl = new CustomerDAOIMPL();
        return customerDAOimpl.getCustomer(id,connection);
    }

    @Override
    public List<CustomerDTO> getAllCustomers(Connection connection) throws Exception {
        var customerDAOImpl = new CustomerDAOIMPL();
        return customerDAOImpl.getAllCustomers(connection);
    }
}
