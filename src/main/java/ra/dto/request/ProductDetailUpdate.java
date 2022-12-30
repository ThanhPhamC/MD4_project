package ra.dto.request;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;

@Data
public class ProductDetailUpdate extends ProductDetailModel{
    private int productDetailId;
    private String dateDetail;
    private boolean detailStatus;
}
