package ra.model.serviceIpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.entity.CartDetail;
import ra.model.repository.CartDetailRepository;
import ra.model.service.CartDetailService;
@Service
public class CartDetailServiceImpl implements CartDetailService<CartDetail,Integer> {
    @Autowired
    CartDetailRepository repository;
    @Override
    public Page<CartDetail> findByName(String name, Pageable pageable) {
        return null;
    }

    @Override
    public CartDetail saveOrUpdate(CartDetail cartDetail) {
        return repository.save(cartDetail);
    }

    @Override
    public void delete(Integer id) {
    repository.deleteById(id);
    }

    @Override
    public CartDetail findById(Integer id) {
        return repository.findById(id).get();
    }

    @Override
    public Page<CartDetail> getPagging(Pageable pageable) {
        return repository.findAll(pageable);
    }


    @Override
    public CartDetail findByProductDetail_ProductDetailIdAndCart_CartId( int pDetailId,int cartId) {
        return repository.findByProductDetail_ProductDetailIdAndCart_CartId(pDetailId, cartId);
    }
}
