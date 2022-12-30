package ra.model.service;


import ra.model.entity.Color;
import ra.model.entity.ProductDetail;

import java.util.List;
import java.util.Set;

public interface ColorService<T, V> extends IService<T, V> {
    List<Color> findColorForCreatProductDetail();
    Set<Color> findByProductDetailList(List<ProductDetail> list);
}
