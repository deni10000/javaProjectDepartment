<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.TeacherSubject" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Subject" %>
<html>
<head>
    <title>Ваши предметы</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css"></head>
<body>
<h1>Ваши предметы</h1>

<ul>
    <%
        List<Subject> teacherSubjects = (List<Subject>) request.getAttribute("teacherSubjects");
        int i = 1;
        for (Subject subject : teacherSubjects) {
    %>
    <li><%= i++ %>. <%= subject.name() %></li>
    <%
        }
    %>
</ul>

<a href="<%= request.getContextPath() + "/teacher/menu" %>">Меню</a>
</body>
</html>
