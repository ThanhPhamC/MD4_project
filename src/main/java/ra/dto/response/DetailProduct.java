package ra.dto.response;
import lombok.*;
import ra.model.entity.Color;
import ra.model.entity.Product;
import ra.model.entity.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class DetailProduct extends ProductForView {
    private Set<Size> sizeList= new HashSet<>();
    private Set<Color> colorList= new HashSet<>();
    public DetailProduct(Product product) {
        super(product);
    }
}
