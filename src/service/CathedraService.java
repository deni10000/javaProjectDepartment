package service;

import dao.RepositoryImpl.dbRepositories.CathedraRepository;
import models.Cathedra;
import object_manager.ObjectManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CathedraService {
    CathedraRepository cathedraRepository = ObjectManager.get(CathedraRepository.class);

    public Map<Integer, String> getCathedrasInMap() {
        return getAllCathedras().stream().collect(HashMap::new, (map, x) -> map.put(x.id(), x.name()), HashMap::putAll);
    }

    public List<Cathedra> getAllCathedras() {
        return cathedraRepository.findAll();
    }

    public Optional<Cathedra> getCathedraById(int id) {
        return cathedraRepository.findById(id);
    }
}
