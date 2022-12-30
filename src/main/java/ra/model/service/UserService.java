package ra.model.service;

import ra.model.entity.Users;

public interface UserService<Users,Integer> extends IService<Users,Integer>{
    Users findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Users saveOrUpdate(Users user);
    Users findByEmail(String email);
}