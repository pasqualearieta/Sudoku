$(document).ready(function() {

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
});
