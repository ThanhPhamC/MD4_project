package ra.model.service;

import ra.model.entity.ProductDetail;

public interface ProductDetailService<T,V> extends IService<T,V>{
    boolean existsByColor_ColoIdAndAndSize_SizeIdAndProduct_ProductId(int colorId, int sizeId, int proId);
    ProductDetail findByColor_ColoIdAndSize_SizeIdAndProduct_ProductId(int colorId, int sizeId, int proId);
}
