package ra.model.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ra.model.entity.CartDetail;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail,Integer> {
//    Page<CartDetail>findByProduct_ProductNameDetail_Product(String colorName, Pageable pageable);
    CartDetail findByProductDetail_ProductDetailIdAndCart_CartId( int pDetailId,int cartId);

}
