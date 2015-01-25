angular.module('jacksonServices').service('Modal', [ function() {
	var defaultId = 'newUsers';
	var show = function(id) {
		$('#' + id || defaultId).modal('show');
	};
	var toggle = function(id) {
		$('#' + id || defaultId).modal('toggle');
	};
	var hide = function(id) {
		$('#' + id || defaultId).modal('hide');
	};
	return {
		show : show,
		toggle : toggle,
		hide : hide
	};
} ]);