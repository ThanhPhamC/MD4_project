package ra.dto.response;

import lombok.Data;
import ra.model.entity.Product;
import ra.model.entity.ProductDetail;
import ra.model.entity.SubImg;

import java.util.ArrayList;
import java.util.List;


@Data
public class ProductForView {
    private int productId;
    private String productName;
    private String title;
    private float productDiscount;
    private String productImg;
    private String productDescription;
    private List<SubImg> subImgList= new ArrayList<>();
    private List<ProductDetail> detailList= new ArrayList<>();

    public ProductForView(Product product) {
        this.detailList=product.getDetailList();
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.title = product.getTitle();
        this.productDiscount = product.getProductDiscount();
        this.productImg = product.getProductImg();
        this.productDescription = product.getProductDescription();
        this.subImgList=product.getImgList();
    }
}
