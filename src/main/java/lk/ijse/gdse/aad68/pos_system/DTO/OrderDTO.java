package lk.ijse.gdse.aad68.pos_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO implements Serializable {
    private String orderId;
    private String cusId;
    private String orderDate;
    private int orderTotal;
    private int discount;
    private int subTotal;
    private int cash;
    private int balance;

}
