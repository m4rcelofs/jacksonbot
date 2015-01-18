angular.module('jacksonServices').factory('Jackson',
		[ '$resource', function($resource) {
			return $resource('get');
		} ]);