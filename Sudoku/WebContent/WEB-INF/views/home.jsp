<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">

<head>
<meta charset="utf-8">
<title>Sudo -ku turtles</title>
<jsp:include page="navbar.jsp"></jsp:include>
<link href="resources/css/home.css" rel="stylesheet">
</head>
<body>
	<img id="bg" src="resources/img/back.jpg" />

	<div class="container">

		<div class="row">
			<c:if test="${empty username }">
				<div class="row">
					<div class="col-md-12" style="text-align: center;">
						<h2>
							<strong>Login or Register to Play</strong>
						</h2>
					</div>
				</div>
			</c:if>
		</div>

		<div class="row" style="margin-top: 1%;">
			<div>
				<img id="hmlg" src="resources/img/home.gif"
					style="display: block; margin-left: auto; margin-right: auto"
					width="40%" height="40%" />
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
						There is only <strong> one valid solution </strong>to each Sudoku
						puzzle. <br>The only way the puzzle can be considered solved
						correctly is when all the 81 cells contain numbers and the other
						Sudoku rules have been followed. <br> You can choose from
						three different level. (Easy, Medium, Hard)
					</h4>
					<hr>
					<h4 id="f2">
						When you start a game of Sudoku, some blocks, depending on the
						difficulty choose will be pre-filled for you. <br>You cannot
						change these numbers in the course of the game.
					</h4>

					<div>
						<img id="hmlg" src="resources/img/block.PNG"
							style="display: block; margin-left: auto; margin-right: auto"
							width="28%" height="28%" />
					</div>
					<hr>
					<h4 id="f3">
						Each column must contain all of the numbers 1 through 9. <br>It
						is no possible have two or more equal numbers in the same column
						of a Sudoku puzzle.
					</h4>
					<div>
						<img id="hmlg" src="resources/img/column.PNG"
							style="display: block; margin-left: auto; margin-right: auto"
							width="28%" height="28%" />
					</div>

					<hr>
					<h4 id="f4">
						Each row must contain all of the numbers 1 through 9. <br> It
						is no possible have two or more equal numbers in the same row of a
						Sudoku puzzle.
					</h4>

					<div>
						<img id="hmlg" src="resources/img/row.PNG"
							style="display: block; margin-left: auto; margin-right: auto"
							width="28%" height="28%" />
					</div>
					<hr>
					<h4>
						Each block must contain all of the numbers 1 through 9. <br>It
						is no possible have two or more equal numbers in the same block of
						a Sudoku puzzle.
					</h4>

					<div>
						<img id="hmlg" src="resources/img/blockError.PNG"
							style="display: block; margin-left: auto; margin-right: auto"
							width="28%" height="28%" />
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
