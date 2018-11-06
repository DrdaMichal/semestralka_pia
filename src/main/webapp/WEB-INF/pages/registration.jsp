<!DOCTYPE html>
<!-- 
Description: Register form page, used for adding new user to the database.

Author: Michal Drda

Version:	ver  / DD-MM-CCYY / comment
			0.01 / 20-10-2017 / initial version
			0.02 / 31-10-2017 / form align
            0.03 / 22-10-2018 / refactoring to 2018 project (Internet Banking)
 -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Registration</title>
		
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
						<li><a href="register">Register</a></li>
						<li><a href="about">About</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li id="login"><a href="login" onclick="loginFunction()"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
						<li id="logout" style="display: none"><a href="" onclick="loginFunction()"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
					</ul>
				</div>
			</div>
		</nav>
		<div class="clear"></div>


		<div class="container-fluid">
            <c:if test="${not empty requestScope.err}">
                <div class="alert alert-danger alert-dismissible">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <p>Error: ${requestScope.err}</p>
                </div>
            </c:if>
			<div class="container form-max-width">
				<h1 class="form-h1-paddings">Register a new User</h1>
				<form action="register" method="post" class="form-horizontal form-border box-border">
					<sec:csrfInput/>
					<sec:csrfMetaTags/>
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
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="address">*Address</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<input class="form-control" type="text" id="address" name="address"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="address">*City</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<input class="form-control" type="text" id="city" name="city"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="zip">*Zip Code</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<input class="form-control" type="text" id="zip" name="zip"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-12 col-sm-6 col-md-6 control-label" for="birthId">*Birth id</label>
								<div class="col-xs-12 col-sm-6 col-md-6 disable-padding">
									<input class="form-control" type="number" id="birthId" name="birthId" maxlength="10" minlength="10"/>
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
							<label class="col-xs-12 col-sm-3 col-md-3 control-label">Terms and Conditions</label>
							<div class="col-xs-12 col-sm-9 col-md-9 terms">
								<h1 class="disable-margin-top h1-terms-smaller">Introduction</h1>
								<p>These Website Standard Terms and Conditions written on this webpage shall manage your use of our website, coolbank.com accessible at cool-bank.com.</p>
								<p>These Terms will be applied fully and affect to your use of this Website. By using this Website, you agreed to accept all terms and conditions written in here. You must not use this Website if you disagree with any of these Website Standard Terms and Conditions. These terms and conditions have been <a href="https://termsandcondiitionssample.com">generated at Terms And Conditions Sample.com</a></p>
								<p>Minors or people below 18 years old are not allowed to use this Website.</p>

								<h2 class="h2-terms-smaller">Intellectual Property Rights</h2>
								<p>Other than the content you own, under these Terms, CoolBank and/or its licensors own all the intellectual property rights and materials contained in this Website.</p>
								<p>You are granted limited license only for purposes of viewing the material contained on this Website.</p>

								<h2 class="h2-terms-smaller">Restrictions</h2>
								<p>You are specifically restricted from all of the following:</p>
								<ul>
									<li>publishing any Website material in any other media;</li>
									<li>selling, sublicensing and/or otherwise commercializing any Website material;</li>
									<li>publicly performing and/or showing any Website material;</li>
									<li>using this Website in any way that is or may be damaging to this Website;</li>
									<li>using this Website in any way that impacts user access to this Website;</li>
									<li>using this Website contrary to applicable laws and regulations, or in any way may cause harm to the Website, or to any person or business entity;</li>
									<li>engaging in any data mining, data harvesting, data extracting or any other similar activity in relation to this Website;</li>
									<li>using this Website to engage in any advertising or marketing.</li>
								</ul>
								<p>Certain areas of this Website are restricted from being access by you and CoolBank may further restrict access by you to any areas of this Website, at any time, in absolute discretion. Any user ID and password you may have for this Website are confidential and you must maintain confidentiality as well.</p>

								<h2 class="h2-terms-smaller">Your Content</h2>
								<p>In these Website Standard Terms and Conditions, "Your Content" shall mean any audio, video text, images or other material you choose to display on this Website. By displaying Your Content, you grant CoolBank a non-exclusive, worldwide irrevocable, sub licensable license to use, reproduce, adapt, publish, translate and distribute it in any and all media.</p>
								<p>Your Content must be your own and must not be invading any third-party’s rights. CoolBank reserves the right to remove any of Your Content from this Website at any time without notice.</p>

								<h2 class="h2-terms-smaller">No warranties</h2>
								<p>This Website is provided "as is," with all faults, and CoolBank express no representations or warranties, of any kind related to this Website or the materials contained on this Website. Also, nothing contained on this Website shall be interpreted as advising you.</p>

								<h2 class="h2-terms-smaller">Limitation of liability</h2>
								<p>In no event shall CoolBank, nor any of its officers, directors and employees, shall be held liable for anything arising out of or in any way connected with your use of this Website whether such liability is under contract.  CoolBank, including its officers, directors and employees shall not be held liable for any indirect, consequential or special liability arising out of or in any way related to your use of this Website.</p>

								<h2 class="h2-terms-smaller">Indemnification</h2>
								<p>You hereby indemnify to the fullest extent CoolBank from and against any and/or all liabilities, costs, demands, causes of action, damages and expenses arising in any way related to your breach of any of the provisions of these Terms.</p>

								<h2 class="h2-terms-smaller">Severability</h2>
								<p>If any provision of these Terms is found to be invalid under any applicable law, such provisions shall be deleted without affecting the remaining provisions herein.</p>

								<h2 class="h2-terms-smaller">Variation of Terms</h2>
								<p>CoolBank is permitted to revise these Terms at any time as it sees fit, and by using this Website you are expected to review these Terms on a regular basis.</p>

								<h2 class="h2-terms-smaller">Assignment</h2>
								<p>The CoolBank is allowed to assign, transfer, and subcontract its rights and/or obligations under these Terms without any notification. However, you are not allowed to assign, transfer, or subcontract any of your rights and/or obligations under these Terms.</p>

								<h2 class="h2-terms-smaller">Entire Agreement</h2>
								<p>These Terms constitute the entire agreement between CoolBank and you in relation to your use of this Website, and supersede all prior agreements and understandings.</p>

								<h2 class="h2-terms-smaller">Governing Law & Jurisdiction</h2>
								<p>These Terms will be governed by and interpreted in accordance with the laws of the State of cz, and you submit to the non-exclusive jurisdiction of the state and federal courts located in cz for the resolution of any disputes.</p>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12 col-sm-offset-3 col-sm-9 col-md-offset-3 col-md-9 disable-padding">
								<label><input type="checkbox" id="terms" name="terms"/> Agree with terms and conditions</label>
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

		<div class="clear"></div>
		<footer id="footer">
			<div class="container-fluid">
				<p class="p-bigger-white">Created by Michal Drda in 2018.</p>
			</div>
		</footer>
	</body>
</html>