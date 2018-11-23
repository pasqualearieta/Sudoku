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
