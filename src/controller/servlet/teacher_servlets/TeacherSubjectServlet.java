package controller.servlet.teacher_servlets;

import models.Subject;
import object_manager.ObjectManager;
import service.TeacherSubjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/teacher/subjects")
public class TeacherSubjectServlet extends HttpServlet {

    private final TeacherSubjectService teacherSubjectService = ObjectManager.get(TeacherSubjectService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer teacherId = (Integer) request.getSession().getAttribute("userId");
        List<Subject> teacherSubjects = teacherSubjectService.getTeacherSubjects(teacherId);

        request.setAttribute("teacherSubjects", teacherSubjects);
        request.setAttribute("teacherId", teacherId);

        request.getRequestDispatcher("/WEB-INF/teacher/subjects.jsp").forward(request, response);
    }
}
