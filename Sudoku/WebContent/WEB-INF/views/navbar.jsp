<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<!-- This file has been downloaded from Bootsnipp.com. Enjoy! -->
<title>Sudo -ku turtles</title>
<jsp:include page="include.jsp"></jsp:include>
<link href="resources/css/navbar_style.css" rel="stylesheet">
<script src="resources/js/navbar.js"></script>

</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<!--LOGO SETTINGS-->
				<a href="" class="navbar-brand" id="logo"><img class="img-fluid"
					alt="Responsive image" src="resources/img/logoNinjaTurtles.png"
					style="width: 320px; margin-left: 5px; margin-bottom: 20px; margin-top: -15px;"></a>
			</div>

			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<!--USER DASHBOARD-->
					<c:if test="${not empty username }">
						<li class="dropdown"><a href="" class="dropdown-toggle"
							data-toggle="dropdown"><span class="glyphicon glyphicon-user"
								aria-hidden="true"></span> Welcome, ${username } <b
								class="caret"></b></a>
							<ul class="dropdown-menu dropdown-md animated slideInDown">
								<c:if test="${not empty dashboard }">
									<form action="dashboard">
										<div class="text-center">
											<button class="wrap_button" type="submit"
												name="dashboard_btn">
												<i class="fa fa-history" aria-hidden="true"></i> History
											</button>
										</div>
									</form>

								</c:if>
								<c:if test="${not empty dashboard }">
									<li class="divider"></li>
								</c:if>
								<c:if test="${not empty viewLobby }">
									<form action="lobby">
										<div class="text-center">
											<button class="wrap_button" type="submit" name="lobby_btn"
												id="go_to_lobby">
												<i class="fa fa-handshake-o" aria-hidden="true"></i>Lobby
											</button>
										</div>
									</form>
								</c:if>

								<c:if test="${not empty viewLobby }">
									<li class="divider"></li>
								</c:if>
								<li>

									<form action="logout">
										<div class="text-center">
											<button class="wrap_button" type="submit" name="logout_btn">
												<i class="icon-off"></i> <span
													class="glyphicon glyphicon-off" aria-hidden="true"></span>
												Logout
											</button>
										</div>
									</form>
								</li>
							</ul></li>
					</c:if>


					<c:if test="${empty username }">
						<!--REGISTRATION-->
						<li class="dropdown"><a href="" class="dropdown-toggle"
							data-toggle="dropdown">Register <span class="caret"></span></a>
							<ul class="dropdown-menu dropdown-lr animated flipInX"
								role="menu">
								<div class="col-lg-12">
									<div class="text-center">
										<h3>
											<b>Register</b>
										</h3>
									</div>

									<form action="register" method="post" role="form"
										autocomplete="off" id="registration_form">
										<div class="form-group">
											<input type="text" name="username_register"
												id="username_register" tabindex="1" class="form-control"
												placeholder="Username" required />
										</div>

										<div class="rgu-al alert alert-danger" role="alert"
											hidden="true">
											<button type="button" id="btn-rgu" class="btn close"
												aria-hidden="true">
												<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
											</button>
											<strong> <span class="glyphicon glyphicon-remove"
												aria-hidden="true"></span> Warning!
											</strong>
											<p>The Username is already in use!</p>
										</div>

										<div class="form-group">
											<input type="password" name="password_register"
												id="password_register" tabindex="2" class="form-control"
												placeholder="Password" required />
										</div>
										<div class="form-group">
											<input type="password" name="confirm-password_register"
												id="confirm-password_register" tabindex="2"
												class="form-control" placeholder="Confirm Password" required />
										</div>

										<div class="rgp-al alert alert-danger" role="alert"
											hidden="true">
											<button type="button" id="btn-rgp" class="btn close"
												aria-hidden="true">
												<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
											</button>
											<strong> <span class="glyphicon glyphicon-remove"
												aria-hidden="true"></span> Pay Attention!
											</strong>
											<p>The Password must be equal!</p>
										</div>

										<div class="form-group">
											<div class="row">
												<div class="col-xs-6 col-xs-offset-3">
													<input type="submit" name="register-submit"
														id="register-submit" tabindex="4"
														class="form-control btn btn-info" value="Register Now">
												</div>
											</div>
										</div>

										<div class="rg-ok alert alert-success" role="alert"
											hidden="true">
											<strong> <span class="glyphicon glyphicon-check"
												aria-hidden="true"></span> Well Done!
											</strong>
											<p>You successfully registered!</p>
										</div>

									</form>

								</div>
							</ul></li>
					</c:if>

					<c:if test="${empty username }">
						<!--LOGIN-->
						<li class="dropdown"><a href="" class="dropdown-toggle"
							data-toggle="dropdown"> <span
								class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
								Log In <span class="caret"></span></a>
							<ul
								class="dropdown-menu drop-log-in dropdown-lr animated slideInRight"
								class="caret" role="menu">
								<div class="col-lg-12 menu">
									<div class="text-center">
										<h3>
											<b>Log In</b>
										</h3>
									</div>
									<form action="login" method="post" role="form"
										autocomplete="off" id="login_form">
										<div class="form-group">
											<label for="username">Username</label> <input type="text"
												name="username_log_in" id="username_log_in" tabindex="1"
												class="form-control" placeholder="Username"
												autocomplete="off">
										</div>
										<div class="form-group">
											<label for="password">Password</label> <input type="password"
												name="password_log_in" id="password_log_in" tabindex="2"
												class="form-control" placeholder="Password"
												autocomplete="off">
										</div>

										<div class="lg-al alert alert-danger" role="alert"
											hidden="true">
											<button type="button" id="btn-lgn" class="btn close"
												aria-hidden="true">
												<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
											</button>
											<strong> Error! </strong>
											<p id="lgin_message"></p>
										</div>

										<div class="form-group">
											<div class="row">
												<div class="col-xs-5 pull-right">
													<button type="submit" name="login-submit" id="login-submit"
														tabindex="4" class="form-control btn btn-success"
														value="Log In">
														<span class="glyphicon glyphicon-log-in"
															aria-hidden="true"></span> Log In
													</button>
												</div>
											</div>
										</div>
									</form>
								</div>
							</ul></li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
</body>
</html>
