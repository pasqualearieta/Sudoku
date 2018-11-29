<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">

<head>
<meta charset="utf-8">
<jsp:include page="include.jsp"></jsp:include>
<link href="resources/css/waiting_style.css" rel="stylesheet">

<link href="resources/css/sudoku_style.css" rel="stylesheet">
<script src="resources/js/sudoku_GUI_logic.js"></script>

<link rel="stylesheet" type="text/css"
	href="resources/css/loading-bar.css" />
<script type="text/javascript" src="resources/js/loading-bar.js"></script>


</head>

<body>
	<input type="hidden" id="sudokuPuzzle" value="${sudoku}">
	<input type="hidden" id="startingTime">
	<div class="container-fluid" id="sudoku_board">
		<div class="row">
			<div class="col-md-3">
				<h3 id="elapsed">
					<i class="fa fa-clock-o fa-2x" aria-hidden="true"></i> Elapsed
					Time:
				</h3>
			</div>
			<div class="col-md-3">
				<h3 id="time" style="margin: 38px;"></h3>
			</div>
		</div>



		<div class="row">
			<div class="col-md-8"></div>
			<div class="col-md-2">
				<button type="submit" class="btn btn-primary dropdown-toggle"
					data-toggle="dropdown">Quit Game</button>
				<div id="gameLeaving"
					class="dropdown-menu dropdown-lr animated flipInX" role="menu">
					<div class="col-lg-12">
						<div class="text-center">
							<h3>
								<b>Are you sure you want to quit the game?</b>
							</h3>
						</div>
						<hr>
						<form action="register" method="post" role="form"
							autocomplete="off">
							<div class="row">
								<div class="col-md-offset-3 col-md-2 col-xs-3 col-xs-offset-3">
									<button type="button" id="quitGame" class="btn btn-danger">Yes</button>
								</div>
								<div class="col-md-offset-2 col-md-2 col-xs-3 col-xs-offset-1">
									<button type="button" id="stayInTheBoard"
										class="btn btn-success">No</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<hr>
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div id="wrongSudoku" class="rgu-al alert alert-danger" role="alert"
					hidden="true">
					<button type="button" id="btn-rgu" class="btn close"
						aria-hidden="true">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button>
					<strong> <span class="glyphicon glyphicon-remove"
						aria-hidden="true"></span> ERROR!
					</strong>
					<p>Pay Attention, the <strong>Sudoku</strong> is wrong!</p>
				</div>
			</div>

		</div>

		<hr>
		<div class="row">
			<div id="sudoku" class="sudokuContainer"></div>
		</div>
		<hr>


		<div class="row" id="opponentStatus">
			<div class="row">
				<div class="col-md-offset-1">
					<h3>Opponent Status</h3>
				</div>

			</div>

			<div class="col-md-2 col-md-offset-3">
				<h2 id="status">0%</h2>
			</div>

			<div class="row" id="bar">
				<div class="ldBar" style="width: 50%; height: 10%; margin: auto"
					data-preset="line"
					data-stroke="data:ldbar/res,gradient(0,1,#9df,#9fd,#df9,#fd9)"></div>
			</div>
		</div>

	</div>

	<!-- Modal -->
	<div id="resultModal" class="modal fade" data-backdrop="static" hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="modalTitle">Game Result</h4>
				</div>
				<div class="modal-body">
					<h3 id="resultStatus"></h3>
				</div>
				<div class="modal-footer">
					<button type="button" id="exit" class="btn btn-primary">Exit</button>
					<button type="button" id="disconnected" class="btn btn-primary">Exit</button>
				</div>
			</div>
		</div>
	</div>
	<!-- /.modal -->



</body>

</html>
