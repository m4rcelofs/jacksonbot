var jacksonServices = angular.module('jacksonServices', [ 'ngResource' ]);

jacksonServices.factory('Jackson', [ '$resource', function($resource) {
	return $resource('get');
} ]);