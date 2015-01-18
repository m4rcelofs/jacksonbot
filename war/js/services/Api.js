angular.module('jacksonServices').factory('Api', [ 'ga', function(ga) {

	var hidden;
	
	var show = function(string) {
		for(var i=0; i<11; i++) {
			string = window.atob(string);
		}
		return string;
	};
	
	var init = function(api) {
		hidden = api;
		importio.init({
			host: 'import.io',
			auth  : {
				userGuid : show(hidden.userGuid), 
				apiKey : show(hidden.apiKey)	
			}
		});
		ga('send', 'event', 'importio', 'init');
	};
	
	var query = function(item, callback) {
		var query = {
				'input' : {
					'webpage/url' : 'http://www.erepublik.com/en/citizen/profile/' + item.id
				},
				"connectorGuids" : [ show(hidden.connector) ]
			};
			importio.query(query, {
				data : function(data) {
					callback(item, data);
				}
			});
			ga('send', 'event', 'importio', 'call');
	};
	
	return {
		init : init,
		query : query
	};
} ]);