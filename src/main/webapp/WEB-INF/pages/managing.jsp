<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: Michal Drda
  Date: 05-Nov-18
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Manage CoolBank</title>
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
                <a class="navbar-brand" href="/index.jsp">CoolBank</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav underline-menu">
                    <li><a id="selected-page-item" href="${location.reload(true)}">Managing</a></li>
                    <li><a href="managing/register">Register</a></li>
                    <%--<li><a href="managing/manage_user">User management</a></li>--%>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li id="logged-user"><div class="logged-user"><p>Logged as: ${sessionScope.user}</p></div></li>
                    <li id="login" style="display: none"><a href="/login" onclick="loginFunction()"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                    <li id="logout"><a href="logout" onclick="loginFunction()"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <jsp:include page="generic/alerts.jsp">
        <jsp:param name="err" value="${requestScope.err}"/>
        <jsp:param name="suc" value="${requestScope.suc}"/>
    </jsp:include>


    <div class="container max-width-1300">
        <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-4 max-width-460 row">
            <form action="managing" method="post" class="form-horizontal">
                <div class="row button-menu">
                    <div class="form-group form-input-170 disable-padding">
                        <label class="control-label" for="username">*Username</label>
                        <input class="form-control" type="text" id="username" name="username" value="<c:out value="${requestScope.username}"/>"/>
                    </div>
                    <div class="row button-menu">
                        <button class="btn btn-default toggle-button" name="removeAction" value="remove" type="submit">Remove user</button>
                    </div>
                    <div class="row button-menu">
                        <button class="btn btn-default toggle-button" name="updateAction" value="update" type="submit">Update user</button>
                    </div>
                    <div class="row button-menu">
                        <button class="btn btn-default toggle-button" name="activateAction" value="activate" type="submit">Re-Activate user</button>
                    </div>
                </div>
            </form>
        </div>

        <c:if test="${(not empty requestScope.username) and (requestScope.updateAction eq 'update')}">
            <div class="col-xs-12 col-sm-12 col-md-8 row">
                <form action="managing" method="post" class="form-horizontal">
                    <div class="container max-width-600">
                        <h2>Update user <strong>${requestScope.username}</strong></h2>
                        <div class="container-fluid disable-padding">
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <label class="control-label" for="firstname">*First name</label>
                                    <div class="disable-padding">
                                        <input class="form-control" type="text" id="firstname" name="firstname" value="<c:out value="${requestScope.firstname}"/>"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label" for="lastname">*Last name</label>
                                    <div class="disable-padding">
                                        <input class="form-control" type="text" id="lastname" name="lastname" value="<c:out value="${requestScope.lastname}"/>"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label" for="email">*E-mail</label>
                                    <div class="disable-padding">
                                        <input class="form-control" type="text" id="email" name="email" value="<c:out value="${requestScope.email}"/>"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label" for="gender">*Gender</label>
                                    <div class="disable-padding">
                                        <label class="radio-inline"><input type="radio" name="gender" id="gender" value="male" <c:if test="${requestScope.gender eq 'male'}">checked="checked"</c:if>> Male</label>
                                        <label class="radio-inline"><input type="radio" name="gender" value="female" <c:if test="${requestScope.gender eq 'female'}">checked="checked"</c:if>> Female</label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <label class="control-label" for="address">Address</label>
                                    <div class="disable-padding">
                                        <input class="form-control" type="text" id="address" name="address" value="<c:out value="${requestScope.address}"/>"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label" for="address">City</label>
                                    <div class="disable-padding">
                                        <input class="form-control" type="text" id="city" name="city" value="<c:out value="${requestScope.city}"/>"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label" for="zip">Zip code</label>
                                    <div class="disable-padding">
                                        <input class="form-control" type="text" id="zip" name="zip" value="<c:out value="${requestScope.zip}"/>"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label" for="captchaUser">*Two minus one</label>
                                    <div class="disable-padding">
                                        <input class="form-control" type="text" id="captchaUser" name="captchaUser"/>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-12 col-sm-9 col-md-9 disable-padding">
                                    <button class="btn btn-default" type="submit" name="updateUser" value="updateUser">Update</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </c:if>

        <c:if test="${(not empty requestScope.username) and (requestScope.removeAction eq 'remove')}">
            <div class="col-xs-12 col-sm-12 col-md-8 row">
                <div class="container max-width-600">
                    <form action="managing" method="post" class="form-horizontal form-border box-border">
                        <h2>Surely remove user <strong>${requestScope.username}</strong>?</h2>
                        <div class="form-group">
                            <div class="disable-padding">
                                <label><input type="checkbox" id="deactivate" name="deactivate"/> Just deactivate</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-12 col-sm-9 col-md-9 disable-padding">
                                <button class="btn btn-default" type="submit" name="removeUser" value="removeUser">Proceed</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </c:if>

            <c:if test="${(not empty requestScope.username) and (requestScope.activateAction eq 'activate')}">
                <div class="col-xs-12 col-sm-12 col-md-8 row">
                    <div class="container max-width-600">
                        <form action="managing" method="post" class="form-horizontal form-border box-border">
                            <h2>Surely activate user <strong>${requestScope.username}</strong>?</h2>
                            <div class="form-group">
                                <div class="col-xs-12 col-sm-9 col-md-9 disable-padding">
                                    <button class="btn btn-default" type="submit" name="activateUser" value="activateUser">Activate</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </c:if>



        </div>
    </div>


    <jsp:include page="generic/footer.jsp"/>
</body>
</html>
