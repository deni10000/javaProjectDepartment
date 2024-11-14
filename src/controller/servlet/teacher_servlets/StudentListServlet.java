package controller.servlet.teacher_servlets;

import models.Student;
import service.SortedStudentService;
import service.StudentService;
import object_manager.ObjectManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/teacher/student-list")
public class StudentListServlet extends HttpServlet {

    private final StudentService studentService = ObjectManager.get(SortedStudentService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nameFilter = request.getParameter("name");
        String surnameFilter = request.getParameter("surname");
        String courseFilter = request.getParameter("course");
        String groupNumberFilter = request.getParameter("group_number");
        String pageParam = request.getParameter("page");

        nameFilter = nameFilter == null ? "" : nameFilter;
        surnameFilter = surnameFilter == null ? "" : surnameFilter;
        courseFilter = courseFilter == null ? "" : courseFilter;
        groupNumberFilter = groupNumberFilter == null ? "" : groupNumberFilter;

        int pageNumber = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
        int pageSize = 50;

        Map<String, Object> params = new HashMap<>();
        if (!nameFilter.isEmpty()) {
            params.put("name", nameFilter);
        }
        if (!surnameFilter.isEmpty()) {
            params.put("surname", surnameFilter);
        }
        if (!courseFilter.isEmpty()) {
            params.put("course", Integer.parseInt(courseFilter));
        }
        if (!groupNumberFilter.isEmpty()) {
            params.put("group_number", Integer.parseInt(groupNumberFilter));
        }

        List<Student> students = studentService.getStudents(params, pageNumber, pageSize);
        int totalRecords = studentService.getStudentsCount(params);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("students", students);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("nameFilter", nameFilter);
        request.setAttribute("surnameFilter", surnameFilter);
        request.setAttribute("courseFilter", courseFilter);
        request.setAttribute("groupNumberFilter", groupNumberFilter); // Передаем фильтр по group_number в JSP

        request.getRequestDispatcher("/WEB-INF/teacher/student-list.jsp").forward(request, response);
    }
}
