<%--
  Created by IntelliJ IDEA.
  User: Michal Drda
  Date: 08-Nov-18
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>User management</title>

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
                <a class="navbar-brand" href="index.jsp">CoolBank</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav underline-menu">
                    <li><a href="/managing">Managing</a></li>
                    <li><a href="/register">Register</a></li>
                    <li><a href="${location.reload(true)}">User management</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li id="login" style="display: none"><a href="/login" onclick="loginFunction()"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                    <li id="logout"><a href="/logout" onclick="loginFunction()"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>


    <div class="container-fluid">
        <c:if test="${not empty requestScope.err}">
        <div class="alert alert-danger alert-dismissible">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <p>Error: ${requestScope.err}</p>
        </div>
        </c:if>
        <c:if test="${empty requestScope.chosenAction}">
            <div class="container form-max-width">
                <h1>Choose an action</h1>
                <form action="manage_user" method="post" class="form-horizontal form-border box-border">
                    <div class="container-fluid disable-padding">
                        <div class="col-xs-6 col-sm-6 col-md-6">
                            <div class="form-group">
                                <label class="col-xs-12 col-sm-6 col-md-6 control-label" for="username">*Username</label>
                                <div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
                                    <input class="form-control" type="text" id="username" name="username"/>
                                </div>
                                <label class="col-xs-12 col-sm-6 col-md-6 control-label" for="action">*Action</label>
                                <div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
                                    <label class="radio-inline"><input type="radio" name="action" id="action" value="edit"> Edit</label>
                                    <label class="radio-inline"><input type="radio" name="action" value="remove"> Remove</label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-12 col-sm-offset-3 col-sm-9 col-md-offset-3 col-md-9 disable-padding">
                                <button class="btn btn-default" type="submit" value="chosenAction">Proceed</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </c:if>

        <c:if test="${requestScope.chosenAction == 'edit'}">
            <h1>Edit a user.</h1>
        </c:if>
        <c:if test="${requestScope.chosenAction == 'remove'}">
            <h1>Remove a user.</h1>
        </c:if>

    </div>
</body>
</html>
