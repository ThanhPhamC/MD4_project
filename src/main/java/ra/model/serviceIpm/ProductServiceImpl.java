package ra.model.serviceIpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.entity.Product;
import ra.model.repository.ProductRepository;
import ra.model.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService<Product,Integer> {
    @Autowired
    private ProductRepository repository;
    @Override
    public Page<Product> findByName(String name,Pageable pageable) {
        return repository.findByProductNameContaining(name, pageable);
    }

    @Override
    public Product saveOrUpdate(Product product) {
        return repository.save(product);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Product findById(Integer id) {
        return repository.findById(id).get();
    }

    @Override
    public Page<Product> getPagging(Pageable pageable) {
        return repository.findAll(pageable);
    }
    @Override
    public List<Product> findByCatalog_CatalogId(int catId) {
        List<Product> lists= findByCatalog_CatalogId(catId);
        for (Product product:lists) {
            if (!product.isProductStatus()){
                lists.remove(product);
            }
        }
        return lists;
    }

    @Override
    public List<Product> findAllForUser() {
        List<Product> lists= repository.findAll();
        for (Product product:lists) {
            if (!product.isProductStatus()){
                lists.remove(product);
            }
        }
        return lists;
    }
}
