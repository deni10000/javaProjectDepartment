package controller.console;

import controller.console.base.IO;
import models.Student;
import object_manager.ObjectManager;
import service.StudentService;

import java.util.List;

public class StudentListController extends IO {
    private final StudentService studentService = ObjectManager.get(StudentService.class);

    public void show() {
        List<Student> allStudents = studentService.getAllStudents();
        allStudents.forEach(x -> System.out.printf("%s %s %s курс %s группа %s логин\n", x.name(), x.surname(), x.course(), x.groupNumber(), x.login()));
    }
}
