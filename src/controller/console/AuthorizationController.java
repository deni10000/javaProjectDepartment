package controller.console;

import controller.console.base.IO;
import models.Student;
import models.Teacher;
import object_manager.ObjectManager;
import service.StudentService;
import service.TeacherService;

import java.util.Optional;

public class AuthorizationController extends IO {
    private final TeacherService teacherService = ObjectManager.get(TeacherService.class);
    private final StudentService studentService = ObjectManager.get(StudentService.class);
    private final TeacherController teacherController = ObjectManager.get(TeacherController.class);
    private final StudentController studentController = ObjectManager.get(StudentController.class);

    public Optional<Integer> getUserId(String login, String password, boolean isTeacher) {
        if (isTeacher) {
            Optional<Teacher> teacher = teacherService.getTeacherByLoginAndPassword(login, password);
            return teacher.map(Teacher::getId);
        } else {
            Optional<Student> student = studentService.getStudentByLoginAndPassword(login, password);
            return student.map(Student::getId);
        }
    }


    public void show() {
        Integer userId;
        while (true) {
            out.println("""
                    1. Войти как преподаватель
                    2. Войти как студент
                    """);
            String inp;
            Boolean isTeacher = null;
            do {
                inp = in.next();
                switch (inp) {
                    case "1" -> isTeacher = true;
                    case "2" -> isTeacher = false;
                }
            } while (isTeacher == null);
            do {
                out.println("Введите логин:");
                String login = in.next();
                out.println("Введите пароль:");
                String password = in.next();
                Optional<Integer> optionalUserId = getUserId(login, password, isTeacher);
                if (optionalUserId.isPresent()) {
                    userId = optionalUserId.get();
                    break;
                }
                out.println("Неправильные данные");
            } while (true);

            if (isTeacher) {
                teacherController.show(userId);
            } else {
                studentController.show(userId);
            }
        }
    }

}
