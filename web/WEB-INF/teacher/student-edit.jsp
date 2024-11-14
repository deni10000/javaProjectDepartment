<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Student" %>
<%@ page import="models.Cathedra" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <title>Редактирование студента</title>
</head>
<body>
<h1>Редактирование студента</h1>

<form method="get" action="student-edit">
    <label for="login">Логин студента:</label>
    <input type="text" id="login" name="login" required>
    <button type="submit">Найти</button>
</form>

<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    String message = (String) request.getAttribute("message");
    if (errorMessage != null) {
%>
<p style="color: red;"><%= errorMessage %></p>
<%
} else if (message != null) {
%>
<p style="color: green;"><%= message %></p>
<%
    }

    Student student = (Student) request.getAttribute("student");
    Map<Integer, String> cathedras = (Map<Integer, String>) request.getAttribute("cathedras");
    if (student != null) {
%>
<form method="post" action="student-edit">
    <input type="hidden" name="login" value="<%= student.login() %>"/>

    <label for="name">Имя:</label>
    <input type="text" id="name" name="name" value="<%= student.name() %>" required/>

    <label for="surname">Фамилия:</label>
    <input type="text" id="surname" name="surname" value="<%= student.surname() %>" required/>

    <label for="course">Курс:</label>
    <input type="number" id="course" name="course" value="<%= student.course() %>" required/>

    <label for="groupNumber">Номер группы:</label>
    <input type="number" id="groupNumber" name="groupNumber" value="<%= student.groupNumber() %>" required/>

    <label for="cathedraId">Кафедра:</label>
    <select id="cathedraId" name="cathedraId" required>
        <%
            for (Map.Entry<Integer, String> entry : cathedras.entrySet()) {
        %>
        <option value="<%= entry.getKey() %>" <%= student.cathedraId() != null && student.cathedraId().equals(entry.getKey()) ? "selected" : "" %>><%= entry.getValue() %></option>
        <%
            }
        %>
    </select>

    <button type="submit" name="action" value="update">Сохранить изменения</button>
    <button type="submit" name="action" value="delete" onclick="return confirm('Вы уверены, что хотите удалить студента?')">Удалить студента</button>
</form>
<%
    }
%>
<a href="<%= request.getContextPath() + "/teacher/menu" %>">Меню</a>
</body>
</html>
