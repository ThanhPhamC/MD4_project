package ra.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IService<T,V> {
    Page<T> findByName(String name, Pageable pageable);
    T saveOrUpdate(T t);
    void delete(V id);
    T findById(V id);
    Page<T> getPagging(Pageable pageable);
}
