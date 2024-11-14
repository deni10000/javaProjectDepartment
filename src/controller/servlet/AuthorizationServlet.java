package controller.servlet;

import models.Student;
import models.Teacher;
import object_manager.ObjectManager;
import service.StudentService;
import service.TeacherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class AuthorizationServlet extends HttpServlet {
    private final TeacherService teacherService = ObjectManager.get(TeacherService.class);
    private final StudentService studentService = ObjectManager.get(StudentService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("username");
        String password = request.getParameter("password");
        boolean isTeacher = request.getParameter("isTeacher") != null;
        Integer userId;
        if (isTeacher) {
            userId = teacherService.getTeacherByLoginAndPassword(login, password).map(Teacher::getId).orElse(null);
        } else {
            userId = studentService.getStudentByLoginAndPassword(login, password).map(Student::getId).orElse(null);
        }
        if (userId == null) {
            request.setAttribute("errorMessage", "Неверный логин или пароль.");
            request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("userId", userId);
            request.getSession().setAttribute("isTeacher", isTeacher);
            if (isTeacher) {
                response.sendRedirect(request.getContextPath() + "/teacher/menu");
            } else {
                response.sendRedirect(request.getContextPath() + "/student/menu");
            }
        }

    }
}
