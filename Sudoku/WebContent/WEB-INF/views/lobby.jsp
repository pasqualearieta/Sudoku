<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">

<head>
<meta charset="utf-8">

<jsp:include page="navbar.jsp"></jsp:include>
<link href="resources/css/lobby_style.css" rel="stylesheet">
<script src="resources/js/lobby.js"></script>
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
								<input type="text" name="lobbyName" id="lobbyName" tabindex="1"
									class="form-control" placeholder="Lobby Name">
							</div>
							<div class="form-group">
								<div class="row">
									<div class="col-md-3" id="selected_diff">
										<p>Difficulty</p>
									</div>
									<div class="col-md-7">
										<div class="btn-group">
											<select id="diff_drop">
												<option>EASY</option>
												<option>MEDIUM</option>
												<option>HARD</option>
											</select> <input type="hidden" id="difficulty" name="difficulty"
												value="EASY">
										</div>
									</div>

								</div>
							</div>
							<hr>
							<div class="form-group">
								<div class="row">
									<div class="col-xs-6 col-xs-offset-3">
										<button type="submit" name="create-room" id="create-room"
											class="form-control btn btn-success">Create</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>


			<c:if test="${fn:length(available_room) gt 0}">
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
										<th class="header-divider">Name</th>
										<th class="header-divider"><em>Difficulty</em></th>
										<th class="header-divider"><em>Join</em> <i
											class="fa fa-check-square-o check" aria-hidden="true"></i></th>
									</tr>
								</thead>
								<tbody id="torep">
								</tbody>

							</table>
						</div>
						<div class="panel-footer" id="available-lobby-panel">
							<div class="row">
								<div class="col col-xs-4">
									Page ${currentPagination}
									<c:if test="${total_room_page gt 1 }"> 
								of ${total_room_page }
								</c:if>
								</div>
								<div class="col col-xs-8">
									<ul class="pagination hidden-xs pull-right">
										<li><button class="btn btn-warning" type="submit">«</button></li>
										
										<c:forEach begin="1" end="${total_room_page }"
											varStatus="loop">
											<li><button class="btn btn-warning pg-button"
													type="submit">${loop.index}</button></li>
										</c:forEach>
										
										<li><button class="btn btn-warning pg-button-arrow-right" value="${currentPagination}" type="submit">»</button></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>

			</c:if>
		</div>
	</div>
</body>
</html>
