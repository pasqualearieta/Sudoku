<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
					<h1>
						You Lose <i class="fa fa-frown-o fa-2x" aria-hidden="true"></i>
					</h1>
					<h1>
						You Win <i class="fa fa-smile-o fa-2x" aria-hidden="true"></i>
					</h1>
					<hr>
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="pull-right">
								<div class="btn-group">
									<button type="button" class="btn btn-success btn-filter"
										data-target="pagado">Pagado</button>
									<button type="button" class="btn btn-warning btn-filter"
										data-target="pendiente">Pendiente</button>
									<button type="button" class="btn btn-danger btn-filter"
										data-target="cancelado">Cancelado</button>
									<button type="button" class="btn btn-default btn-filter"
										data-target="all">Todos</button>
								</div>
							</div>


							<div class="table-container">
								<table id="reviewTable" class="table table-filter">
									<col width="80">
									<col width="100">
									<col width="120">
									<col width="100">
									<thead>
										<th colspan=2>User <i class="fa fa-user-o"
											aria-hidden="true"></i></th>

										<th>Time <i class="fa fa-clock-o" aria-hidden="true"></i></th>
										<th>Status</th>

									</thead>
									<tbody id="reviewbody">
										<tr data-status="pagado">
											<td><a> <i class="fa fa-user-circle-o fa-2x"
													aria-hidden="true"></i>
											</a></td>
											<td>
												<h5 class="media-meta">Febrero 13, 2016</h5>
											</td>
											<td>
												<h5 class="title">Lorem Impsum</h5>
											</td>
											<td><span class="pagado">(Pagado)</span></td>
										</tr>
										
										<tr data-status="pendiente">
											<td><a href="#"> <img
													src="https://s3.amazonaws.com/uifaces/faces/twitter/fffabs/128.jpg"
													class="media-photo">
											</a></td>
											<td>
												<h5 class="media-meta">Febrero 13, 2016</h5>
											</td>
											<td>
												<h5 class="title">Lorem Impsum</h5>
											</td>
											<td><span class="pendiente">(pendiente)</span></td>

										</tr>
										<tr data-status="cancelado">
											<td><a href="#"> <img
													src="https://s3.amazonaws.com/uifaces/faces/twitter/fffabs/128.jpg"
													class="media-photo">
											</a></td>
											<td>
												<h5 class="media-meta">Febrero 13, 2016</h5>
											</td>
											<td>
												<h5 class="title">Lorem Impsum</h5>
											</td>
											<td><span class="cancelado">(Cancelado)</span></td>
										</tr>

									</tbody>
								</table>
							</div>
						</div>
					</div>

				</div>
			</section>

		</div>
	</div>

</body>
</html>