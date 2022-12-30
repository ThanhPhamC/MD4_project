package ra.model.serviceIpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.entity.Color;
import ra.model.entity.ProductDetail;
import ra.model.repository.ColorRepository;
import ra.model.service.ColorService;

import java.util.List;
import java.util.Set;

@Service
public class ColorServiceImpl implements ColorService<Color, Integer> {
    @Autowired
    private ColorRepository colorRepository;

    @Override
    public Page<Color> findByName(String name, Pageable pageable) {
        return colorRepository.findByColorNameContaining(name, pageable);
    }

    @Override
    public Color saveOrUpdate(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public void delete(Integer id) {
        colorRepository.deleteById(id);
    }

    @Override
    public Color findById(Integer id) {
        return colorRepository.findById(id).get();
    }

    @Override
    public Page<Color> getPagging(Pageable pageable) {
        return colorRepository.findAll(pageable);
    }

    @Override
    public List<Color> findColorForCreatProductDetail() {
        List<Color> colors = colorRepository.findAll();
        for (Color color : colors) {
            if (!color.isColorStatus()){
                colors.remove(color);
            }
        }
        return colors;
    }

    @Override
    public Set<Color> findByProductDetailList(List<ProductDetail> list) {
        return colorRepository.findByProductDetailListIn(list);
    }
}
