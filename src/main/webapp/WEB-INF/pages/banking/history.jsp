<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: Michal Drda
  Date: 06-Nov-18
  Time: 21:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>CoolBank Internet Banking</title>
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
                    <li><a href="/banking">Banking</a></li>
                    <li><a href="pay">New payment</a></li>
                    <li><a id="selected-page-item" href="${location.reload(true)}">Transaction history</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li id="login" style="display: none"><a href="/login" onclick="loginFunction()"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                    <li id="logout"><a href="/logout" onclick="loginFunction()"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <jsp:include page="../generic/alerts.jsp">
        <jsp:param name="err" value="${requestScope.err}"/>
        <jsp:param name="suc" value="${requestScope.suc}"/>
    </jsp:include>

    <div class="container-fluid max-width-1300">
        <div class="row">
            <h1 class="">Transaction history</h1>
        </div>
        <div  class="row">
            <div class="col-xs-12 col-sm-12 col-md-12">
                <table class="table box-background max-width-1300">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Date</th>
                            <th scope="col">In/Out</th>
                            <th scope="col">Account</th>
                            <th scope="col">Amount</th>
                            <th scope="col">Vs</th>
                            <th scope="col">Cs</th>
                            <th scope="col" class="hidden-xs">Ss</th>
                            <th scope="col" class="hidden-xs">Your message</th>
                            <th scope="col" class="hidden-xs">Their message</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${transactions}" var="transaction">
                        <tr>
                            <th scope="row">${transaction.id}</th>
                            <td>${transaction.date}</td>
                            <td>${transaction.direction}</td>
                            <td>${transaction.account}</td>
                            <td>${transaction.amount}</td>
                            <td>${transaction.vs}</td>
                            <td>${transaction.cs}</td>
                            <td class="hidden-xs">${transaction.ss}</td>
                            <td class="hidden-xs">${transaction.yourMessage}</td>
                            <td class="hidden-xs">${transaction.theirMessage}</td>
                        </tr>
                    </c:forEach>
                    <%--
                        <tr>
                            <th scope="row">1</th>
                            <td>21-10-2018</td>
                            <td>In</td>
                            <td>1257725423/3031</td>
                            <td>6435 CZK</td>
                            <td>9</td>
                            <td>991</td>
                            <td class="hidden-xs">1253</td>
                            <td class="hidden-xs">-</td>
                            <td class="hidden-xs">Buy some good alcohol for this, bro!</td>
                        </tr>
                        <tr>
                            <th scope="row">2</th>
                            <td>17-10-2018</td>
                            <td>Out</td>
                            <td>1257725422/3031</td>
                            <td>65 CZK</td>
                            <td>91</td>
                            <td>3241</td>
                            <td class="hidden-xs">45425</td>
                            <td class="hidden-xs">sfdkj nsdf kjaskd jksfjg nafkjds f</td>
                            <td class="hidden-xs">sdfkjn ksjdf jkajsk j kssfjdf kjdf kajsd kjds </td>
                        </tr>
                        <tr>
                            <th scope="row">3</th>
                            <td>15-10-2018</td>
                            <td>Out</td>
                            <td>1257725421/3031</td>
                            <td>635 CZK</td>
                            <td>0123</td>
                            <td>9394</td>
                            <td class="hidden-xs">34098234</td>
                            <td class="hidden-xs">sdkf jkjsdf kjnaskd nsd f</td>
                            <td class="hidden-xs">sdfkjnkfdjnkjnksdf ksdfn kj13k jnskdf nkjnk jsnfd c.</td>
                        </tr>
                        <tr>
                            <th scope="row">4</th>
                            <td>21-10-2018</td>
                            <td>In</td>
                            <td>1257725423/3031</td>
                            <td>6435 CZK</td>
                            <td></td>
                            <td></td>
                            <td class="hidden-xs"></td>
                            <td class="hidden-xs">-</td>
                            <td class="hidden-xs">nothing</td>
                        </tr>
                        <tr>
                            <th scope="row">5</th>
                            <td>17-10-2018</td>
                            <td>Out</td>
                            <td>1257725422/3031</td>
                            <td>65 CZK</td>
                            <td></td>
                            <td>9291</td>
                            <td class="hidden-xs"></td>
                            <td class="hidden-xs">kjsdf kak jksf kjndskf jasd </td>
                            <td class="hidden-xs">sgfkj nkfd</td>
                        </tr>--%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <jsp:include page="../generic/footer.jsp"/>
</body>
</html>
