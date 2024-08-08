package lk.ijse.gdse.aad68.pos_system.DTO;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO implements Serializable {

    private String cusId;
    private String cusName;
    private String cusAddress;
    private String cusSalary;
}
