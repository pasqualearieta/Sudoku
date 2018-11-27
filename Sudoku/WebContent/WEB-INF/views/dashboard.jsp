<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        <h1 class="page-header animated bounce" align="center">Performances</h1>
      </div>
    </div>

      <div class="col-xs-12 animated pulse">
        <div class="panel panel-success">
          <div class="panel-heading font_settings" id="winning-panel">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-smile-o fa-4x" aria-hidden="true"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div>Win</div>
                <div class="huge">Ratio of won matches</div> 
              </div>
            </div>
          </div>
        </div>
      </div>

<div class="parent">
	<div class="left">
	      <div class="col-xs-12 " id="left-pie">
	        <div class="panel panel-default">
	          <div class="panel-body easypiechart-panel">
	            <h4>EASY</h4>
	            <div class="easypiechart" id="easypiechart-ew" data-percent="${easyWinRatio}">
	            	<span class="percent">${easyWinRatio}</span>
	            </div>
	          </div>
	        </div>
	      </div>
	 </div>
      <div class="center">
	      <div class="col-xs-12" id="left-pie">
	        <div class="panel panel-default">
	          <div class="panel-body easypiechart-panel">
	            <h4>MEDIUM</h4>
	            <div class="easypiechart" id="easypiechart-mw" data-percent="${mediumWinRatio}">
	            	<span class="percent">${mediumWinRatio}</span>
	            </div>
	          </div>
	        </div>
	     </div>
      </div>
      <div class="right">
	      <div class="col-xs-12 " id="left-pie">
	        <div class="panel panel-default">
	          <div class="panel-body easypiechart-panel">
	            <h4>HARD</h4>
	            <div class="easypiechart" id="easypiechart-hw" data-percent="${hardWinRatio}">
	            	<span class="percent">${hardWinRatio}</span>
	            </div>
	          </div>
	        </div>
	      </div>
	  </div>
	</div>
      
      
      
      
     <div class="col-xs-12 animated pulse">
       <div class="panel panel-warning">
         <div class="panel-heading font_settings" id="drawing-panel">
           <div class="row">
             <div class="col-xs-3">
               <i class="fa fa-meh-o fa-4x" aria-hidden="true"></i>
             </div>
             <div class="col-xs-9 text-right">
               <div>Draw</div>
               <div class="huge">Ratio of drawn matches</div> 
             </div>
           </div>
         </div>
       </div>
     </div>
     
<div class="parent">
	<div class="left">
	      <div class="col-xs-12 " id="left-pie">
	        <div class="panel panel-default">
	          <div class="panel-body easypiechart-panel">
	            <h4>EASY</h4>
	            <div class="easypiechart" id="easypiechart-ed" data-percent="${easyDrawRatio}">
	            	<span class="percent">${easyDrawRatio}</span>
	            </div>
	          </div>
	        </div>
	      </div>
	 </div>
      <div class="center">
	      <div class="col-xs-12 " id="left-pie">
	        <div class="panel panel-default">
	          <div class="panel-body easypiechart-panel">
	            <h4>MEDIUM</h4>
	            <div class="easypiechart" id="easypiechart-md" data-percent="${mediumDrawRatio}">
	            	<span class="percent">${mediumDrawRatio}</span>
	            </div>
	          </div>
	        </div>
	     </div>
      </div>
      <div class="right">
	      <div class="col-xs-12 " id="left-pie">
	        <div class="panel panel-default">
	          <div class="panel-body easypiechart-panel">
	            <h4>HARD</h4>
	            <div class="easypiechart" id="easypiechart-hd" data-percent="${hardDrawRatio}">
	            	<span class="percent">${hardDrawRatio}</span>
	            </div>
	          </div>
	        </div>
	      </div>
	  </div>
	</div>
    
      
<div class="col-xs-12 animated pulse">
  <div class="panel panel-danger">
    <div class="panel-heading font_settings" id="losing-panel">
      <div class="row">
        <div class="col-xs-3">
          <i class="fa fa-frown-o fa-4x" aria-hidden="true"></i>
        </div>
        <div class="col-xs-9 text-right">
          <div>Lose</div>
          <div class="huge">Ratio of lost matches</div> 
        </div>
      </div>
    </div>
  </div>
</div>
      
