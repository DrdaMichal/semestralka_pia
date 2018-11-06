<!DOCTYPE html>
<!--
Description: Login form page.

Author: Michal Drda

Version:	ver  / DD-MM-CCYY / comment
0.01 / 28-10-2017 / initial version
0.02 / 25-10-2018 / refactoring to 2018 project (Internet Main)

-->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login to CoolBank</title>

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
                <a class="navbar-brand" href="index.jsp">CoolBank</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <li><a href="about">About</a></li>
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
        <div class="container login-max-width">
            <h1 class="form-h1-paddings">Login to CoolBank</h1>
            <form action="login" method="post" class="form-horizontal form-border box-border">
                <sec:csrfInput/>
                <sec:csrfMetaTags/>
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

    <footer id="footer">
        <div class="container-fluid">
            <p class="p-bigger-white">Created by Michal Drda in 2018.</p>
        </div>
    </footer>
</body>
</html>