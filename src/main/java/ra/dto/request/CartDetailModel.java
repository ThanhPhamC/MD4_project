package ra.dto.request;

import lombok.Data;

@Data
public class CartDetailModel {
    private int cartId;
    private int productDetailId;
    private int detailCartQuantity;
    private float priceCurrentDetail;
}
