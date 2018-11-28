$(document).ready(function() {

	$("#lose").hide();
	$("#win").hide();
	getWinner();
	getMatchInfo();

	$('#exit').on('click', function() {
		$.ajax({
			type : "POST",
			url : "exitGame",
			success : function(result) {
				window.location.href = "./";

			},

		});
	});

	$('.btn-filter').on('click', function() {
		var $target = $(this).data('target');
		if ($target != 'all') {
			$('.table tr').css('display', 'none');
			$('.table tr[data-status="' + $target + '"]').fadeIn('slow');
		} else {
			$('.table tr').css('display', 'none').fadeIn('slow');
		}
	});

});

function getMatchInfo() {
	$
			.ajax({
				type : "POST",
				url : "getMatchInfo",
				success : function(result) {
					var parsed = JSON.parse(result);

					for (i = 0; i < parsed.numPlayers; i++) {
						var duration = undefined;
						for (y = 0; y < parsed.durations.length; y++) {
							if (parsed.durations[y].username == parsed.players[i].username)
								duration = parsed.durations[y].value;
						}
						var clas = undefined;
						var durationToShow = undefined;
						switch (duration) {
						case 0:
							clas = "disconnected";
							durationToShow = " ";
							break;
						case undefined:
							clas = "gaming";
							durationToShow = " - ";
							break;
						default:
							clas = "ended";
							durationToShow = timeConversion(duration) + " ";
							break;
						}

						$("#reviewbody")
								.append(
										'<tr data-status='
												+ clas
												+ '>'
												+ '<td> <i style="color: blue;" class="fa fa-user-circle-o fa-2x" aria-hidden="true"></i> </td>'
												+ '<td><h4>'
												+ parsed.players[i].username
												+ '</h4></td>'
												+ '<td><h5 class="title">'
												+ durationToShow + '</h5>'
												+ '</td>' + '<td><span class='
												+ clas + '>('
												+ clas.toUpperCase()
												+ ')</span></td>' + '</tr>');

					}

				},

			});
}

function getWinner() {
	$.ajax({
		type : "POST",
		url : "getWinner",
		success : function(result) {
			if (result == "winner")
				$("#win").show();
			else
				$("#lose").show();
		},

	});
}

function timeConversion(millisec) {

	var seconds = (millisec / 1000).toFixed(1);

	var minutes = (millisec / (1000 * 60)).toFixed(1);

	var hours = (millisec / (1000 * 60 * 60)).toFixed(1);

	var days = (millisec / (1000 * 60 * 60 * 24)).toFixed(1);

	if (seconds < 60) {
		return seconds + " Sec";
	} else if (minutes < 60) {
		return minutes + " Min";
	} else if (hours < 24) {
		return hours + " Hrs";
	} else {
		return days + " Days"
	}
}
