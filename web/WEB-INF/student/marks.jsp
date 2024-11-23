<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Оценки</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css"></head>
<body>
<h1>Оценки</h1>

<ul>
    <%
        List<String> marksDetails = (List<String>) request.getAttribute("marksDetails");
        for (String mark : marksDetails) {
    %>
    <li><%= mark %></li>
    <%
        }
    %>
</ul>

<a href="<%= request.getContextPath() + "/student/menu" %>">Меню</a>
</body>
</html>
