package dao.RepositoryImpl.listRepositories;

import dao.RepositoryImpl.listRepositories.base.ListRepository;
import models.TeacherSubject;
import utils.Pair;

public class TeacherSubjectRepository extends ListRepository<TeacherSubject, Pair<Integer, Integer>> {

    public TeacherSubjectRepository() {
        list.add(new TeacherSubject(1, 1));
    }

    @Override
    public TeacherSubject saveWithoutId(TeacherSubject model) {
        throw new RuntimeException("Id не может быть null");
    }
}
