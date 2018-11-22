$(document).ready(function() {

	$("#diff_drop").change(function() {
		var selectedVal = $(this).find(':selected').val();
		document.getElementById('difficulty').value = selectedVal;
	});

});
