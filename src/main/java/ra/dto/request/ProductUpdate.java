package ra.dto.request;

import lombok.Data;
@Data
public class ProductUpdate extends ProductModel{
    private int productId;
    private boolean productStatus;
    private String creatDate;
}