<div class="parent">
	<div class="left">
	      <div class="col-xs-12 " id="left-pie">
	        <div class="panel panel-default">
	          <div class="panel-body easypiechart-panel">
	            <h4>EASY</h4>
	            <div class="easypiechart" id="easypiechart-el" data-percent="${easyLoseRatio}">
	            	<span class="percent">${easyLoseRatio}</span>
	            </div>
	          </div>
	        </div>
	      </div>
	 </div>
      <div class="center">
	      <div class="col-xs-12 " id="left-pie">
	        <div class="panel panel-default">
	          <div class="panel-body easypiechart-panel">
	            <h4>MEDIUM</h4>
	            <div class="easypiechart" id="easypiechart-ml" data-percent="${mediumLoseRatio}">
	            	<span class="percent">${mediumLoseRatio}</span>
	            </div>
	          </div>
	        </div>
	     </div>
      </div>
      <div class="right">
	      <div class="col-xs-12 " id="left-pie">
	        <div class="panel panel-default">
	          <div class="panel-body easypiechart-panel">
	            <h4>HARD</h4>
	            <div class="easypiechart" id="easypiechart-hl" data-percent="${hardLoseRatio}">
	            	<span class="percent">${hardLoseRatio}</span>
	            </div>
	          </div>
	        </div>
	      </div>
	  </div>
	</div>
    
    <hr>


  <div class="row">
    <div class="col-md-12">
      <h3 class="animated bounce">Avg resolution time <i class="fa fa-clock-o fa-2x" aria-hidden="true"></i></h3>
    </div>
  </div>

<div class="parent">
  <div class="row">
  	<div class="left">
	    <div class="col-xs-12  animated pulse" id="left-pie">
	      <div class="panel panel-success">
	        <div class="panel-heading font_settings" id="average-easy-panel">
	          <div class="row">
	            <div class="col-xs-3">
	              <i class="fa fa-hourglass-start fa-2x" aria-hidden="true"></i>
	            </div>
	            <div class="col-xs-9 text-right">
	              <div>Easy</div>
	              <div class="huge">${easyAverageDuration}</div>
	            </div>
	          </div>
	        </div>
	      </div>
	    </div>
	 </div>
  	<div class="center">
	    <div class="col-xs-12  col-md-offset-1  animated pulse">
	      <div class="panel panel-warning">
	        <div class="panel-heading font_settings" id="average-medium-panel">
	          <div class="row">
	            <div class="col-xs-3">
	              <i class="fa fa-hourglass-half fa-2x" aria-hidden="true"></i>
	            </div>
	            <div class="col-xs-9 text-right">
	              <div>Medium</div>
	              <div class="huge">${mediumAverageDuration}</div>
	            </div>
	          </div>
	        </div>
	      </div>
	    </div>
	 </div>
  	<div class="right">

	    <div class="col-xs-12  col-md-offset-1  animated pulse">
	      <div class="panel panel-danger">
	        <div class="panel-heading font_settings" id="average-hard-panel">
	          <div class="row">
	            <div class="col-xs-3">
	              <i class="fa fa-hourglass-end fa-2x" aria-hidden="true"></i>
	            </div>
	            <div class="col-xs-9 text-right">
	              <div>Hard</div>
	              <div class="huge">${hardAverageDuration}</div>
	            </div>
	          </div>
	        </div>
	      </div>
	    </div>
    </div>
	</div>
	</div>


  <hr>


  <div class="row">
    <div class="col-md-12">
      <h3 class="animated bounce">History Details <i class="fa fa-list-alt fa-2x" aria-hidden="true"></i></h3>
    </div>
  </div>


  <div class="container">
    <div class="row">
      <div class="col-md-12 col-xs-12">
        <div class="table-responsive">
          <table id="mytable" class="table table-bordred table-striped" border="1">
            <thead>
              <th>Match ID</th>
              <th>Player</th>
              <th>Day</th>
              <th>Elapsed Time</th>
            </thead>
            <tbody>
            <c:forEach items="${previousMatches}" var="match"> 
            	<c:forEach items="${match.durations}" var="durationEntry"> 
				  <tr>
				    <td>${match.starting_date}</td>
				    <td>${match.id}</td>
				    <td>$<c:out value="${durationEntry.key.username}"/></td>
				    <td>$<c:out value="${durationEntry.value}"/></td>
				  </tr>
				</c:forEach>
				<tr>
				    <td colspan="4"></td>
			     </tr>
			</c:forEach>
            </tbody>
          </table>

          <div class="clearfix"></div>
          <!--  <ul class="pagination pull-right">
            <li class="disabled"><a href="#"><span class="glyphicon glyphicon-chevron-left"></span></a></li>
            <li class="active"><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span></a></li>
          </ul>-->
        </div>
      </div>
    </div>
  </div>
</div>
</body>

</html>
