package ra.model.serviceIpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.entity.ProductDetail;
import ra.model.repository.ProductDetailRepository;
import ra.model.service.ProductDetailService;
@Service
public class ProductDetailServiceImpl implements ProductDetailService<ProductDetail,Integer> {
    @Autowired private ProductDetailRepository repository;
    @Override
    public Page<ProductDetail> findByName(String name, Pageable pageable) {
        return null;
    }

    @Override
    public ProductDetail saveOrUpdate(ProductDetail productDetail) {
        return repository.save(productDetail);
    }

    @Override
    public void delete(Integer id) {
    repository.deleteById(id);
    }

    @Override
    public ProductDetail findById(Integer id) {
        return repository.findById(id).get();
    }

    @Override
    public Page<ProductDetail> getPagging(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public boolean existsByColor_ColoIdAndAndSize_SizeIdAndProduct_ProductId(int colorId, int sizeId, int proId) {
        return repository.existsByColor_ColoIdAndAndSize_SizeIdAndProduct_ProductId(colorId,sizeId,proId);
    }

    @Override
    public ProductDetail findByColor_ColoIdAndSize_SizeIdAndProduct_ProductId(int colorId, int sizeId, int proId) {
        return repository.findByColor_ColoIdAndSize_SizeIdAndProduct_ProductId(colorId, sizeId, proId);
    }
}
