package controller.servlet.teacher_servlets;

import models.Teacher;
import object_manager.ObjectManager;
import service.TeacherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/teacher/menu")
public class TeacherMenuServlet extends HttpServlet {
    private final TeacherService teacherService = ObjectManager.get(TeacherService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer teacherId = (Integer) req.getSession().getAttribute("userId");
        Optional<Teacher> optionalTeacher = teacherService.getTeacherById(teacherId);
        Teacher teacher = optionalTeacher.orElseThrow();
        req.setAttribute("teacher", teacher);
        req.getRequestDispatcher("/WEB-INF/teacher/menu.jsp").forward(req, resp);


    }
}
