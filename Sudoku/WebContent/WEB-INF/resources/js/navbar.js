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
				window.location.href = "./"
			},
			error : function(data) {
				$('.lg-al').fadeIn('slow');
			}
		});

		return false;
	});

	$('#btn-lgn').on("click", function() {
		$('.lg-al').fadeOut('slow');
	});

});