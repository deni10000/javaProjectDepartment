package dao.RepositoryImpl.listRepositories;

import dao.RepositoryImpl.listRepositories.base.ListRepository;
import models.Teacher;
import utils.IdGenerator;

public class TeacherRepository extends ListRepository<Teacher, Integer> {
    public TeacherRepository() {
        list.add(new Teacher(1, "Дмитрий", "Соломатин", "19419409", "admin", 92668751, 1));
    }

    @Override
    public Teacher saveWithoutId(Teacher model) {
        Integer id = IdGenerator.nextId(list.stream().map(Teacher::getId).toList());
        return save(new Teacher(id, model.name(), model.surname(), model.phoneNumber(), model.login(), model.passwordHash(), model.cathedraId()));
    }
}
