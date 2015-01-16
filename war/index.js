$(document).ready(function() {
	JacksonChart.start($('#chart'));
	JacksonChart.loadFrom('');
	
	var max = new Date();
	max.setDate(max.getDate() - 1);
	max = max.toISOString().substring(0,10);
	$('#date').attr('max', max);
	
	$('#dateForm').submit(function(event) {
		event.preventDefault();
		JacksonChart.loadFrom($('#date').val());
	});
});