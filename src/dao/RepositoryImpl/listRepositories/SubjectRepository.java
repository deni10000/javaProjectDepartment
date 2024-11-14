package dao.RepositoryImpl.listRepositories;

import dao.RepositoryImpl.listRepositories.base.ListRepository;
import models.Subject;
import utils.IdGenerator;

public class SubjectRepository extends ListRepository<Subject, Integer> {

    public SubjectRepository() {
        list.add(new Subject(1, "Математика", 1));
    }

    @Override
    public Subject saveWithoutId(Subject model) {
        Integer id = IdGenerator.nextId(list.stream().map(Subject::getId).toList());
        return save(new Subject(id, model.name(), model.assessmentId()));
    }
}
