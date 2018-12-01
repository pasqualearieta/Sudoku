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
					
					<div class="panel-body">
						<h1>Waiting for other player</h1>
						<p id="message"></p>
						<div id="preloader">
							<div id="loader"></div>
						</div>
					</div>
					<div class="panel-footer">
						<input type="hidden" name="idRoom" id="idRoom" value="${idRoom}" />
						<div class="form-group">
							<div class="row">
								<div class="col-xs-4">
									<button type="submit" name="start-room" id="start-room"
										class="form-control btn btn-success">Start Match</button>
								</div>
								<div class="col-xs-4"></div>
								<div class="col-xs-4">
									<button type="button" name="leave-room" id="leave-room"
										class="form-control btn btn-danger leave-room">Leave Match</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- Modal -->
	<div id="resultModal" class="modal fade" data-backdrop="static" hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="modalTitle">Disconnected</h4>
				</div>
				<div class="modal-body">
					The creator leave the room
				</div>
				<div class="modal-footer">
					<button type="button" id="disconnected" class="btn btn-primary">Exit</button>
				</div>
			</div>
		</div>
	</div>
		
		
	</div>

</body>
</html>