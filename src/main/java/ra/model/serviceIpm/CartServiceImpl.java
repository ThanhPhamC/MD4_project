package ra.model.serviceIpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.entity.Cart;
import ra.model.repository.CartRepository;
import ra.model.service.CartService;

@Service
public class CartServiceImpl implements CartService<Cart, Integer> {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Page<Cart> findByName(String name, Pageable pageable) {
        return cartRepository.findByCartNameContaining(name, pageable);
    }

    @Override
    public Cart saveOrUpdate(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void delete(Integer id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Cart findById(Integer id) {
        return cartRepository.findById(id).get();
    }

    @Override
    public Page<Cart> getPagging(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }
}
