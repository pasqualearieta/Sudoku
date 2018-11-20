
$(document).ready(function() {

	$("#lg-btn").click(function() {
		$("#log-f").prop('hidden', true);
	});

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

				$("#log-f").prop('hidden', false);
			}
		});

		return false;
	});

});