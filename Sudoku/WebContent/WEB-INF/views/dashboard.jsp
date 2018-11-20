<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
  <title>History</title>
  <jsp:include page="navbar.jsp"></jsp:include>
  
  <link href="resources/css/dashboard_styles.css" rel="stylesheet">
  <script src="resources/js/easypiechart.js"></script>
  <script src="resources/js/easypiechart-data.js"></script>

</head>

<body>
  <div class="container-fluid">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header animated bounce">History <i class="fa fa-book fa-2x" aria-hidden="true"></i></h1>
      </div>
    </div>

    <div class="row">
      <div class="col-xs-12 col-md-3 animated pulse" id="left-pie">
        <div class="panel panel-success">
          <div class="panel-heading font_settings" id="winning-panel">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-smile-o fa-4x" aria-hidden="true"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div>Winning Match</div>
                <div class="huge">#winning</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="col-xs-12 col-md-3 col-md-offset-1  animated pulse">
        <div class="panel panel-warning">
          <div class="panel-heading font_settings" id="drawing-panel">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-meh-o fa-4x" aria-hidden="true"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div>Drawing Match</div>
                <div class="huge">#Drawing</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="col-xs-12 col-md-3 col-md-offset-1  animated pulse">
        <div class="panel panel-danger">
          <div class="panel-heading font_settings" id="losing-panel">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-frown-o fa-4x" aria-hidden="true"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div>Losed Match</div>
                <div class="huge">#Losed</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <hr>

    <div class="row">
      <div class="col-xs-12 col-md-3" id="left-pie">
        <div class="panel panel-default">
          <div class="panel-body easypiechart-panel">
            <h4>Winned</h4>
            <div class="easypiechart" id="easypiechart-teal" data-percent="56"><span class="percent">56%</span></div>
          </div>
        </div>
      </div>
      <div class="col-xs-12 col-md-3 col-md-offset-1">
        <div class="panel panel-default">
          <div class="panel-body easypiechart-panel">
            <h4>Drawed</h4>
            <div class="easypiechart" id="easypiechart-orange" data-percent="65"><span class="percent">65%</span></div>
          </div>
        </div>
      </div>
      <div class="col-xs-12 col-md-3 col-md-offset-1 ">
        <div class="panel panel-default">
          <div class="panel-body easypiechart-panel">
            <h4>Losed</h4>
            <div class="easypiechart" id="easypiechart-red" data-percent="27"><span class="percent">27%</span></div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <hr>


  <div class="row">
    <div class="col-md-12">
      <h3 class="animated bounce">Average Time of resolution by Difficulty <i class="fa fa-clock-o fa-2x" aria-hidden="true"></i></h3>
    </div>
  </div>

  <div class="row">
    <div class="col-xs-12 col-md-3 animated pulse" id="left-pie">
      <div class="panel panel-success">
        <div class="panel-heading font_settings" id="average-easy-panel">
          <div class="row">
            <div class="col-xs-3">
              <i class="fa fa-hourglass-start fa-2x" aria-hidden="true"></i>
            </div>
            <div class="col-xs-9 text-right">
              <div>Easy</div>
              <div class="huge">#winning</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="col-xs-12 col-md-3 col-md-offset-1  animated pulse">
      <div class="panel panel-warning">
        <div class="panel-heading font_settings" id="average-medium-panel">
          <div class="row">
            <div class="col-xs-3">
              <i class="fa fa-hourglass-half fa-2x" aria-hidden="true"></i>
            </div>
            <div class="col-xs-9 text-right">
              <div>Medium</div>
              <div class="huge">#Drawing</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="col-xs-12 col-md-3 col-md-offset-1  animated pulse">
      <div class="panel panel-danger">
        <div class="panel-heading font_settings" id="average-hard-panel">
          <div class="row">
            <div class="col-xs-3">
              <i class="fa fa-hourglass-end fa-2x" aria-hidden="true"></i>
            </div>
            <div class="col-xs-9 text-right">
              <div>Hard</div>
              <div class="huge">#Losed</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>


  <hr>


  <div class="row">
    <div class="col-md-12">
      <h3 class="animated bounce">Games Detail <i class="fa fa-list-alt fa-2x" aria-hidden="true"></i></h3>
    </div>
  </div>


  <div class="container">
    <div class="row">
      <div class="col-md-12 col-xs-12">
        <div class="table-responsive">
          <table id="mytable" class="table table-bordred table-striped" border="1">
            <thead>
              <th>Player</th>
              <th>Day</th>
              <th>Elapsed Time</th>
              <th>Elapsed Time Opposite</th>
              <th>Status</th>
            </thead>
            <tbody>
              <tr>
                <td>REPLACE</td>
                <td>REPLACE</td>
                <td>REPLACE</td>
                <td>REPLACE</td>
                <td>REPLACE</td>
              </tr>
            </tbody>
          </table>

          <div class="clearfix"></div>
          <ul class="pagination pull-right">
            <li class="disabled"><a href="#"><span class="glyphicon glyphicon-chevron-left"></span></a></li>
            <li class="active"><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span></a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>

</body>

</html>
