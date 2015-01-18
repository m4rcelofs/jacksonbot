angular.module('jacksonServices', [ 'ngResource' ]);

angular.module('jacksonControllers', []); 

angular.module('jacksonApp', [ 'highcharts-ng', 'ngStorage',
                       		'ga', 'jacksonControllers', 'jacksonServices' ]);