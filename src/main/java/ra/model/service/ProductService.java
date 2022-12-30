package ra.model.service;

import ra.model.entity.Product;

import java.util.List;

public interface ProductService<T,V> extends IService<T,V>{
    List<Product> findByCatalog_CatalogId(int catId);
    List<Product> findAllForUser();
}
