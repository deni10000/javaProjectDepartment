<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Teacher" %>
<% Teacher teacher = (Teacher)request.getAttribute("teacher"); %>
<!DOCTYPE html>
<html>
<head>
    <title>Teacher Dashboard</title>
</head>
<body>
<h1>Добро пожаловать, <%= teacher.name() %> <%= teacher.surname() %>!</h1>
<h2>Ваши действия:</h2>
<ul>
    <li><a href="<%= request.getContextPath() + "/teacher-subjects" %>">Посмотреть свои предметы</a></li>
    <li><a href="<%= request.getContextPath() + "/teacher/add-student" %>">Добавить студента</a></li>
    <li><a href="<%= request.getContextPath() + "/teacher/student-edit" %>">Изменить студента</a></li>
    <li><a href="<%= request.getContextPath() + "/teacher/student-list" %>">Посмотреть студентов</a></li>
</ul>
<form action="<%= request.getContextPath() + "/logout" %>" method="post">
    <button type="submit">Выйти</button>
</form>
</body>
</html>
