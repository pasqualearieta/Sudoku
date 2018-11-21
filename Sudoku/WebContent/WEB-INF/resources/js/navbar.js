$(document).ready(function() {
	$("#login_form").on("submit", function() {
		$.ajax({
			type : "POST",
			url : "login",
			data : {
				username : $('#username_log_in').val(),
				password : $('#password_log_in').val()
			},
			success : function(data) {

				if (data === "LOGIN_OK") {
					window.location.href = "./"
				} else {

					$('.lg-al').fadeIn('slow');
					$("#lgin_message").html(data);
				}

			},
		});

		return false;
	});

	$('#btn-lgn').on("click", function() {
		$('.lg-al').fadeOut('slow');
	});

	$('#logo').on("click", function() {
		window.location.href = "./";
	});

});