package controller.servlet.teacher_servlets;

import models.Student;
import object_manager.ObjectManager;
import service.CathedraService;
import service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@WebServlet("/teacher/student-edit")
public class StudentEditServlet extends HttpServlet {

    private final StudentService studentService = ObjectManager.get(StudentService.class);
    private final CathedraService cathedraService = ObjectManager.get(CathedraService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");

        if (login != null && !login.isEmpty()) {
            Optional<Student> studentOptional = studentService.getStudentByLogin(login);
            if (studentOptional.isPresent()) {
                request.setAttribute("student", studentOptional.get());
            } else {
                request.setAttribute("errorMessage", "Студент с таким логином не найден.");
            }
        }

        Map<Integer, String> cathedras = cathedraService.getCathedrasInMap();
        request.setAttribute("cathedras", cathedras);

        request.getRequestDispatcher("/WEB-INF/teacher/student-edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String login = request.getParameter("login");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        Integer course = Integer.parseInt(request.getParameter("course"));
        Integer groupNumber = Integer.parseInt(request.getParameter("groupNumber"));
        Integer cathedraId = Integer.parseInt(request.getParameter("cathedraId"));
        String action = request.getParameter("action");

        if (login == null || login.isEmpty()) {
            request.setAttribute("errorMessage", "Логин не может быть пустым.");
            forwardToEditPage(request, response);
            return;
        }

        Optional<Student> studentOptional = studentService.getStudentByLogin(login);

        if (studentOptional.isEmpty()) {
            request.setAttribute("errorMessage", "Студент с таким логином не найден.");
            forwardToEditPage(request, response);
            return;
        }
        switch (action) {
            case "update" -> {
                Student student = studentOptional.get();
                Student updatedStudent = new Student(student.id(), name, surname, course, groupNumber, login, student.passwordHash(), cathedraId);
                boolean updated = studentService.updateStudent(updatedStudent);
                request.setAttribute(updated ? "message" : "errorMessage", updated ? "Студент успешно обновлен." : "Ошибка при обновлении студента.");
            }
            case "delete" -> {
                boolean deleted = studentService.deleteStudent(studentOptional.get().id());
                request.setAttribute(deleted ? "message" : "errorMessage", deleted ? "Студент успешно удален." : "Ошибка при удалении студента.");
            }
            default -> request.setAttribute("errorMessage", "Неизвестное действие.");
        }

        forwardToEditPage(request, response);
    }

    private void forwardToEditPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<Integer, String> cathedras = cathedraService.getCathedrasInMap();
        request.setAttribute("cathedras", cathedras);
        request.getRequestDispatcher("/WEB-INF/teacher/student-edit.jsp").forward(request, response);
    }
}
