package controller.console;

import controller.console.base.IO;
import object_manager.ObjectManager;
import service.TeacherSubjectService;

public class TeacherSubjectController extends IO {
    private final TeacherSubjectService teacherSubjectService = ObjectManager.get(TeacherSubjectService.class);

    public void show(Integer teacherId) {
        int[] k = {1};
        teacherSubjectService.getTeacherSubjects(teacherId).
                forEach(x -> {
                    out.println(k[0] + ". " + x.name());
                    k[0] += 1;
                });
        out.println();
/*        String inp;
        boolean flag = true;
        do {
            out.println("""
                1. Вернутся назад
                """);
            inp = in.next();
            switch (inp) {
                case "1" -> flag = false;
            }
        } while (flag);*/
    }
}
