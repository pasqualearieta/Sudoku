<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<jsp:include page="include.jsp"></jsp:include>
<link href="resources/css/waiting_style.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/waiting.js"></script>

<title>Wait for opponent</title>
</head>
<body>
	<div class="container-fluid" id="waiting">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="panel panel-default">
					<div class="panel-heading"></div>
					<div class="panel-body">
						<h3>Waiting for other player</h3>
						<p id="message"></p>
						<div id="preloader">
							<div id="loader"></div>
						</div>
					</div>
					<div class="panel-footer">
						<form action="leaveRoom" method="post">

							<input type="hidden" name="idRoom" value="${idRoom}" />
							<div class="form-group">
								<div class="row">
									<div class="col-xs-3 col-xs-offset-9">
										<button type="submit" href="#" name="leave-room"
											id="create-room" class="form-control btn btn-danger">
											Leave Match</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>