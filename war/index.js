$(document).ready(function() {
	JacksonChart.start($('#chart'), chartCallback);
	JacksonChart.loadFrom('');
	configureSearch();
	configureModal();
});

var key = '';
var chartCallback = function() {
	// this = http://api.highcharts.com/highcharts#Point
	key = JacksonChart.dateToString(new Date(this.x));
	var newUsers = JacksonChart.cache[key];

	var row = $('#userIdsRow');
	row.empty();
	newUsers.forEach(function(item) {
		var link = $('<a>', {
			id : 'a_' + item,
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

var configureSearch = function() {
	var max = new Date();
	max.setDate(max.getDate() - 1);
	$('#date').attr('max', JacksonChart.dateToString(max));

	$('#dateForm').submit(function(event) {
		event.preventDefault();
		JacksonChart.loadFrom($('#date').val());
		ga('send', 'event', 'search', 'submit', $('#date').val());
	});
};

var configureModal = function() {
	$('#newUsers').on('shown.bs.modal', function(e) {
		goTo('/details?key=' + key);
	});

	$('#newUsers').on('hidden.bs.modal', function(e) {
		goTo('/');
	});
};

var goTo = function(url) {
	history.pushState({}, '', url);
	registerPageview();
}

$(window).on("popstate", function(e) {
	registerPageview();
});

var registerPageview = function() {
	ga('send', 'pageview');
};