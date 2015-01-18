angular.module('jacksonServices').service('Modal', [ function() {
	var id = 'newUsers';
	var show = function() {
		$('#' + id).modal('show');
	};
	return {
		show : show
	};
} ]);