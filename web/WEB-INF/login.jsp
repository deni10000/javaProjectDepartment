<!-- web/login.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
    <title>Login</title>
</head>
<body>
<h2>Вход</h2>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
<p style="color: red;"><%= errorMessage %></p>
<%
    }
%>
<form action="login" method="post">
    <label for="username">Логин:</label>
    <input type="text" id="username" name="username" required>
    <br><br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>
    <br><br>
    <label>
        Войти как преподаватель:
        <input name="isTeacher" type="checkbox">
    </label>
    <br><br>
    <button type="submit">Войти</button>
</form>
</body>
</html>
