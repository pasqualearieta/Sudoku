<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">

<head>
<meta charset="utf-8">
<jsp:include page="navbar.jsp"></jsp:include>
</head>
<style>
<!--
body { #hmlg { border:3pxsolid#fff;
	-moz-box-shadow: 15px 15px 5px #ccc;
	-webkit-box-shadow: 15px 15px 5px #ccc;
	box-shadow: 15px 15px 5px #ccc;
	-moz-border-radius: 25px;
	-webkit-border-radius: 25px;
	border-radius: 15px;
}
-->
</style>
<body>
	<div class="container" id="back">

		<c:if test="${empty username }">
			<div class="row">
				<div class="col-md-12" style="text-align: center;">
					<h2>Login or Register to Play</h2>
				</div>
			</div>
		</c:if>



		<div class="row" style="margin-top: 1%;">
			<div>
				<img id="hmlg" src="resources/img/home.gif"
					style="display: block; margin-left: auto; margin-right: auto"
					width="40%" height="40%" />
			</div>
		</div>

	</div>
	<hr>
	<div class="row">
		<div class="col-md-12" style="text-align: center;">
			<h3>
				<strong>Rules</strong>
			</h3>
		</div>
	</div>


	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<div id="rules_div" style="text-align: center;">
				<hr>
				<h4 id="f1">
					There is only one valid solution to each Sudoku puzzle. <br>The
					only way the puzzle can be considered solved correctly is when all
					81 boxes contain numbers and the other Sudoku rules have been
					followed.
				</h4>
				<hr>
				<h4 id="f2">
					When you start a game of Sudoku, some blocks will be pre-filled for
					you. <br>You cannot change these numbers in the course of the
					game.
				</h4>

				<div>
					<img id="hmlg" src="resources/img/block.PNG"
						style="display: block; margin-left: auto; margin-right: auto"
						width="28%" height="28%" />
				</div>
				<hr>
				<h4 id="f3">Each column must contain all of the numbers 1
					through 9 and no two numbers in the same column of a Sudoku puzzle
					can be the same.</h4>
				<div>
					<img id="hmlg" src="resources/img/column.PNG"
						style="display: block; margin-left: auto; margin-right: auto"
						width="28%" height="28%" />
				</div>

				<hr>
				<h4 id="f4">Each row must contain all of the numbers 1 through
					9 and no two numbers in the same row of a Sudoku puzzle can be the
					same.</h4>

				<div>
					<img id="hmlg" src="resources/img/row.PNG"
						style="display: block; margin-left: auto; margin-right: auto"
						width="28%" height="28%" />
				</div>
				<hr>
				<h4>Each block must contain all of the numbers 1 through 9 and
					no two numbers in the same block of a Sudoku puzzle can be the
					same.</h4>

				<div>
					<img id="hmlg" src="resources/img/blockError.PNG"
						style="display: block; margin-left: auto; margin-right: auto"
						width="28%" height="28%" />
				</div>
			</div>
		</div>
	</div>
</body>

</html>
