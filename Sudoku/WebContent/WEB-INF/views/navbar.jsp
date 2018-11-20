<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <!-- This file has been downloaded from Bootsnipp.com. Enjoy! -->
  <title>Sudo -ku turtles</title>

  <jsp:include page="include.jsp"></jsp:include>
  <link href="resources/css/navbar_style.css" rel="stylesheet"> 
</head>

<body>

  <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <!--LOGO SETTINGS-->
        <a class="navbar-brand" href=""><img class="img-fluid" alt="Responsive image" src="https://image.ibb.co/dkWns0/logo.png" style="width: 320px;  margin-left: 5px; margin-bottom: 20px; margin-top:-5px;"></a>
      </div>

      <div id="navbar" class="collapse navbar-collapse">
        <ul class="nav navbar-nav navbar-right">
          <!--USER PREFERENCES-->
          <li class="dropdown">
            <a href="" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> Welcome, User <b class="caret"></b></a>
            <ul class="dropdown-menu dropdown-md animated slideInDown">
              <li><a href="" name="preferences"><i class="icon-cog"></i> <span class="glyphicon glyphicon-cog" aria-hidden="true"></span> Preferences</a></li>
              <li class="divider"></li>
              <li><a href="" name="logout"><i class="icon-off"></i> <span class="glyphicon glyphicon-off" aria-hidden="true"></span> Logout</a></li>
            </ul>
          </li>

          <!--REGISTRATION-->
          <li class="dropdown">
            <a href="" class="dropdown-toggle" data-toggle="dropdown">Register <span class="caret"></span></a>
            <ul class="dropdown-menu dropdown-lr animated flipInX" role="menu">
              <div class="col-lg-12">
                <div class="text-center">
                  <h3><b>Register</b></h3>
                </div>
                <form action="register" method="post" role="form" autocomplete="off">
                  <div class="form-group">
                    <input type="text" name="username_register" id="username_register" tabindex="1" class="form-control" placeholder="Username">
                  </div>
                  <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong> <span class="glyphicon glyphicon-remove"></span> Warning!</strong> The Username is already in use!
                  </div>
                  <div class="form-group">
                    <input type="password" name="password_register" id="password_register" tabindex="2" class="form-control" placeholder="Password">
                  </div>
                  <div class="form-group">
                    <input type="password" name="confirm-password_register" id="confirm-password_register" tabindex="2" class="form-control" placeholder="Confirm Password">
                  </div>
                  <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong> <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Pay Attention! </strong> The Password must be equal!
                  </div>
                  <div class="form-group">
                    <div class="row">
                      <div class="col-xs-6 col-xs-offset-3">
                        <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-info" value="Register Now">
                      </div>
                    </div>
                  </div>
                </form>
              </div>
            </ul>
          </li>

          <!--LOGIN-->
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown"> <span class="glyphicon glyphicon-log-in" aria-hidden="true"></span> Log In <span class="caret"></span></a>
            <ul class="dropdown-menu dropdown-lr animated slideInRight" role="menu">
              <div class="col-lg-12">
                <div class="text-center">
                  <h3><b>Log In</b></h3>
                </div>
                <form action="login" method="post" role="form" autocomplete="off">
                  <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" name="username_log_in" id="username_log_in" tabindex="1" class="form-control" placeholder="Username" autocomplete="off">
                  </div>
                  <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" name="password_log_in" id="password_log_in" tabindex="2" class="form-control" placeholder="Password" autocomplete="off">
                  </div>
                  <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong> <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Error!</strong> Username or Password not valid!
                  </div>
                  <div class="form-group">
                    <div class="row">
                      <div class="col-xs-5 pull-right">
                        <button type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-success" value="Log In"><span class="glyphicon glyphicon-log-in" aria-hidden="true"></span> Log In</button>
                      </div>
                    </div>
                  </div>
                </form>
              </div>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</body>

</html>
