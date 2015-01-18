angular.module('jacksonServices').factory('Util', [ function() {

	var dateToString = function(date) {
		return date ? date.toISOString().substring(0, 10) : '';
	};
	
	var stringToTime = function(string) {
		return Date.parse(string);
	};
	
	var stringToDate = function(string) {
		return new Date(stringToTime(string));
	};
	
	var urlBkg = function(html) {
		var start = html.indexOf('url(') + 4;
		var end = html.indexOf(');');
		return html.substring(start, end);
	};
	
	return {
		dateToString : dateToString,
		stringToTime : stringToTime,
		stringToDate : stringToDate,
		urlBkg : urlBkg
	};
} ]);