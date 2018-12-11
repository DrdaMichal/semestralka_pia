<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: Michal Drda
  Date: 06-Nov-18
  Time: 21:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Payment history</title>
    <link rel="icon" href="../img/icon-c.jpg">
</head>
<body>

    <jsp:include page="../generic/alerts.jsp">
        <jsp:param name="err" value="${requestScope.err}"/>
        <jsp:param name="suc" value="${requestScope.suc}"/>
    </jsp:include>

    <jsp:include page="../generic/footer.jsp"/>
</body>
</html>
