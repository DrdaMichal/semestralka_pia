<!DOCTYPE html>
<!-- 
Description: About page, used for an info About project.

Author: Michal Drda

Version:	ver  / DD-MM-CCYY / comment
			0.01 / 28-10-2017 / initial version
			0.02 / 25-10-2018 / refactoring to 2018 project (Internet Banking)
 
TODO:		1)	Improve text
TODO:		2)	Create main_page.jsp
 
 -->
 
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>About CoolBank</title>
		
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
					<ul class="nav navbar-nav underline-menu">
						<li><a href="about">About</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li id="login"><a href="login" onclick="loginFunction()"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
						<li id="logout" style="display: none"><a href="" onclick="loginFunction()"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
					</ul>
				</div>
			</div>
		</nav>

		<div class="container-fluid">
			<div class="container about">
				<h1>About the CoolBank ...</h1>
				<p>CoolBank is a web application that has been created during winter semester of university year 2018/2019. It has to be some kind of website like internet banking, so students will learn how to work with various technologies used for developing web applications.</p>
				<h1>You would like an account too?</h1>
				<p>For account creation, please contact administrator on <a href="mailto:drdam@students.zcu.cz">drdam@students.zcu.cz</a></p>
			</div>
		</div>

		<footer id="footer">
			<div class="container-fluid">
				<p class="p-bigger-white">Created by Michal Drda in 2018.</p>
			</div>
		</footer>
	</body>
</html>