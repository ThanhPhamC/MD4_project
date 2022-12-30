package ra.model.serviceIpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.entity.ProductDetail;
import ra.model.entity.Size;
import ra.model.repository.SizeRepository;
import ra.model.service.SizeService;
import java.util.List;
import java.util.Set;

@Service
public class SizeServiceImpl implements SizeService<Size, Integer> {
    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public Page<Size> findByName(String name, Pageable pageable) {
        return sizeRepository.findBySizeNameContaining(name, pageable);
    }

    @Override
    public Size saveOrUpdate(Size size) {
        return sizeRepository.save(size);
    }

    @Override
    public void delete(Integer id) {
        sizeRepository.deleteById(id);
    }
    @Override
    public Size findById(Integer id) {
        return sizeRepository.findById(id).get();
    }
    @Override
    public Page<Size> getPagging(Pageable pageable) {
        return sizeRepository.findAll(pageable);
    }
    @Override
    public List<Size> findSizeForCreatProductDetail() {
        List<Size> sizeList = sizeRepository.findAll();
        for (Size size : sizeList) {
            if (!size.isSizeStatus()){
                sizeList.remove(size);
            }
        }
        return sizeList;
    }

    @Override
    public Set<Size> findByProductDetailList(List<ProductDetail> list) {
        return sizeRepository.findByProductDetailListIn(list);
    }
}
