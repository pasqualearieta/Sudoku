<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Match review</title>
<meta charset="utf-8">

<jsp:include page="include.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="resources/css/review.css" />
<script type="text/javascript" src="resources/js/review.js"></script>

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-2">
				<h1>
					<strong>Game Review</strong>
				</h1>
			</div>
		</div>

		<hr>

		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div id="allOut" class="rgu-al alert alert-danger" role="alert"
					hidden="true">
					<button type="button" id="btn-rgu" class="btn close"
						aria-hidden="true">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button>
					<strong> <span class="glyphicon glyphicon-remove"
						aria-hidden="true"></span> ERROR!
					</strong>
					<p>
						Pay Attention, all the <strong>User</strong> was disconnected!. <br>
						You can check your result in the History
					</p>
				</div>
			</div>
		</div>

		<div class="row">
			<section class="content">
				<div class="col-md-8 col-md-offset-2">
					<hr>
					<div id="lose" hidden="true">
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

					<div id="win" hidden="true">
						<h1>
							<c:forEach var="i" begin="1" end="3">
								<i style="color: green;" class="fa fa-smile-o fa-${i }x"
									aria-hidden="true"></i>
							</c:forEach>

							<strong> You Win </strong>
							<c:forEach var="i" begin="1" end="3" step="1">
								<i style="color: green;" class="fa fa-smile-o fa-${4-i }x"
									aria-hidden="true"></i>
							</c:forEach>
						</h1>
					</div>

					<hr>

					<div id="paneReviews" class="panel panel-default">
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
										<tr>
											<th colspan=2>Player <i class="fa fa-user-o"
												aria-hidden="true"></i></th>

											<th>Time <i class="fa fa-clock-o" aria-hidden="true"></i></th>
											<th>Status</th>
										</tr>
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
			<div class="col-md-2 col-md-offset-2">
				<button type="button" id="exit" class="btn btn-primary">Exit</button>
			</div>
		</div>

	</div>
	
</body>
</html>