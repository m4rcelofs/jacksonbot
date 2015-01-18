angular.module('jacksonServices').factory('Jackson', 
	[ '$resource', function($resource) {
		return {
			api : $resource('api'),
			users : $resource('get')
		};
} ]);