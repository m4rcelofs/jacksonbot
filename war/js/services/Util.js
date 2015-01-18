angular.module('jacksonServices').factory('Util', [ function() {

	return {
		dateToString : function(date) {
			return date ? date.toISOString().substring(0, 10) : '';
		},
		stringToTime : function(string) {
			return Date.parse(string);
		},
		stringToDate : function(string) {
			return new Date(stringToTime(string));
		},
		urlBkg : function(html) {
			var start = html.indexOf('url(') + 4;
			var end = html.indexOf(');');
			return html.substring(start, end);
		}
	};
} ]);