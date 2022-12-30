package ra.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Color;
import ra.model.entity.ProductDetail;

import java.util.List;
import java.util.Set;

@Repository
public interface ColorRepository extends JpaRepository<Color,Integer> {
    Page<Color> findByColorNameContaining(String colorName, Pageable pageable);
    Set<Color> findByProductDetailListIn(List<ProductDetail> list);
}
