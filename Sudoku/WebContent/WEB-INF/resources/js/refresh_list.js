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
															+ '<td align="center" class="vertical-divider">' + rooms[key].match.difficulty +'</td>'
															+ '<td align="center" class="vertical-divider"><button value="' +key +'" class="wrap_button"  id="join" type="submit">'
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

}
$(document).ready(getEventsFromServer());