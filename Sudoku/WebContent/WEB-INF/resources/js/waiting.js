$(document).ready(function() {
	$("#start-room").hide();

	$('#disconnected').on('click', function() {
		window.location.href = "./lobby";
	});

	$("#leave-room").click(function() {
		var room = document.getElementById('idRoom').value;
		$.ajax({
			type : "POST",
			url : "leaveRoom",
			data : {
				idRoom : room
			},
			success : function() {
				window.location.href = "./lobby";
			},
		});
	});

	checkBoardFull();

	$("#start-room").click(function() {
		$.ajax({
			type : "POST",
			url : "startRoom",
			success : function() {
				window.location.href = "./gameBoard";
			}
		});
	});
});

function checkBoardFull() {
	console.log("board")
	$.ajax({
		type : "POST",
		url : "checkBoardFull",
		success : function(result) {
			if (result === "start_match") {
				$("#start-room").show();
				setTimeout(function() {
					checkBoardFull();
				}, 1000)
			} else if (result === "go_to_board")
				window.location.href = "./gameBoard";
			else if (result === "chiudi"){
				$("#resultModal").modal('show');
				$("#leave-room").hide();
			}
			else {
				$("#start-room").hide();
				setTimeout(function() {
					checkBoardFull();
				}, 1000)
			}
		},
	});
}
