package dao.RepositoryImpl.dbRepositories;

import dao.RepositoryImpl.dbRepositories.base.DataBaseRepository;
import models.Student;

import java.util.Map;

public class StudentRepository extends DataBaseRepository<Student, Integer> {

    @Override
    protected Map<String, ?> idToMap(Integer id) {
        return Map.of("id", id);
    }

    @Override
    protected String getTableName() {
        return "student";
    }

    @Override
    protected Class<Student> getEntityClass() {
        return Student.class;
    }
}

