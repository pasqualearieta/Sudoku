
function loginAttempt() {
	
	var username = document.getElementByName("username-log-in").value();
	var password = document.getElementByName("password-log-in").value();	
	$.ajax({
		type: 'POST',
		url : 'login', 
		contentType: 'application/json; charset=utf-8',
		data: JSON.stringify(username, password), 
		success : function(result) {
			console.log("login action")
			if (result === "success")
				$("#login_failed").hide();
			else
				$("#login_failed").show();
		}
	});
}

$(document).ready(function() {
	$("#login-submit").click(loginAttempt());
});
