package dao.RepositoryImpl.dbRepositories;

import dao.RepositoryImpl.dbRepositories.base.DataBaseRepository;
import models.Grade;
import utils.Pair;

import java.util.Map;

public class GradeRepository extends DataBaseRepository<Grade, Pair<Integer, Integer>> {
    @Override
    protected Map<String, ?> idToMap(Pair<Integer, Integer> id) {
        return Map.of("studentId", id.first(), "subjectId", id.second());
    }

    @Override
    protected String getTableName() {
        return "grade";
    }

    @Override
    protected Class<Grade> getEntityClass() {
        return Grade.class;
    }
}
