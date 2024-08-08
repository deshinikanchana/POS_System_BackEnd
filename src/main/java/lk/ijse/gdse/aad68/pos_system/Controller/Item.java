package lk.ijse.gdse.aad68.pos_system.Controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse.aad68.pos_system.BO.ItemBOIMPL;
import lk.ijse.gdse.aad68.pos_system.DTO.ItemDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static lk.ijse.gdse.aad68.pos_system.Controller.Customer.logger;

@WebServlet(urlPatterns = "/item" ,loadOnStartup = 2)
public class Item extends HttpServlet {

    Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            logger.info("Item init Method Invoked !");
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/POSSystem");
            this.connection = pool.getConnection();
             logger.info("Item Connection Initialized",this.connection);

        }catch (SQLException | NamingException e){
            logger.error("Connection Failed !");
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(var writer = resp.getWriter()) {
            var itemBoImpl = new ItemBOIMPL();
            Jsonb jsonb = JsonbBuilder.create();
            //DB Process
            if(req.getParameterMap().containsKey("itemCode")) {
                logger.info("Specific Item Get Method Invoked !");
                var code = req.getParameter("itemCode");
                resp.setContentType("application/json");
                jsonb.toJson(itemBoImpl.getItem(code, connection), writer);
                logger.info("Get Item "+code+" Successfully !");
            }else{
                logger.info("All Items Get Method Invoked !");
                resp.setContentType("application/json");
                jsonb.toJson(itemBoImpl.getAllItems(connection), writer);
                logger.info("Get All Items Successfully !");
            }
        }catch (Exception e){
            logger.error(("Connection Error !"));
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Item Save Request not matched with the criteria");
        }

        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            var itemBoImpl = new ItemBOIMPL();
            ItemDTO item = jsonb.fromJson(req.getReader(), ItemDTO.class);
            writer.write(itemBoImpl.saveItem(item,connection));
            logger.info("Item "+item.getItemCode()+" saved successfully");
            resp.setStatus(HttpServletResponse.SC_CREATED);

        }catch (Exception e){
            logger.error("Connection failed !");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            var itemBoImpl = new ItemBOIMPL();
            var code = req.getParameter("itemCode");
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO item = jsonb.fromJson(req.getReader(), ItemDTO.class);
            if(itemBoImpl.updateItem(code,item,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                logger.info("Item "+item.getItemCode()+" Updated Successfully !");
            }else {
                writer.write("Item Update failed");
                logger.info("Item "+item.getItemCode()+ "Update Failed !");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (Exception e){
            logger.info("Connection Failed !");
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            var code = req.getParameter("itemCode");
            var itemBoImpl = new ItemBOIMPL();
            if(itemBoImpl.deleteItem(code,connection)){
                logger.info("Item "+code+" Deleted Successfully !");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Delete failed");
                logger.info("Item "+code +" Delete Failed !");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (Exception e){
            logger.error("Connection Failed !");
            e.printStackTrace();
        }
    }
}
