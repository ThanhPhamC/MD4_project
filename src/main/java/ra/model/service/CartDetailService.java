package ra.model.service;

import ra.model.entity.CartDetail;

public interface CartDetailService<T,V> extends IService<T,V>{
    CartDetail findByProductDetail_ProductDetailIdAndCart_CartId( int pDetailId,int cartId);
}
