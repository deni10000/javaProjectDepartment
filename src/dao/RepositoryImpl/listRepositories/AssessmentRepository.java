package dao.RepositoryImpl.listRepositories;

import dao.RepositoryImpl.listRepositories.base.ListRepository;
import models.Assessment;
import utils.IdGenerator;

public class AssessmentRepository extends ListRepository<Assessment, Integer> {

    @Override
    public Assessment saveWithoutId(Assessment model) {
        Integer id = IdGenerator.nextId(list.stream().map(Assessment::getId).toList());
        return save(new Assessment(id, model.name()));
    }
}
