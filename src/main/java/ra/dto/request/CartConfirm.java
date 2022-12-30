package ra.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CartConfirm {
    private int cartId;
    private String toAddress;
    private String phoneNumber;
    private String fullName;
    private String emailConfirm;
    private float totalAmount;
    private LocalDate creatDate;
        private String note;
}
