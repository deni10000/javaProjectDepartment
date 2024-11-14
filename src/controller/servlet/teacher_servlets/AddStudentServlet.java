package controller.servlet.teacher_servlets;

import models.Student;
import object_manager.ObjectManager;
import service.CathedraService;
import service.StudentService;
import utils.HashCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/teacher/add-student")
public class AddStudentServlet extends HttpServlet {
    private final StudentService studentService = ObjectManager.get(StudentService.class);
    private final CathedraService cathedraService = ObjectManager.get(CathedraService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("cathedraOptions", cathedraService.getCathedrasInMap());
        request.getRequestDispatcher("/WEB-INF/teacher/add-student.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        int course = Integer.parseInt(request.getParameter("course"));
        int group = Integer.parseInt(request.getParameter("group"));
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        int cathedra = Integer.parseInt(request.getParameter("cathedra"));

        Student student = new Student(null, name, surname, course, group, login, HashCode.hash(password), cathedra);

        boolean success = studentService.addStudent(student);
        request.setAttribute("cathedraOptions", cathedraService.getCathedrasInMap());

        if (success) {
            request.setAttribute("successMessage", "Студент успешно добавлен.");
            request.getRequestDispatcher("/WEB-INF/teacher/add-student.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Некорректные данные");
            request.getRequestDispatcher("/WEB-INF/teacher/add-student.jsp").forward(request, response);
        }
    }
}
