package ra.model.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.ProductDetail;
@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail,Integer> {
   boolean existsByColor_ColoIdAndAndSize_SizeIdAndProduct_ProductId(int colorId, int sizeId, int proId);
   ProductDetail findByColor_ColoIdAndSize_SizeIdAndProduct_ProductId(int colorId, int sizeId, int proId);

}
