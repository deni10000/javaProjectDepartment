package dao.RepositoryImpl.listRepositories.base;

import dao.Repository;
import models.entity.Entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class ListRepository<T extends Entity<K>, K> implements Repository<T, K> {
    protected List<T> list = new ArrayList<T>();

    @Override
    public boolean delete(K id) {
        Optional<T> byId = findById(id);
        if (byId.isEmpty()) {
            return false;
        }
        list.remove(byId.get());
        return true;
    }

    @Override
    public T save(T model) {
        if (model.getId() == null) {
            return saveWithoutId(model);
        }
        Optional<T> byId = findById(model.getId());
        if (byId.isPresent()) {
            throw new RuntimeException("Такой id уже существует");
        }
        list.add(model);
        return model;
    }

    public abstract T saveWithoutId(T model);

    @Override
    public Optional<T> findById(K id) {
        return list.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(list);
    }

    @Override
    public boolean update(T model) {
        Optional<T> byId = findById(model.getId());
        if (byId.isEmpty()) {
            return false;
        } else {
            list.remove(byId.get());
            list.add(model);
            return true;
        }
    }

    @Override
    public List<T> findAllWithParameter(Map<String, ?> parameters) {
        return list.stream().filter(e -> parameters.entrySet().stream().reduce(true, (bl, x) -> {
            try {
                Field field = e.getClass().getDeclaredField(x.getKey());
                field.setAccessible(true);
                Object obj = field.get(e);
                return bl & obj.equals(x.getValue());
            } catch (NoSuchFieldException ex) {
                throw new RuntimeException("Неправильные параметры в мапе");
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }, Boolean::logicalAnd)).toList();
    }
}
