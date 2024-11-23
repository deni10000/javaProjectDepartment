package controller.servlet.student_servlets;

import models.Grade;
import models.Subject;
import object_manager.ObjectManager;
import service.GradeService;
import service.SubjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/student/marks")
public class StudentMarksServlet extends HttpServlet {

    private final GradeService gradeService = ObjectManager.get(GradeService.class);
    private final SubjectService subjectService = ObjectManager.get(SubjectService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer studentId = (Integer) request.getSession().getAttribute("userId");
        List<Grade> studentMarks = gradeService.getStudentMarks(studentId);

        List<String> marksDetails = studentMarks.stream()
                .map(grade -> {
                    Optional<Subject> subject = subjectService.getSubjectById(grade.subjectId());
                    return subject.map(s -> s.name() + ": " + grade.points()).orElse("Неизвестный предмет");
                })
                .toList();

        request.setAttribute("marksDetails", marksDetails);
        request.setAttribute("studentId", studentId);

        request.getRequestDispatcher("/WEB-INF/student/marks.jsp").forward(request, response);
    }
}
