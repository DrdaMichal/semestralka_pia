<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: Michal Drda
  Date: 30-Sep-18
  Time: 22:35
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login to CoolBank</title>
    <link rel="icon" href="../img/icon-c.jpg">

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../style/style.css">

    <!-- Used for hiding menu - small screens -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Used for hiding menu - small screens -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <!-- Function used for hiding login/logout button -->
    <script src="../js/loginFunction.js"></script>
</head>
<body>
    <nav class="navbar navbar-default navbar-static-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${location.reload(true)}">CoolBank</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav underline-menu">
                    <c:if test="${sessionScope.role.name == 'ADMIN'}">
                        <li><a href="managing">Managing</a></li>
                        <li><a href="managing/register">Register</a></li>
                        <li><a href="managing/manage_user">User management</a></li>
                    </c:if>
                    <c:if test="${sessionScope.role.name == 'USER'}">
                        <li><a href="banking">Banking</a></li>
                        <li><a href="banking/pay">New payment</a></li>
                        <li><a href="history">Payment history</a></li>
                    </c:if>
                    <c:if test="${sessionScope.role.name == 'NOTSET' || empty sessionScope.role}">
                        <li><a href="about">About</a></li>
                    </c:if>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${sessionScope.role.name == 'NOTSET' || empty sessionScope.role}">
                        <li id="login"><a href="${location.reload(true)}" onclick="loginFunction()"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                    </c:if>
                    <c:if  test="${sessionScope.role.name != 'NOTSET' && not empty sessionScope.role}">
                        <li id="logout"><a href="logout" onclick="loginFunction()"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>

    <jsp:include page="WEB-INF/pages/generic/alerts.jsp">
        <jsp:param name="err" value="${requestScope.err}"/>
        <jsp:param name="suc" value="${requestScope.suc}"/>
    </jsp:include>

    <c:if test="${sessionScope.role == 'NOTSET' || empty sessionScope.role}">
        <div class="container-fluid">
            <div class="container login-max-width">
                <h1 class="form-h1-paddings">Login to CoolBank</h1>
                <form action="login" method="post" class="form-horizontal form-border box-border">
                    <div class="container"></div>
                    <div class="form-group">
                        <label class="" for="username">User ID</label>
                        <input class="form-control" type="text" id="username" name="username"/>
                    </div>
                    <div class="form-group">
                        <label for="password">PIN</label>
                        <div class="">
                            <input class="form-control" type="password" id="password" name="password"/>
                        </div>
                    </div>
                    <div class="form-group col-xs-12 ">
                        <button class="btn btn-default" type="submit" value="login">Login</button>
                    </div>
                </form>
            </div>
        </div>
    </c:if>

    <c:if test="${not (sessionScope.role.name == 'NOTSET' || empty sessionScope.role)}">
        <div class="container-fluid">
            <h1 class="text-center">You are already logged in. Please choose what you want to do.</h1>
        </div>
    </c:if>

    <jsp:include page="WEB-INF/pages/generic/footer.jsp"/>
</body>
</html>