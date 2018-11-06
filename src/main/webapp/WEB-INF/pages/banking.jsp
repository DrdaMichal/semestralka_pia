<%--
  Created by IntelliJ IDEA.
  User: Michal Drda
  Date: 05-Nov-18
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>CoolBank Internet Banking</title>

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
                <a class="navbar-brand" href="main">CoolBank</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <li><a href="pay">New payment</a></li>
                    <li><a href="history">Payment history</a></li>
                    <li><a href="account">Update account</a></li>
                    <li><a href="about">About</a></li>
                    <li><p >Logged in as USER. (for testing purpouses)</p></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li id="register"><a href="/register" style="display: none" onclick="loginFunction()"><span class="glyphicon glyphicon-user"></span> Register</a></li>
                    <li id="login"><a href="/login" style="display: none" onclick="loginFunction()"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                    <li id="logout"><a href="/logout" onclick="loginFunction()"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container-fluid max-width-1300">
        <div class="row">
            <h1 class="">Welcome to CoolBank Internet Banking!</h1>
        </div>
        <div  class="row">
            <div class="col-xs-12 col-sm-6 col-md-5">
                <h2>Your current account</h2>
                <table class="table box-background max-width-460">
                    <thead>
                    <tr>
                        <th scope="col">Account number</th>
                        <th scope="col">Balance</th>
                    </tr>
                    </thead>
                    <td>1257725029/3031</td>
                    <td>6435 CZK</td>
                </table>
            </div>
            <div class="col-xs-12 col-sm-10 col-md-7">
                <h2>Latest transactions</h2>
                <table class="table box-background max-width-700">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Date</th>
                        <th scope="col">In/Out</th>
                        <th scope="col">Account</th>
                        <th scope="col">Amount</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th scope="row">1</th>
                        <td>21-10-2018</td>
                        <td>In</td>
                        <td>1257725423/3031</td>
                        <td>6435 CZK</td>
                    </tr>
                    <tr>
                        <th scope="row">2</th>
                        <td>17-10-2018</td>
                        <td>Out</td>
                        <td>1257725422/3031</td>
                        <td>65 CZK</td>
                    </tr>
                    <tr>
                        <th scope="row">3</th>
                        <td>15-10-2018</td>
                        <td>Out</td>
                        <td>1257725421/3031</td>
                        <td>635 CZK</td>
                    </tr>
                    <tr>
                        <th scope="row">4</th>
                        <td>21-10-2018</td>
                        <td>In</td>
                        <td>1257725423/3031</td>
                        <td>6435 CZK</td>
                    </tr>
                    <tr>
                        <th scope="row">5</th>
                        <td>17-10-2018</td>
                        <td>Out</td>
                        <td>1257725422/3031</td>
                        <td>65 CZK</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <footer id="footer">
        <div id="container-fluid">
            <p class="p-bigger-white">Created by Michal Drda in 2018.</p>
        </div>
    </footer>
</body>
</html>
