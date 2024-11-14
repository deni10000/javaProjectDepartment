package service;

import dao.RepositoryImpl.dbRepositories.StudentRepository;
import models.Student;
import object_manager.ObjectManager;
import utils.HashCode;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StudentService {

    protected StudentRepository studentRepository = ObjectManager.get(StudentRepository.class);

    public Optional<Student> getStudentByLoginAndPassword(String login, String password) {
        Integer passwordHash = HashCode.hash(password);
        List<Student> students = studentRepository.findAllWithParameter(Map.of("login", login, "passwordHash", passwordHash));
        if (students.size() > 1) {
            throw new RuntimeException("Почему-то больше одного пользователя");
        } else if (students.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(students.getFirst());
        }

    }

    public Optional<Student> getStudentByLogin(String login) {
        List<Student> students = studentRepository.findAllWithParameter(Map.of("login", login));
        if (students.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(students.getFirst());
        }
    }

    public boolean updateStudent(Student student) {
        return studentRepository.update(student);
    }

    public boolean deleteStudent(Integer id) {
        return studentRepository.delete(id);
    }

    public Optional<Student> getStudentById(int id) {
        return studentRepository.findById(id);
    }

    public boolean addStudent(Student student) {
        if (student.course() > 6 || student.course() < 1) {
            return false;
        }
        if (!studentRepository.findAllWithParameter(Map.of("login", student.login())).isEmpty()) {
            return false;
        }
        studentRepository.save(student);
        return true;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getAllStudents(Integer pageNumber, Integer pageSize) {
        return studentRepository.findAll(pageNumber, pageSize);
    }

    public List<Student> getStudents(Map<String, ?> params, Integer pageNumber, Integer pageSize) {
        return studentRepository.findAllWithParameter(params, pageNumber, pageSize);
    }

    public int getStudentsCount(Map<String, ?> params) {
        return studentRepository.countWithParameter(params);
    }

}
