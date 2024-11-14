package dao.RepositoryImpl.listRepositories;

import dao.RepositoryImpl.listRepositories.base.ListRepository;
import models.Grade;
import utils.Pair;

public class GradeRepository extends ListRepository<Grade, Pair<Integer, Integer>> {
    public GradeRepository() {
        list.add(new Grade(1, 1, 50));
    }

    @Override
    public Grade saveWithoutId(Grade model) {
        throw new RuntimeException("Id не может быть null");
    }
}
