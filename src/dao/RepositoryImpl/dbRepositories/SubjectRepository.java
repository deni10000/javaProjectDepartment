package dao.RepositoryImpl.dbRepositories;

import dao.RepositoryImpl.dbRepositories.base.DataBaseRepository;
import models.Subject;

import java.util.Map;

public class SubjectRepository extends DataBaseRepository<Subject, Integer> {
    @Override
    protected Map<String, ?> idToMap(Integer id) {
        return Map.of("id", id);
    }

    @Override
    protected String getTableName() {
        return "subject";
    }

    @Override
    protected Class<Subject> getEntityClass() {
        return Subject.class;
    }
}
