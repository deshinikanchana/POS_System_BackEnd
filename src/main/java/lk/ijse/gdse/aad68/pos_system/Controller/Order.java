package lk.ijse.gdse.aad68.pos_system.Controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse.aad68.pos_system.BO.OrderBOIMPL;
import lk.ijse.gdse.aad68.pos_system.DTO.OrderDTO;
import lk.ijse.gdse.aad68.pos_system.DTO.purchaseOrderDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static lk.ijse.gdse.aad68.pos_system.Controller.Customer.logger;

@WebServlet(urlPatterns = "/order" ,loadOnStartup = 2)
public class Order extends HttpServlet {
    Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            logger.info("Order init Method Invoked !");
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/POSSystem");
            this.connection = pool.getConnection();
            logger.info("Order Connection Initialized !",this.connection);

        }catch (SQLException | NamingException e){
            logger.error("Connection Failed !");
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(var writer = resp.getWriter()) {
            var orderBoImpl = new OrderBOIMPL();
            Jsonb jsonb = JsonbBuilder.create();
            //DB Process
                var code = req.getParameter("orderId");
                resp.setContentType("application/json");
                jsonb.toJson(orderBoImpl.getOrder(code, connection), writer);
                logger.info("Order "+code+" Get Successfully !");
        }catch (Exception e){
            logger.error("Connection Failed ");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Order Save Request not matched with the criteria");
        }


        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            var orderBoImpl = new OrderBOIMPL();
             purchaseOrderDTO purch = jsonb.fromJson(req.getReader(), purchaseOrderDTO.class);
            if(orderBoImpl.PurchaseOrder(purch,connection)) {
                logger.info("Order "+purch.getOrderDto().getOrderId()+" Saved Successfully !");
                writer.write("Order Saved Successfully");
            }else{
                logger.info("Order "+purch.getOrderDto().getOrderId()+" Saving Failed !");
                writer.write("Order Saving Process Failed");
            }
            resp.setStatus(HttpServletResponse.SC_CREATED);

        }catch (Exception e){
            logger.error("Connection failed !");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
