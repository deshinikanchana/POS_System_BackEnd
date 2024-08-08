package lk.ijse.gdse.aad68.pos_system.DAO;

import lk.ijse.gdse.aad68.pos_system.DTO.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CustomerDAOIMPL implements CustomerDAO {
    public static String SAVE_CUSTOMER = "INSERT INTO customer (cusId,cusName,cusAddress,cusSalary) VALUES(?,?,?,?)";
    public static String GET_CUSTOMER = "SELECT * FROM customer WHERE cusId=?";

    public static String GET_ALL_CUSTOMERS = "SELECT * FROM customer";
    public static String UPDATE_CUSTOMER = "UPDATE customer SET cusName =?,cusAddress =?,cusSalary=? WHERE cusId =?";
    public static String DELETE_CUSTOMER = "DELETE FROM customer WHERE cusId=?";
    @Override
    public String saveCustomer(CustomerDTO customer, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER);
            ps.setString(1, customer.getCusId());
            ps.setString(2, customer.getCusName());
            ps.setString(3, customer.getCusAddress());
            ps.setString(4, customer.getCusSalary());
            if(ps.executeUpdate() != 0){
                return "Customer Save Successfully";
            }else {
                return "Failed to Save Customer";
            }
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) throws Exception {
        var ps = connection.prepareStatement(DELETE_CUSTOMER);
        ps.setString(1, id);
        return ps.executeUpdate() != 0;
    }

    @Override
    public boolean updateCustomer(String id, CustomerDTO customer, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(UPDATE_CUSTOMER);
            ps.setString(1, customer.getCusName());
            ps.setString(2, customer.getCusAddress());
            ps.setString(3, customer.getCusSalary());
            ps.setString(4, id);
            return ps.executeUpdate() != 0;
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public CustomerDTO getCustomer(String id, Connection connection) throws Exception {
        try {
            CustomerDTO customerDto = new CustomerDTO();
                var ps = connection.prepareStatement(GET_CUSTOMER);
            ps.setString(1, id);
            var rst = ps.executeQuery();
            while (rst.next()){
                customerDto.setCusId(rst.getString(1));
                customerDto.setCusName(rst.getString(2));
                customerDto.setCusAddress(rst.getString(3));
                customerDto.setCusSalary(rst.getString(4));

            }
            return customerDto;
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(GET_ALL_CUSTOMERS);
            var rst = ps.executeQuery();

            List<CustomerDTO> dtoList = new ArrayList<>();

            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                String salary = rst.getString(4);


                var Dto = new CustomerDTO(id,name,address,salary);

                dtoList.add(Dto);
            }
            return dtoList;
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }
}
