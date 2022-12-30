package ra.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Users;


@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    Page<Users> findByUserNameContaining(String name, Pageable pageable);
    Users findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Users findByEmail(String email);
    boolean existsByPhone(String phone);
}