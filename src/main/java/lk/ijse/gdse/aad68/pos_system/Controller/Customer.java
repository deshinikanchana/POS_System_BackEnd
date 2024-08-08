package lk.ijse.gdse.aad68.pos_system.Controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse.aad68.pos_system.BO.CustomerBOIMPL;
import lk.ijse.gdse.aad68.pos_system.DTO.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer" ,loadOnStartup = 2)
public class Customer extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(Customer.class);
    Connection connection;

    @Override
    public void init() throws ServletException {
        logger.info("Customer Init method Invoked");
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/POSSystem");
            this.connection = pool.getConnection();
            logger.info("Customer Connection Initialized",this.connection);

        }catch (SQLException | NamingException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(var writer = resp.getWriter()) {
            var customerBOImpl = new CustomerBOIMPL();
            Jsonb jsonb = JsonbBuilder.create();
            //DB Process
            if(req.getParameterMap().containsKey("cusId")) {
                logger.info(("Specific Customer get Method Invoked !"));
                var cusId = req.getParameter("cusId");
                resp.setContentType("application/json");
                jsonb.toJson(customerBOImpl.getCustomer(cusId, connection), writer);
                logger.info("Get "+cusId+" Customer Successfully !");
            }else{
                logger.info("All Customers Get Method Invoked !");
                resp.setContentType("application/json");
                jsonb.toJson(customerBOImpl.getAllCustomers(connection), writer);
                logger.info("Get All Customers Successfully !");
            }
        }catch (Exception e){
            logger.error("Connection Failed !");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Customer Save Request not matched with the criteria");
        }

        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            var customerBOImpl = new CustomerBOIMPL();
            CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            writer.write(customerBOImpl.saveCustomer(customer,connection));
            logger.info("Customer "+customer.getCusId()+" saved successfully");
            resp.setStatus(HttpServletResponse.SC_CREATED);

        }catch (Exception e){
            logger.error("Connection failed");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {

            var customerBOIMPL = new CustomerBOIMPL();
            var cusId = req.getParameter("cusId");
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            if(customerBOIMPL.updateCustomer(cusId,customer,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                logger.info(("Customer "+cusId+" Updated Successfully !!!"));
            }else {
                writer.write("Customer Update failed");
                logger.info("Customer "+cusId+" Update Failed !");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (Exception e){
            logger.error(("Connection Failed !"));
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            var cusId = req.getParameter("cusId");
            var customerBoImpl = new CustomerBOIMPL();
            if(customerBoImpl.deleteCustomer(cusId,connection)){
                logger.info(("Customer "+cusId+" Delete Successfully !!!"));
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Delete failed");
                logger.info(("Customer "+cusId+" Delete Failed !!!"));
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (Exception e){
            logger.error(("Connection Failed !"));
            e.printStackTrace();
        }
    }
}
