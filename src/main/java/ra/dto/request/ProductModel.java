package ra.dto.request;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@Data
public class ProductModel {
    private String productName;
    private float imPortPrice;
    private String title;
    private int catalogId;
    private float productDiscount;
    private String productImg;
    private String productDescription;

    List<String> subImgList= new ArrayList<>();
}
