<%--
  User: 何处是归程
  Time: 19:47
--%>
<%session.setAttribute("APP_PATH", request.getPathInfo());%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:forward page="${APP=PATH}/toIndex.htm"></jsp:forward>
</body>
</html>
