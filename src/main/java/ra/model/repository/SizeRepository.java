package ra.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ra.model.entity.ProductDetail;
import ra.model.entity.Size;

import java.util.List;
import java.util.Set;

@Repository
public interface SizeRepository extends JpaRepository<Size,Integer> {
    Page<Size> findBySizeNameContaining(String sizeName, Pageable pageable);
    Set<Size> findByProductDetailListIn(List<ProductDetail> list);
}
