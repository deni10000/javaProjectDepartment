<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Student" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Список студентов</title>
</head>
<body>
<h1>Список студентов</h1>

<form method="get" action="student-list">
    <label for="name">Имя:</label>
    <input type="text" id="name" name="name" value="<%= request.getAttribute("nameFilter") != null ? request.getAttribute("nameFilter") : "" %>"/>

    <label for="surname">Фамилия:</label>
    <input type="text" id="surname" name="surname" value="<%= request.getAttribute("surnameFilter") != null ? request.getAttribute("surnameFilter") : "" %>"/>

    <label for="course">Курс:</label>
    <input type="number" id="course" name="course" value="<%= request.getAttribute("courseFilter") != null ? request.getAttribute("courseFilter") : "" %>"/>

    <label for="group_number">Номер группы:</label>
    <input type="number" id="group_number" name="group_number" value="<%= request.getAttribute("groupNumberFilter") != null ? request.getAttribute("groupNumberFilter") : "" %>"/>

    <button type="submit">Фильтровать</button>
</form>

<p>Всего записей: <%= request.getAttribute("totalRecords") %></p>

<table border="1">
    <tr>
        <th>Фамилия</th>
        <th>Имя</th>
        <th>Курс</th>
        <th>Группа</th>
        <th>Логин</th>
    </tr>
    <%
        List<Student> students = (List<Student>) request.getAttribute("students");
        for (Student student : students) {
    %>
    <tr>
        <td><%= student.surname() %></td>
        <td><%= student.name() %></td>
        <td><%= student.course() %></td>
        <td><%= student.groupNumber() %></td>
        <td><%= student.login() %></td>
    </tr>
    <%
        }
    %>
</table>

<div>
    <%
        Integer currentPage = (Integer) request.getAttribute("currentPage");
        Integer totalPages = (Integer) request.getAttribute("totalPages");

        if (currentPage > 1) {
    %>
    <a href="student-list?page=<%= currentPage - 1 %>&name=<%= request.getAttribute("nameFilter") %>&surname=<%= request.getAttribute("surnameFilter") %>&course=<%= request.getAttribute("courseFilter") %>&group_number=<%= request.getAttribute("groupNumberFilter") %>">« Назад</a>
    <%
        }

        if (currentPage < totalPages) {
    %>
    <a href="student-list?page=<%= currentPage + 1 %>&name=<%= request.getAttribute("nameFilter") %>&surname=<%= request.getAttribute("surnameFilter") %>&course=<%= request.getAttribute("courseFilter") %>&group_number=<%= request.getAttribute("groupNumberFilter") %>">Вперед »</a>
    <%
        }
    %>
</div>

<p>Страница <%= request.getAttribute("currentPage") %> из <%= request.getAttribute("totalPages") %></p>

<a href="<%= request.getContextPath() + "/teacher/menu" %>">Меню</a>
</body>
</html>
