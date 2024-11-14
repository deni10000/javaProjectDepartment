<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Student" %>
<%Student student = (Student) request.getAttribute("student");%>
<!DOCTYPE html>
<html>
<head>
    <title>Информация о студенте</title>
</head>
<body>
<h1>Информация о студенте</h1>

<p>Имя: <%= student.name() %></p>
<p>Фамилия: <%= student.surname() %></p>
<p>Курс: <%= student.course() %></p>
<p>Группа: <%= student.groupNumber() %></p>

<form action="${pageContext.request.contextPath}/studentMarks" method="get">
    <input type="hidden" name="id" value="<%= student.id() %>">
    <input type="submit" value="Посмотреть свои оценки">
</form>

<form action="${pageContext.request.contextPath}/logout" method="post">
    <input type="submit" value="Выход">
</form>
</body>
</html>
