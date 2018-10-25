<!DOCTYPE html>
<!-- 
Description: Login form page.

Author: Michal Drda

Version:	ver  / DD-MM-CCYY / comment
			0.01 / 28-10-2017 / initial version
			0.02 / 25-10-2018 / refactoring to 2018 project (Internet banking)

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
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
					<a class="navbar-brand" href="main_page.jsp">CoolBank</a>
				</div>
				<div class="collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav">
						<li><a href="about">About</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="register"><span class="glyphicon glyphicon-user"></span> Register</a></li>
					</ul>
				</div>
			</div>
		</nav>
		<div class="container-fluid">
			<form action="login" class="form-horizontal">
				<h1 class="col-sm-offset-4">Login to CoolBank now!</h1>
				<div class="container-fluid col-xs-12 col-sm-6 col-md-4 col-sm-offset-4 col-md-offset-4 box-border login-max-width">
					<div class="container-fluid">
						<div class="form-group">
							<label class="" for="username">Username</label>
							<input class="form-control" type="text" id="username" name="username"/>
						</div>
						<div class="form-group">
							<label for="password">Password</label>
							<div class="">
								<input class="form-control" type="password" id="password" name="password"/>
							</div>
						</div>
					</div>
					<div class="form-group col-xs-12 ">				
						<button class="btn btn-default" type="submit" value="login">Login</button>
					</div>
				</div>
			</form>		
		</div>
	</body>
</html>