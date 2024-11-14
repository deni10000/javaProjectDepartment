package dao.RepositoryImpl.listRepositories;

import dao.RepositoryImpl.listRepositories.base.ListRepository;
import models.Student;
import utils.IdGenerator;

public class StudentRepository extends ListRepository<Student, Integer> {
    public StudentRepository() {
        list.add(new Student(1, "Ivan", "Ivanov", 3, 10, "ivan", 3244570, 2));
    }

    @Override
    public Student saveWithoutId(Student model) {
        Integer id = IdGenerator.nextId(list.stream().map(Student::getId).toList());
        return save(new Student(id, model.name(), model.surname(), model.course(), model.groupNumber(), model.login(), model.passwordHash(), model.cathedraId()));
    }
}
