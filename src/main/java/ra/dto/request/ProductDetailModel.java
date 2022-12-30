package ra.dto.request;
import lombok.Data;
@Data
public class ProductDetailModel {
    private float exportDetail;
    private int quantity;
    private int colorId;
    private int sizeId;
    private int productId;
    private boolean detailStatus;
}
