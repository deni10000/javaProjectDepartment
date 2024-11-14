package dao.RepositoryImpl.dbRepositories;

import dao.RepositoryImpl.dbRepositories.base.DataBaseRepository;
import models.Cathedra;

import java.util.Map;

public class CathedraRepository extends DataBaseRepository<Cathedra, Integer> {


    @Override
    protected Map<String, ?> idToMap(Integer id) {
        return Map.of("id", id);
    }

    @Override
    protected String getTableName() {
        return "cathedra";
    }

    @Override
    protected Class<Cathedra> getEntityClass() {
        return Cathedra.class;
    }
}
