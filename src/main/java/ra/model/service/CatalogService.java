package ra.model.service;

import org.springframework.data.domain.Pageable;
import ra.model.entity.Catalog;
import ra.model.entity.Color;

import java.util.List;

public interface CatalogService<T,V> extends IService<T,V>{
    List<Catalog> findChildById(Integer id);
    List<Catalog> findCatalogForCreatProductDetail();
}
