var winner = null;

$(document).ready(function() {

	getWinner();
	getMatchInfo();

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
	$.ajax({
				type : "POST",
				url : "getMatchInfo",
				success : function(result) {
					
					console.log(JSON.parse(result))
					alert(result.players[0].username)
					
					for ( var user in result.players) {
						console.log(user.username);
						$("#reviewbody")
								.append(
										'<tr data-status="pagado"> <td> <i class="fa fa-user-circle-o fa-2x" aria-hidden="true"></i> </td> <td>'
												+ '<h5 class="media-meta">'
												+ parsed[user].username
												+ '</h5>'
												+ '</td>'
												+ '<td>'
												+ '<h5 class="title">'
												+ durations[user]
												+ parsed.difficulty
												+ '</h5>'
												+ '</td>'
												+ '<td><span class="pagado">(Pagado)</span></td>'
												+ '</tr>');

					}

				},

			});
}

function getWinner() {
	$.ajax({
		type : "POST",
		url : "getWinner",
		success : function(result) {
			winner = result;
	
		},

	});
}