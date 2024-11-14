package controller.console;

import controller.console.base.IO;
import models.Grade;
import object_manager.ObjectManager;
import service.GradeService;
import service.SubjectService;

import java.util.List;

public class StudentMarksController extends IO {
    private final GradeService gradeService = ObjectManager.get(GradeService.class);
    private final SubjectService subjectService = ObjectManager.get(SubjectService.class);

    public void show(Integer studentId) {
        List<Grade> studentMarks = gradeService.getStudentMarks(studentId);
        studentMarks.stream().map(x -> subjectService.getSubjectById(x.subjectId()).
                orElseThrow().name() + " " + x.points()).forEach(out::println);
        out.println();
    }
}
