package lk.ijse.gdse.aad68.pos_system.BO;
import lk.ijse.gdse.aad68.pos_system.DTO.OrderDTO;
import lk.ijse.gdse.aad68.pos_system.DTO.purchaseOrderDTO;

import java.sql.Connection;

public interface OrderBO {
    boolean PurchaseOrder(purchaseOrderDTO purchaseList, Connection connection)throws Exception;
    OrderDTO getOrder(String id,Connection connection)throws Exception;

}
