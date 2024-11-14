package controller.console;

import controller.console.base.IO;
import models.Student;
import object_manager.ObjectManager;
import service.StudentService;
import utils.HashCode;

public class AddStudentController extends IO {
    private final StudentService studentService = ObjectManager.get(StudentService.class);

    public void show() {
        while (true) {
            out.println("Введите имя:");
            String name = in.next();
            out.println("Введите фамилию:");
            String surname = in.next();
            out.println("Введите курс:");
            while (!in.hasNextInt()) {
                out.println("Введите число");
                in.next();
            }
            int course = in.nextInt();
            out.println("Введите группу:");
            while (!in.hasNextInt()) {
                out.println("Введите число");
                in.next();
            }
            int group = in.nextInt();
            out.println("Введите логин:");
            String login = in.next();
            out.println("Введите пароль:");
            String password = in.next();
            out.println("Введите id кафедры:");
            while (!in.hasNextInt()) {
                out.println("Введите число");
                in.next();
            }
            int cathedra = in.nextInt();

            Student student = new Student(null, name, surname, course, group, login, HashCode.hash(password), cathedra);

            boolean flag = studentService.addStudent(student);
            if (flag) break;
            out.println("Некорректные данные");
        }

    }
}
