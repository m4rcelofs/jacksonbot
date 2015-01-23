angular.module('jacksonServices').factory('Jackson', 
	[ '$resource', function($resource) {
		return {
			api : $resource('api'),
			players : $resource('get'),
			user : $resource('user')
		};
} ]);