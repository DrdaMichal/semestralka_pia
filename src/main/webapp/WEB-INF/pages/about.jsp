<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: Michal Drda
  Date: 05-Nov-18
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
 
<html lang="en">

	<head>

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>CoolBank - about</title>
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
		<!-- show text to contact admin on click -->
		<script src="../../js/onBtnClickShow.js"></script>

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
					<ul class="nav navbar-nav underline-menu">
						<li><a id="selected-page-item" href="${location.reload(true)}">About</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<c:if test="${not empty sessionScope.user}"><li id="logged-user"><div class="logged-user"><p>Logged as: ${sessionScope.user}</p></div></li></c:if>
						<li id="login"><a href="login" onclick="loginFunction()"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
						<li id="logout" style="display: none"><a href="logout" onclick="loginFunction()"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
					</ul>
				</div>

			</div>
		</nav>

		<jsp:include page="generic/alerts.jsp">
			<jsp:param name="err" value="${requestScope.err}"/>
			<jsp:param name="suc" value="${requestScope.suc}"/>
		</jsp:include>

		<div class="container-fluid">
			<div class="container about about-center">

				<h1 class="about-center">About the CoolBank ...</h1>
				<p >CoolBank is a web application that has been created during winter semester of university year 2018/2019. It has to be some kind of website like internet banking, so students will learn how to work with various technologies used for developing web applications.</p>
				<h1 class="about-center">You would like an account too?</h1>
				<button id="hideBtn" class="btn btn-default" onclick="hideBtn()">Yes!</button>
				<div id="showText" style="display: none">
					<p>For account creation, please contact administrator on <a href="mailto:drdam@students.zcu.cz">drdam@students.zcu.cz</a></p>
				</div>

			</div>
		</div>

		<jsp:include page="generic/footer.jsp"/>

	</body>

</html>