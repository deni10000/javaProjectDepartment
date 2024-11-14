package dao.RepositoryImpl.dbRepositories;

import dao.RepositoryImpl.dbRepositories.base.DataBaseRepository;
import models.Assessment;

import java.util.Map;

public class AssessmentRepository extends DataBaseRepository<Assessment, Integer> {


    @Override
    protected Map<String, ?> idToMap(Integer id) {
        return Map.of("id", id);
    }

    @Override
    protected String getTableName() {
        return "assessment";
    }

    @Override
    protected Class<Assessment> getEntityClass() {
        return Assessment.class;
    }

}
