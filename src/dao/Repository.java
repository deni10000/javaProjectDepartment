package dao;

import models.entity.Entity;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface Repository<T extends Entity<K>, K> {
    boolean delete(K id);

    T save(T model);

    Optional<T> findById(K id);

    Collection<T> findAll();

    boolean update(T model);

    Collection<T> findAllWithParameter(Map<String, ?> parameters);
}
