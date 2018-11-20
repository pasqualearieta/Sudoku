<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">

<head>
  <meta charset="utf-8">
  <jsp:include page="include.jsp"></jsp:include>
  <link href="resources/css/sudoku_style.css" rel="stylesheet">
  <script src="resources/js/sudoku_GUI_logic.js"></script>

</head>

<body>
  <div class="container-fluid">
    <h3>Remaining Time: </h3>
    <div class="row">
      <div class="col-md-8">
      </div>
      <div class="col-md-2">
        <button type="submit" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">Quit Game</button>
        <div class="dropdown-menu dropdown-lr animated flipInX" role="menu">
          <div class="col-lg-12">
            <div class="text-center">
              <h3><b>Are you sure you want to quit the game?</b></h3>
            </div>
            <hr>
            <form action="register" method="post" role="form" autocomplete="off">
              <div class="row">
                <div class="col-md-offset-3 col-md-2 col-xs-3 col-xs-offset-3">
                  <button type="submit" class="btn btn-danger" name="exit_game">Yes</button>
                </div>
                <div class="col-md-offset-2 col-md-2 col-xs-3 col-xs-offset-1">
                  <button type="submit" class="btn btn-success" name="stay">No</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <hr>
    <div class="row">
      <div id="sudoku"></div>
    </div>
    <hr>
    <hspace>
      <h3>Opponent Status</h3>
      <div class="row">
        <div class="col-md-2 col-xs-1">
        </div>
        <div class="col-md-7 col-xs-10">
          <div class="progress">
            <div class="progress-bar progress-bar-striped" role="progressbar" style="width: 75%" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100">75%</div>
          </div>
        </div>
        <div class="col-md-2 col-xs-1">
        </div>
      </div>
  </div>
</body>

</html>
