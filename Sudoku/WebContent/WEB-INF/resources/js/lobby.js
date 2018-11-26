$(document).ready(function() {
	
	getEventsFromServer();
	
	$("#diff_drop").change(function() {
		var selectedVal = $(this).find(':selected').val();
		document.getElementById('difficulty').value = selectedVal;
	});
	
	$(".pg-button").click(function() {
		var ind = $(this).text();
		
		$.ajax({
			type : "POST",
			url : "roomPagination",
			data : {
				index : ind
			},
			success : function() {
				location.reload();
			}
		});
	});
		
	$(".pg-button-arrow-right").click(function() {
		var ind = $('.pg-button-arrow-right').val();
		
		alert(ind);
		
		$.ajax({
			type : "POST",
			url : "roomPagination",
			data : {
				//FIXME controllo per non sforare dall'ultima pagina
				//TODO freccia sinistra
				index : ++ind
			},
			success : function() {
				location.reload();
			}
		});
	});
});

function getEventsFromServer() {
	$.ajax({
				url : "refresh",
				data : {
					type : 'json'
				},
				success : function(result) {
					setTimeout(
							function() {
								console.log(result);
								$("#torep").html("");
								var rooms = JSON.parse(result);
								for (key in rooms) {
									$("#torep")
											.append('<tr> <td align="center" class="vertical-divider">' + rooms[key].creator.username + '</td>'
															+ '<td align="center" class="vertical-divider">' + rooms[key].match.name +'</td>'
															+ '<td align="center" class="vertical-divider difficulty">' + rooms[key].match.difficulty +'</td>'
															+ '<td align="center" class="vertical-divider"><button value="' +key +'" class="wrap_button join" type="submit">'
															+ '<i class="fa fa-check-square-o fa-2x check" aria-hidden="true"></i></button></td></tr>');
								}
								getEventsFromServer();

							}, 2000);

				},
				error : function() {
					// call events again after some time
					setTimeout(function() {
						getEventsFromServer();
					}, 5000);
				}
			});
		

	$(".join").click(function() {
//		alert($(this).closest('tr').find('td.difficulty').text());
		
		$.ajax({
			type : "POST",
			url : "joinRoom",
			data : {
				//FIXME controllo per non sforare dall'ultima pagina
				//TODO freccia sinistra
				room : $(this).val(),
//				difficulty : $(this).closest('tr').find('td.difficulty').text()
			},
			success : function() {
				 window.location.href = "./";
			}
		});
	});
};
