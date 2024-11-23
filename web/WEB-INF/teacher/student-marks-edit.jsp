<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Grade" %>
<%@ page import="models.Subject" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="models.Student" %>
<html>
<head>
    <title>Редактирование оценок студента</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css"></head>
<body>
<h1>Редактирование оценок студента</h1>

<form method="get" action="student-marks-edit">
    <label for="login">Логин студента:</label>
    <input type="text" id="login" name="login" required>
    <button type="submit">Найти</button>
</form>

<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    String message = (String) request.getAttribute("message");
    String login = request.getParameter("login");
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
    Map<Integer, Grade> grades = (Map<Integer, Grade>) request.getAttribute("grades");
    List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
    if (student != null) {
%>
<h2>Оценки студента: <%= student.name() %> <%= student.surname() %> (Логин: <%= student.login() %>)</h2>
<ul>
    <%
        for (Subject subject : subjects) {
            Grade grade = grades.get(subject.id());
            if (grade == null) {
                continue;
            }
    %>
    <li><%= subject.name() %>:
        <%= grade.points() %>
        <form method="post" action="student-marks-edit">
            <input type="hidden" name="login" value="<%= login %>"/>
            <input type="hidden" name="studentId" value="<%= student.id() %>"/>
            <input type="hidden" name="subjectId" value="<%= subject.id() %>"/>
            <label for="points1">Изменить баллы:</label>
            <input type="number" id="points1" name="points" value="<%= grade.points() %>" required/>
            <button type="submit" name="action" value="update">Сохранить</button>
            <button type="submit" name="action" value="delete" style="color: red;">Удалить</button>
        </form>
    </li>
    <%
        }
    %>
</ul>

<h3>Добавить оценку</h3>
<form method="post" action="student-marks-edit">
    <input type="hidden" name="login" value="<%= login %>"/>
    <input type="hidden" name="studentId" value="<%= student.id() %>"/>
    <label for="subjectId">Предмет:</label>
    <select id="subjectId" name="subjectId" required>
        <%
            for (Subject subject : subjects) {
                if (grades.containsKey(subject.id())) {
                    continue;
                }
        %>
        <option value="<%= subject.id() %>"><%= subject.name() %></option>
        <%
            }
        %>
    </select>

    <label for="points">Баллы:</label>
    <input type="number" id="points" name="points" required/>

    <button type="submit" name="action" value="add">Добавить оценку</button>
</form>

<a href="<%= request.getContextPath() + "/teacher/menu" %>">Меню</a>
</body>
</html>
<%
    }
%>
