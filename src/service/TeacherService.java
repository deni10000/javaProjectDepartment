package service;

import dao.RepositoryImpl.dbRepositories.TeacherRepository;
import models.Teacher;
import object_manager.ObjectManager;
import utils.HashCode;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TeacherService {
    private final TeacherRepository teacherRepository = ObjectManager.get(TeacherRepository.class);

    public Optional<Teacher> getTeacherByLoginAndPassword(String login, String password) {
        Integer passwordHash = HashCode.hash(password);
        List<Teacher> teachers = teacherRepository.findAllWithParameter(Map.of("login", login, "passwordHash", passwordHash));
        if (teachers.size() > 1) {
            throw new RuntimeException("Почему-то больше одного пользователя");
        } else if (teachers.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(teachers.getFirst());
        }

    }

    public Optional<Teacher> getTeacherById(Integer id) {
        return teacherRepository.findById(id);
    }

}
