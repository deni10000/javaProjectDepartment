<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Добавить студента</title>
</head>
<body>
<h1>Добавить студента</h1>

<% if (request.getAttribute("successMessage") != null) { %>
<p style="color: green;"><%= request.getAttribute("successMessage") %></p>
<% } %>

<% if (request.getAttribute("errorMessage") != null) { %>
<p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
<% } %>

<form action="${pageContext.request.contextPath}/teacher/add-student" method="post">
    <label for="name">Имя:</label>
    <input type="text" id="name" name="name" required><br>

    <label for="surname">Фамилия:</label>
    <input type="text" id="surname" name="surname" required><br>

    <label for="course">Курс:</label>
    <select id="course" name="course" required>
        <% for (int i = 1; i <= 6; i++) { %>
        <option value="<%= i %>"><%= i %> курс</option>
        <% } %>
    </select><br>

    <label for="group">Группа:</label>
    <input type="number" id="group" name="group" required><br>

    <label for="login">Логин:</label>
    <input type="text" id="login" name="login" required><br>

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required><br>

    <label for="cathedra">Кафедра:</label>
    <select id="cathedra" name="cathedra" required>
        <%
            Map<Integer, String> cathedraOptions = (Map<Integer, String>) request.getAttribute("cathedraOptions");
            for (Map.Entry<Integer, String> entry : cathedraOptions.entrySet()) {
        %>
        <option value="<%= entry.getKey() %>"><%= entry.getValue() %></option>
        <% } %>
    </select><br>
    <input type="submit" value="Добавить">
</form>

<a href="<%= request.getContextPath() + "/teacher/menu" %>">Меню</a>
</body>
</html>
