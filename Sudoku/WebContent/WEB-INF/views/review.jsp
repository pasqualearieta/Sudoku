<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="http://cdn.phpoll.com/css/animate.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/review.css" />
<script type="text/javascript" src="resources/js/review.js"></script>


</head>
<body>
	<h1>Game Review</h1>
	<div class="container">
		<div class="row">
			<section class="content">
				<div class="col-md-8 col-md-offset-2">
					<hr>

					<div id="lose">
						<h1>
							<c:forEach var="i" begin="1" end="3">
								<i style="color: red;" class="fa fa-frown-o fa-${i }x"
									aria-hidden="true"></i>
							</c:forEach>

							<strong> You Lose </strong>
							<c:forEach var="i" begin="1" end="3" step="1">
								<i style="color: red;" class="fa fa-frown-o fa-${4-i }x"
									aria-hidden="true"></i>
							</c:forEach>
						</h1>
					</div>

					<div id="win">
						<h1>
							<c:forEach var="i" begin="1" end="3">
								<i style="color: orange;" class="fa fa-smile-o fa-${i }x"
									aria-hidden="true"></i>
							</c:forEach>

							<strong> You Win </strong>
							<c:forEach var="i" begin="1" end="3" step="1">
								<i style="color: orange;" class="fa fa-smile-o fa-${4-i }x"
									aria-hidden="true"></i>
							</c:forEach>
						</h1>
					</div>



					<hr>
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="pull-right">
								<div class="btn-group">
									<button type="button" class="btn btn-success btn-filter"
										data-target="ended">Ended</button>
									<button type="button" class="btn btn-warning btn-filter"
										data-target="gaming">Gaming</button>
									<button type="button" class="btn btn-danger btn-filter"
										data-target="disconnected">Disconnected</button>
									<button type="button" class="btn btn-default btn-filter"
										data-target="all">All</button>
								</div>
							</div>


							<div class="table-container">
								<table id="reviewTable" class="table table-filter">
									<col width="40">
									<col width="80">
									<col width="80">
									<col width="80">
									<thead>
										<th colspan=2>Player <i class="fa fa-user-o"
											aria-hidden="true"></i></th>

										<th>Time <i class="fa fa-clock-o" aria-hidden="true"></i></th>
										<th>Status</th>

									</thead>
									<tbody id="reviewbody">
									</tbody>
								</table>
							</div>
						</div>
					</div>



				</div>
			</section>

		</div>


		<div class="row">
			<div class="col-md-2 col-md-offset-4">
				<button type="button" id="exit" class="btn btn-primary">Exit</button>
			</div>
		</div>
	</div>

</body>
</html>