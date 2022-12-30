package ra.model.service;

import ra.model.entity.Color;
import ra.model.entity.ProductDetail;
import ra.model.entity.Size;

import java.util.List;
import java.util.Set;

public interface SizeService<T,V> extends IService<T,V>{
    List<Size> findSizeForCreatProductDetail();
    Set<Size> findByProductDetailList(List<ProductDetail> list);
}
