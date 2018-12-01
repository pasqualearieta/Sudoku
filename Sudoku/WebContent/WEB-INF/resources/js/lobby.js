$(document).ready(function() {

	getEventsFromServer();

	$("#diff_drop").change(function() {
		var selectedVal = $(this).find(':selected').val();
		document.getElementById('difficulty').value = selectedVal;
	});

});

function getEventsFromServer() {
	var ind = $("#page_current").text();
	$
			.ajax({
				url : "refresh",
				data : {
					index : ind
				},
				success : function(result) {
					console.log(result);
					setTimeout(
							function() {
								if (rooms != "null") {
									var rooms = JSON.parse(result);
									$("#torep").html("");
									$("#foreach").html("");
									var indexStop = rooms.tot_pag;
									$('#total_room_pages').html(indexStop);
									var currPag = rooms.curr_pag;
									for (key in rooms.matches) {
										$("#torep")
												.append(
														'<tr> <td align="center" class="vertical-divider">'
																+ rooms.matches[key].creator
																+ '</td>'
																+ '<td align="center" class="vertical-divider">'
																+ rooms.matches[key].name
																+ '</td>'
																+ '<td align="center" class="vertical-divider difficulty">'
																+ rooms.matches[key].difficulty
																+ '</td>'
																+ '<td align="center" class="vertical-divider"><button value="'
																+ rooms.matches[key].id
																+ '" class="wrap_button join" type="submit">'
																+ '<i class="fa fa-check-square-o fa-2x check"aria-hidden="true"></i></button></td></tr>');
									}

									if (indexStop > 0) {
										$("#foreach")
												.append(
														'<li><button class="btn btn-warning btn-arrow-left pg-button-arrow-left" value="'
																+ currPag
																+ '"type="submit"> <span class="glyphicon glyphicon-backward" aria-hidden="true"></span> </button></li>');
										for (var i = 1; i <= indexStop; i++) {
											$("#foreach").append(
													'<li><button class = "btn btn-warning pg-button" type="submit" value="'
															+ currPag + '">'
															+ i
															+ '</button></li>');
										}

										$("#foreach")
												.append(
														'	<li><button class="btn btn-warning btn-arrow-right pg-button-arrow-right" value="'
																+ currPag
																+ '" type="submit"> <span class="glyphicon glyphicon-forward" aria-hidden="true"></span></button></li>');

										$("#pag_num")
												.html(
														'Page '
																+ currPag
																+ '<c:if test="'
																+ indexStop
																+ ' gt 1"> of '
																+ indexStop
																+ '</c:if>');
									}

								}
								getEventsFromServer();

							}, 270);

				},
				error : function() {
					// call events again after some time
					setTimeout(function() {
						getEventsFromServer();
					}, 5000);
				}
			});

	$(".join").click(function() {
		// alert($(this).closest('tr').find('td.difficulty').text());

		$.ajax({
			type : "POST",
			url : "joinRoom",
			data : {
				room : $(this).val(),
			},
			success : function() {
				window.location.href = "./wait";
			}
		});
	});

	$(".pg-button").click(function() {
		var ind = $(this).text();
		$("#page_current").html(ind);
		$.ajax({
			type : "POST",
			url : "roomPagination",
			data : {
				index : ind
			},
			success : function(result) {
				// doNothing. Refresh will do everyThing

			},
			error : function() {
				// call events again
				// after some time
				setTimeout(function() {
					getEventsFromServer();
				}, 5000);
			}
		});
	});

	$(".pg-button-arrow-right").click(function() {
		var ind = parseInt($('.pg-button-arrow-right').val()) + 1;
		if (ind > parseInt($("#total_room_pages").text()))
			ind = ind - 1;

		$.ajax({
			type : "POST",
			url : "roomPagination",
			data : {
				index : ind
			},
			success : function() {
				$('#page_current').html(ind);
			}
		});
	});

	$(".pg-button-arrow-left").click(function() {
		var ind = parseInt($('.pg-button-arrow-left').val()) - 1;

		if (ind == 0)
			ind = 1;

		$.ajax({
			type : "POST",
			url : "roomPagination",
			data : {
				index : ind
			},
			success : function() {
				$('#page_current').html(ind);
			}
		});
	});

};
