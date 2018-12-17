<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: Michal Drda
  Date: 06-Nov-18
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <!-- Function used for hiding template save form -->
    <script src="../js/showTemplateForm.js"></script>
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
                    <li><a id="selected-page-item" href="${location.reload(true)}">New payment</a></li>
                    <li><a href="history">Payment history</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li id="register" style="display: none"><a href="/register" onclick="loginFunction()"><span class="glyphicon glyphicon-user"></span> Register</a></li>
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

    <div class="container-fluid">
        <div class="container form-max-width">
            <h1 class="form-h1-paddings">New payment</h1>
            <form action="pay" method="post" class="form-horizontal form-border box-border">
                <div class="container-fluid disable-padding">
                    <div class="col-xs-6 col-sm-6 col-md-6">
                        <div class="form-group">
                            <label class="col-xs-12 col-sm-6 col-md-6 control-label" for="vs">Saved templates</label>
                            <div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
                                <input class="form-control" type="text" list="selecttemplates" id="selecttemplate" name="selecttemplate"/>
                                <datalist id="selecttemplates">
                                    <option>template1</option>
                                    <option>template2</option>
                                    <option>template3</option>
                                </datalist>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-6 col-md-6">
                        <div class="form-group">
                            <div class="col-xs-12 col-sm-9 col-md-9 disable-padding">
                                <button class="btn btn-default" type="submit" value="loadtemplate">Load template</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <form action="pay" method="post" class="form-horizontal form-border box-border">
                <div class="container-fluid disable-padding">
                    <div class="col-xs-6 col-sm-6 col-md-6">
                        <div class="form-group">
                            <label class="col-xs-12 col-sm-6 col-md-6 control-label" for="sendto">*Send to</label>
                            <div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
                                <input class="form-control" type="text" id="sendto" name="sendto"  value="<c:out value="${requestScope.sendto}"/>"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6 col-sm-5 col-md-4">
                        <div class="form-group">
                            <label class="col-xs-12 col-sm-6 col-md-6 control-label" for="bankcode">*Bank code</label>
                            <div class="col-xs-5 col-sm-4 col-md-4 disable-padding">
                                <input class="form-control" type="text" list="bankcodes" id="bankcode" name="bankcode"/>
                                <datalist id="bankcodes">
                                    <option>3030</option>
                                    <option>1020</option>
                                    <option>1234</option>
                                </datalist>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6 col-sm-6 col-md-6">
                        <div class="form-group">
                            <label class="col-xs-12 col-sm-6 col-md-6 control-label" for="vs">Variable symbol</label>
                            <div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
                                <input class="form-control" type="text" id="vs" name="vs" value="<c:out value="${requestScope.vs}"/>"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-12 col-sm-6 col-md-6 control-label" for="cs">Constant symbol</label>
                            <div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
                                <input class="form-control" type="text" id="cs" name="cs" value="<c:out value="${requestScope.cs}"/>"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-12 col-sm-6 col-md-6 control-label" for="ss">Specific symbol</label>
                            <div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
                                <input class="form-control" type="text" id="ss" name="ss" value="<c:out value="${requestScope.ss}"/>"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-12 message-payment">
                        <div class="form-group">
                            <label class="col-xs-12 col-sm-3 col-md-3 control-label" for="msgrec">Recipient message</label>
                            <div class="col-xs-12 col-sm-9 col-md-9 disable-padding">
                                <input class="form-control" type="password" id="msgrec" name="msgrec" value="<c:out value="${requestScope.msgrec}"/>"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-12 col-sm-3 col-md-3 control-label" for="msgme">My message</label>
                            <div class="col-xs-12 col-sm-9 col-md-9 disable-padding">
                                <input class="form-control" type="text" id="msgme" name="msgme" value="<c:out value="${requestScope.msgme}"/>"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6 col-sm-6 col-md-6">
                        <div class="form-group">
                            <label class="col-xs-12 col-sm-6 col-md-6 control-label" for="amount">*Amount</label>
                            <div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
                                <input class="form-control" type="number" id="amount" name="amount" value="<c:out value="${requestScope.amount}"/>"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-12 col-sm-6 col-md-6 control-label" for="transactiondate">*Valid on</label>
                            <div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
                                <input class="form-control" type="date" id="transactiondate" name="transactiondate" value="<c:out value="${requestScope.transactiondate}"/>"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container-fluid disable-padding">
                    <div class="form-group">
                        <div class="col-xs-12 col-xs-offset-1 col-sm-offset-3 col-sm-9 col-md-offset-3 col-md-9 disable-padding">
                            <label><input type="checkbox" id="istemplate" name="istemplate" onclick="showTplForm()"/> Save as a template</label>
                        </div>
                    </div>
                    <div class="form-group" id="showTemplateForm" style="display: none">
                        <div class="col-xs-6 col-sm-6 col-md-6">
                            <label class="col-xs-12 col-sm-6 col-md-6 control-label" for="template">*Template name</label>
                            <div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
                                <input class="form-control" type="text" id="template" name="template" value="<c:out value="${requestScope.template}"/>"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6 col-sm-6 col-md-6">
                        <div class="form-group">
                            <div class="col-xs-12 col-sm-offset-3 col-sm-9 col-md-offset-3 col-md-9 disable-padding">
                                <button class="btn btn-default" type="submit" value="pay">Make a payment</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <jsp:include page="../generic/footer.jsp"/>
</body>
</html>
