package ra.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Product;
import ra.model.entity.SubImg;
@Repository
public interface SubImgRepository extends JpaRepository<SubImg,Integer> {
    Page<SubImg> findByImgLinkContaining(String imgLink, Pageable pageable);

}
