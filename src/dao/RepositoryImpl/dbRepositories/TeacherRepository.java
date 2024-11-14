package dao.RepositoryImpl.dbRepositories;

import dao.RepositoryImpl.dbRepositories.base.DataBaseRepository;
import models.Teacher;

import java.util.Map;

public class TeacherRepository extends DataBaseRepository<Teacher, Integer> {
    @Override
    protected Map<String, ?> idToMap(Integer id) {
        return Map.of("id", id);
    }

    @Override
    protected String getTableName() {
        return "teacher";
    }

    @Override
    protected Class<Teacher> getEntityClass() {
        return Teacher.class;
    }
}
