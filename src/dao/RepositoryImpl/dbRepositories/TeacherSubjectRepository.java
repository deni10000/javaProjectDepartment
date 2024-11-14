package dao.RepositoryImpl.dbRepositories;

import dao.RepositoryImpl.dbRepositories.base.DataBaseRepository;
import models.TeacherSubject;
import utils.Pair;

import java.util.Map;

public class TeacherSubjectRepository extends DataBaseRepository<TeacherSubject, Pair<Integer, Integer>> {

    @Override
    protected Map<String, ?> idToMap(Pair<Integer, Integer> id) {
        return Map.of("subjectId", id.first(), "teacherId", id.second());
    }

    @Override
    protected String getTableName() {
        return "teacher_subject";
    }

    @Override
    protected Class<TeacherSubject> getEntityClass() {
        return TeacherSubject.class;
    }
}
