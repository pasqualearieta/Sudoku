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
					window.location.href = "./lobby";
				} else {

					$('.lg-al').fadeIn('slow');
					$("#lgin_message").html(data);
				}

			},
		});

		return false;
	});

	$("#registration_form").on("submit", function() {

		$("#register-submit").hide();

		$.ajax({
			type : "POST",
			url : "register",
			data : {
				username : $('#username_register').val(),
				password : $('#password_register').val(),
				confirm_password : $('#confirm-password_register').val()
			},
			success : function(data) {
				if (data === "SUCCESS") {
					$(".rg-ok").fadeIn(2500);

					setTimeout(function() {
						window.location.href = "./lobby";
					}, 3000);
				} else if (data === "USERNAME") {
					$('.rgu-al').fadeIn('slow');
					$("#register-submit").show();
				} else if (data === "PASSWORD") {
					$('.rgp-al').fadeIn('slow');
					$("#register-submit").show();

				}

			},
		});

		return false;
	});

	$('#logo').on("click", function() {
		$.ajax({
			type : "POST",
			url : "goToHome",

			success : function() {
				window.location.href = "./";
			},
		});

	});

	$('#btn-lgn').on("click", function() {
		$('.lg-al').fadeOut('slow');

	});

	$('#btn-rgp').on("click", function() {
		$('.rgp-al').fadeOut('slow');
	});

	$('#btn-rgu').on("click", function() {
		$('.rgu-al').fadeOut('slow');
	});

});