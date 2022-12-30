package ra.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Cart;
import ra.model.entity.Color;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    Page<Cart> findByCartNameContaining(String colorName, Pageable pageable);
}
