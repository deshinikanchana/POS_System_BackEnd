package lk.ijse.gdse.aad68.pos_system.Controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse.aad68.pos_system.BO.OrderDetailsBOIMPL;
import lk.ijse.gdse.aad68.pos_system.DTO.OrderDetailsDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static lk.ijse.gdse.aad68.pos_system.Controller.Customer.logger;

@WebServlet(urlPatterns = "/orderDetails" ,loadOnStartup = 2)
public class OrderDetails extends HttpServlet {

    Connection connection;
    @Override
    public void init() throws ServletException {
        try {
            logger.info("Order Details init method Invoked !");
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/POSSystem");
            this.connection = pool.getConnection();
            logger.info("Order Details Connection Initialized",this.connection);

        }catch (SQLException | NamingException e){
            logger.error("Connection Failed !");
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(var writer = resp.getWriter()) {
            var orderDetailsompl = new OrderDetailsBOIMPL();
            Jsonb jsonb = JsonbBuilder.create();
            //DB Process
                var orderId = req.getParameter("orderId");
                resp.setContentType("application/json");
                jsonb.toJson(orderDetailsompl.getOrderDetails(orderId, connection), writer);
                logger.info(orderId + " Order Details Get Successfully !");
        }catch (Exception e){
            logger.error("Connection Failed !");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Order Details Request not matched with the criteria");
        }

        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            var orderDetailsBoImpl = new OrderDetailsBOIMPL();
            OrderDetailsDTO orders =  jsonb.fromJson(req.getReader(), OrderDetailsDTO.class);

            if(orderDetailsBoImpl.saveOrderDetails(orders,connection)) {
                logger.info(orders.getOrderId()+" Order Details Saved Successfully !");
                writer.write("Order Details Saved Successfully");
            }else{
                logger.info(orders.getOrderId() + " Order Details Save Failed !");
                writer.write("Try Again");
            }
            resp.setStatus(HttpServletResponse.SC_CREATED);

        }catch (Exception e){
            logger.error("Connection failed");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
