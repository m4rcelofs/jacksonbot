$(document).ready(function() {
	JacksonChart.start($('#chart'), chartCallback);
	JacksonChart.loadFrom('');

	var max = new Date();
	max.setDate(max.getDate() - 1);
	$('#date').attr('max', JacksonChart.dateToString(max));

	$('#dateForm').submit(function(event) {
		event.preventDefault();
		JacksonChart.loadFrom($('#date').val());
	});
});

var chartCallback = function() {
	// this = http://api.highcharts.com/highcharts#Point
	var key = JacksonChart.dateToString(new Date(this.x));
	var newUsers = JacksonChart.cache[key];
	console.log(newUsers);

	var row = $('#userIdsRow');
	row.empty();
	newUsers.forEach(function(item) {
		var link = $('<a>', {
			href : 'http://www.erepublik.com/en/citizen/profile/' + item,
			target : '_blank',
			text : item
		});
		var div = $('<div>', {
			'class' : 'col-md-2'
		});
		div.append(link);
		row.append(div);
	});

	$('#selectedDay').text(key);
	$('#selectedLength').text(newUsers.length);
	$('#newUsers').modal('toggle');
};