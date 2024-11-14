package controller.console;

import controller.console.base.IO;
import models.Student;
import object_manager.ObjectManager;
import service.StudentService;

public class StudentController extends IO {
    private final StudentMarksController studentMarksController = ObjectManager.get(StudentMarksController.class);
    private final StudentService studentService = ObjectManager.get(StudentService.class);

    public void show(Integer studentId) {
        Student student = studentService.getStudentById(studentId).orElseThrow();

        String name = student.name();
        String surname = student.surname();

        String inp;
        boolean flag = true;
        do {
            out.println(name + " " + surname + " " + student.course() + "  курс " + student.groupNumber() + " группа");

            out.println("""
                    1. Посмотреть свои оценки
                    -. Выйти
                    """);
            inp = in.next();
            switch (inp) {
                case "1" -> studentMarksController.show(studentId);
                case "-" -> flag = false;
            }
        } while (flag);
    }
}
