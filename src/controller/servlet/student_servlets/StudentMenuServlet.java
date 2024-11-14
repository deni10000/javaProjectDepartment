package controller.servlet.student_servlets;

import models.Student;
import object_manager.ObjectManager;
import service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/student/menu")
public class StudentMenuServlet extends HttpServlet {
    StudentService studentService = ObjectManager.get(StudentService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer studentId = (Integer) req.getSession().getAttribute("userId");
        Optional<Student> optionalStudent = studentService.getStudentById(studentId);
        Student student = optionalStudent.orElseThrow();
        req.setAttribute("student", student);
        req.getRequestDispatcher("/WEB-INF/student/menu.jsp").forward(req, resp);

    }
}
