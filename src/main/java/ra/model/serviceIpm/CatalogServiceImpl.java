package ra.model.serviceIpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.entity.Catalog;
import ra.model.repository.CatalogRepository;
import ra.model.service.CatalogService;

import java.util.List;
@Service
public class CatalogServiceImpl implements CatalogService<Catalog,Integer> {
@Autowired
    CatalogRepository catalogRepository;

    @Override
    public Page<Catalog> findByName(String name,Pageable pageable) {
        return catalogRepository.findByCatalogNameContaining(name,pageable);
    }

    @Override
    public Catalog saveOrUpdate(Catalog catalog) {
        return catalogRepository.save(catalog);
    }

    @Override
    public void delete(Integer id) {
        catalogRepository.deleteById(id);
    }


    @Override
    public Catalog findById(Integer id) {
        return catalogRepository.findById(id).get();
    }

    @Override
    public Page<Catalog> getPagging(Pageable pageable) {
        return catalogRepository.findAll(pageable);
    }

    @Override
    public List<Catalog> findChildById(Integer id) {
        return catalogRepository.findChildById(id);
    }

    @Override
    public List<Catalog> findCatalogForCreatProductDetail() {
        return catalogRepository.getCatCreatProduct();
    }
}
