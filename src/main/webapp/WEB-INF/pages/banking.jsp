<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: Michal Drda
  Date: 05-Nov-18
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>CoolBank - internet banking</title>
        <link rel="icon" href="../../img/icon-c.jpg">

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <!-- Pages general style sheet -->
        <link rel="stylesheet" type="text/css" href="../../style/style.css">

        <!-- Used for hiding menu - small screens -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <!-- Used for hiding menu - small screens -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <!-- Function used for hiding login/logout button -->
        <script src="../../js/loginFunction.js"></script>
        <!-- Function used for hiding and showing forms -->
        <script src="../../js/userUpdateShowFunctions.js"></script>

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
                        <li><a id="selected-page-item" href="${location.reload(true)}">Banking</a></li>
                        <li><a href="banking/pay">New payment</a></li>
                        <li><a href="banking/history">Transaction history</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li id="logged-user"><div class="logged-user"><p>Logged as: ${sessionScope.user}</p></div></li>
                        <li id="login" style="display: none"><a href="/login" onclick="loginFunction()"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                        <li id="logout"><a href="/logout" onclick="loginFunction()"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                    </ul>
                </div>

            </div>
        </nav>

        <jsp:include page="generic/alerts.jsp">
            <jsp:param name="err" value="${requestScope.err}"/>
            <jsp:param name="suc" value="${requestScope.suc}"/>
        </jsp:include>

        <div class="container-fluid max-width-1300">

            <div  class="row">
                <div class="col-xs-12 col-sm-12 col-md-4 disable-padding">

                    <div id="accountInfo">
                        <h2>Your banking account</h2>
                        <table class="table box-background max-width-460">
                            <thead>
                            <tr>
                                <th scope="col">Account number</th>
                                <th scope="col">Balance</th>
                            </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${requestScope.accountNumber}</td>
                                    <td>${requestScope.balance}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="row" id="changeButtons">
                        <div class="max-width-460">
                            <div class="row button-menu">
                                <button class="btn btn-default toggle-button" onclick="showPasswordUpdateForm()">Change password</button>
                            </div>
                            <div class="row button-menu">
                                <button class="btn btn-default toggle-button" onclick="showUserInfoUpdateForm()">Change user info</button>
                            </div>
                        </div>
                    </div>

                </div>

                <div id="showPasswordUpdateForm" style="display: none" class="col-xs-12 col-sm-12 col-md-8">
                    <div class="container max-width-330">

                        <h2>Change password</h2>
                        <form action="banking" method="post" class="form-horizontal">
                            <div class="form-group">
                                <label class="control-label" for="oldPwd">*Old PIN</label>
                                <div class="disable-padding">
                                    <input class="form-control" type="password" id="oldPwd" name="oldPwd"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="newPwd">*New PIN</label>
                                <div class="disable-padding">
                                    <input class="form-control" type="password" id="newPwd" name="newPwd"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="confirmPwd">*Confirm PIN</label>
                                <div class="disable-padding">
                                    <input class="form-control" type="password" id="confirmPwd" name="confirmPwd"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="captchaPwd">*Five minus two</label>
                                <div class="disable-padding">
                                    <input class="form-control" type="text" id="captchaPwd" name="captchaPwd"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-12 col-sm-9 col-md-9 disable-padding">
                                    <button class="btn btn-default" type="submit" name="updatePsw" value="updatePsw">Update PIN</button>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>

                <div class="col-xs-12 col-sm-12 col-md-8 row" id="showUserInfoUpdateForm" style="display: none">
                    <div class="container max-width-600">

                        <h2>Change account information</h2>
                        <form action="banking" method="post" class="form-horizontal">
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
                                        <label class="control-label" for="captchaUser">*Six plus one</label>
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
                        </form>

                    </div>
                </div>

            </div>

            <div class="row" id="transactions">
                <h2>Latest transactions</h2>
                <jsp:include page="generic/transactionsTable.jsp"/>
            </div>

        </div>

        <jsp:include page="generic/footer.jsp"/>

    </body>
</html>
