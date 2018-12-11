<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Registration</title>
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
						<li><a href="/managing">Managing</a></li>
						<li><a id="selected-page-item" href="${location.reload(true)}" class="selected">Register</a></li>
						<li><a href="manage_user">User management</a></li>
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

		<div class="container-fluid">
			<div class="container form-max-width">
				<h1 class="form-h1-paddings">Register a new User</h1>
				<form action="register" method="post" class="form-horizontal form-border box-border">
					<div class="container-fluid disable-padding">
						<div class="col-xs-6 col-sm-6 col-md-6">
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="firstname">*First name</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<input class="form-control" type="text" id="firstname" name="firstname"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="lastname">*Last name</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<input class="form-control" type="text" id="lastname" name="lastname"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="email">*E-mail</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<input class="form-control" type="text" id="email" name="email"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="password">*Security PIN</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<input class="form-control" type="password" id="password" name="password"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="confirmPwd">*Confirm PIN</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<input class="form-control" type="password" id="confirmPwd" name="confirmPwd"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="role">*Role</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<select class="form-control" id="role" name="role">
										<option value="USER">User</option>
										<option value="ADMIN">Admin</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-xs-6 col-sm-6 col-md-6">
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="address">Address</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<input class="form-control" type="text" id="address" name="address"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="address">City</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<input class="form-control" type="text" id="city" name="city"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="zip">Zip Code</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<input class="form-control" type="text" id="zip" name="zip"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="birthId">*Birth id</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<input class="form-control" type="number" id="birthId" name="birthId"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="gender">*Gender</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<label class="radio-inline"><input type="radio" name="gender" id="gender" value="male"> Male</label>
									<label class="radio-inline"><input type="radio" name="gender" value="female"> Female</label>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="captcha">*Six times seven</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<input class="form-control" type="text" id="captcha" name="captcha"/>
								</div>
							</div>
						</div>
					</div>
					<div class="container-fluid">
						<div class="form-group">
							<p class="col-sm-offset-3 col-md-offset-3">Please note, that it is mandatory to answer all columns with * sign.<br>Card & Account numbers and login code will be automatically generated by the system.</p>
						</div>
						<div class="form-group">
							<jsp:include page="../generic/terms.jsp"/>
						</div>
						<div class="form-group">
							<div class="col-xs-12 col-sm-offset-3 col-sm-9 col-md-offset-3 col-md-9 disable-padding">
								<label><input type="checkbox" id="terms" name="terms"/> *Agree with terms and conditions</label>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12 col-sm-offset-3 col-sm-9 col-md-offset-3 col-md-9 disable-padding">
								<button class="btn btn-default" type="submit" value="register">Register</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>

		<jsp:include page="../generic/footer.jsp"/>
	</body>
</html>