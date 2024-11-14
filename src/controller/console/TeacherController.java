package controller.console;

import controller.console.base.IO;
import models.Teacher;
import object_manager.ObjectManager;
import service.TeacherService;

public class TeacherController extends IO {
    private final TeacherSubjectController teacherSubjectController = ObjectManager.get(TeacherSubjectController.class);
    private final AddStudentController addStudentController = ObjectManager.get(AddStudentController.class);
    private final StudentListController studentListController = ObjectManager.get(StudentListController.class);
    private final TeacherService teacherService = ObjectManager.get(TeacherService.class);

    public void show(Integer teacherId) {
        Teacher teacher = teacherService.getTeacherById(teacherId).orElseThrow();
        String name = teacher.name();
        String surname = teacher.surname();
        String inp;
        boolean flag = true;
        do {
            out.println(name + " " + surname);
            out.println("""
                    1. Посмотреть свои предметы
                    2. Добавить студента
                    3. Посмотреть студентов
                    -. Выйти
                    """);
            inp = in.next();
            switch (inp) {
                case "1" -> teacherSubjectController.show(teacherId);
                case "2" -> addStudentController.show();
                case "3" -> studentListController.show();
                case "-" -> flag = false;
            }
        } while (flag);

    }
}
