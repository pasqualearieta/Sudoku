$(document).ready(function() {
	checkBoardFull();
});

function checkBoardFull() {
	$.ajax({
		type : "POST",
		url : "checkBoardFull",
		success : function(result) {
			if (result === "start") {
				window.location.href = "./";
			}
			else {
				setTimeout(function() {
					checkBoardFull();
				}, 1000)
			}
		},
	});
}