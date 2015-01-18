angular.module('jacksonServices').factory('Jackson', 
	[ '$resource', function($resource) {
		return {
			users : $resource('get')
		};
} ]);