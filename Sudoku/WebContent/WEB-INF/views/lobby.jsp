<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">

<head>
<meta charset="utf-8">

<jsp:include page="navbar.jsp"></jsp:include>


<link href="resources/css/lobby_style.css" rel="stylesheet">
</head>

<body>

	<p></p>
	<h3>This Header Must be Removed!!! SPACE ONLY</h3>
	<p></p>
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<div class="panel panel-default" id="panel">
					<div class="panel-heading" id="create-lobby-panel">Create
						Lobby</div>
					<div class="panel-body">
						<form action="create-lobby" method="post" role="form"
							autocomplete="off">
							<div class="form-group">
								<input type="text" name="username" id="username" tabindex="1"
									class="form-control" placeholder="Lobby Name">
							</div>
							<div class="form-group">
								<div class="row">
									<div class="col-md-7">
										<div class="btn-group">
											<button type="button" class="btn btn-default dropright"
												data-toggle="dropdown">
												Difficulty <span class="caret"></span>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li><a href="#">Easy</a></li>
												<li><a href="#">Medium</a></li>
												<li><a href="#">Hard</a></li>
											</ul>
										</div>
									</div>
									<div class="col-md-3">
										<p>Selected Diff</p>
									</div>
								</div>
							</div>
							<hr>
							<div class="form-group">
								<div class="row">
									<div class="col-xs-6 col-xs-offset-3">
										<button type="submit" href="#" name="create-room"
											id="create-room" class="form-control btn btn-success">Create
										</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-md-offset-1">
				<div class="panel panel-default panel-table" id="panel">
					<div class="panel-heading" id="available-lobby-panel">
						<div class="row">
							<div class="col col-xs-6">
								<h3 class="panel-title">
									Available Lobby <i class="fa fa-handshake-o fa-2x"
										aria-hidden="true"></i>
								</h3>
							</div>
						</div>
					</div>
					<div class="panel-body">
						<table class="table table-responsive  ">
							<thead>
								<tr>
									<th class="header-divider"><i class="fa fa-user-o"
										aria-hidden="true"></i> <em>Player</em></th>
									<th class="header-divider"><em>Difficulty</em></th>
									<th class="header-divider"><em>Join</em> <i
										class="fa fa-check-square-o check" aria-hidden="true"></i></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td align="center" class="vertical-divider">PLAYER NAME</td>
									<td class="vertical-divider">DIFFICULTY</td>
									<td align="center" class="vertical-divider"><a href="#"><i
											class="fa fa-check-square-o fa-2x check" aria-hidden="true"></i></a>
									</td>
								</tr>
								<tr>
									<td align="center" class="vertical-divider">PLAYER NAME</td>
									<td class="vertical-divider">DIFFICULTY</td>
									<td align="center" class="vertical-divider"><a href="#"><i
											class="fa fa-check-square-o fa-2x check" aria-hidden="true"></i></a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="panel-footer" id="available-lobby-panel">
						<div class="row">
							<div class="col col-xs-4">Page 1 of 5</div>
							<div class="col col-xs-8">
								<ul class="pagination hidden-xs pull-right">
									<li><a href="#">1</a></li>
								</ul>
								<ul class="pagination visible-xs pull-right">
									<li><a href="#">«</a></li>
									<li><a href="#">»</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
