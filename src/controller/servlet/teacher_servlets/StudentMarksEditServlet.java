package controller.servlet.teacher_servlets;

import models.Grade;
import models.Student;
import models.Subject;
import object_manager.ObjectManager;
import service.GradeService;
import service.StudentService;
import service.TeacherSubjectService;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/teacher/student-marks-edit")
public class StudentMarksEditServlet extends HttpServlet {

    private final GradeService gradeService = ObjectManager.get(GradeService.class);
    private final TeacherSubjectService teacherSubjectService = ObjectManager.get(TeacherSubjectService.class);
    private final StudentService studentService = ObjectManager.get(StudentService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");

        if (login != null && !login.isEmpty()) {
            Optional<Student> studentOptional = studentService.getStudentByLogin(login);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                request.setAttribute("student", student);

                Integer teacherId = (Integer) request.getSession().getAttribute("userId");
                List<Subject> teacherSubjects = teacherSubjectService.getTeacherSubjects(teacherId);

                Map<Integer, Grade> grades = gradeService.getStudentMarks(student.id()).stream()
                        .collect(Collectors.toMap(Grade::subjectId, grade -> grade));

                request.setAttribute("grades", grades);
                request.setAttribute("subjects", teacherSubjects);
            } else {
                request.setAttribute("errorMessage", "Студент с таким логином не найден.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/teacher/student-marks-edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Integer studentId = Integer.parseInt(request.getParameter("studentId"));
        Integer subjectId = Integer.parseInt(request.getParameter("subjectId"));

        switch (action) {
            case "update" -> {
                String strPoints = request.getParameter("points");
                if (Utils.isInteger(strPoints)) {
                    Integer points = Integer.parseInt(strPoints);
                    Grade grade = new Grade(studentId, subjectId, points);
                    boolean updated = gradeService.updateGrade(grade);
                    request.setAttribute(updated ? "message" : "errorMessage",
                            updated ? "Оценка успешно обновлена." : "Ошибка при обновлении оценки.");
                } else {
                    request.setAttribute("errorMessage", "Передано не число.");
                }
            }

            case "delete" -> {
                boolean deleted = gradeService.deleteGrade(studentId, subjectId);
                request.setAttribute(deleted ? "message" : "errorMessage",
                        deleted ? "Оценка успешно удалена." : "Ошибка при удалении оценки.");
            }

            case "add" -> {
                String newPointsStr = request.getParameter("points");
                if (Utils.isInteger(newPointsStr)) {
                    Integer newPoints = Integer.parseInt(newPointsStr);
                    Grade newGrade = new Grade(studentId, subjectId, newPoints);
                    boolean added = gradeService.addGrade(newGrade);
                    request.setAttribute(added ? "message" : "errorMessage",
                            added ? "Оценка успешно добавлена." : "Ошибка при добавлении оценки.");
                } else {
                    request.setAttribute("errorMessage", "Передано не число.");
                }
            }
        }

        doGet(request, response);
    }
}
