var jacksonControllers = angular.module('jacksonControllers', []);

jacksonControllers.controller('JacksonCtrl', [
		'$scope',
		'Jackson',
		function($scope, Jackson) {

			var cache = {};
			
			var dateToString = function(date) {
				return date ? date.toISOString().substring(0, 10) : '';
			};

			var stringToDate = function(string) {
				return Date.parse(string);
			};
			
			$scope.chartOpts = jacksonChartOptions;

			$scope.search = function() {
				$scope.load();
				ga('send', 'event', 'search', 'submit');
			};
			
			$scope.load = function() {
				$scope.chartOpts.loading = true;
				var param = dateToString($scope.loadDate);
				Jackson.query({
					from : param
				}, function(data) {
					var newData = [];
					data.forEach(function(item) {
						newData.push([ stringToDate(item.day),
								item.newUsers.length ]);
						cache[item.day] = item.newUsers;
					});
					$scope.chartOpts.series[0].data = newData;
					$scope.chartOpts.loading = false;
				});
			};
			
			$scope.chartOpts.options.plotOptions.series.point.events.click = function() {
				var self = this;
				$scope.$apply(function(){
					$scope.selectedDay = dateToString(new Date(self.x));
					$scope.selectedNewPlayers = cache[$scope.selectedDay];
				});
				//TODO remove
				$('#newUsers').modal('show');
				
				//TODO pageview qdo fecha
				ga('send', 'pageview', '/details');
			};
			
			$scope.toErep = function() {
				ga('send', 'event', 'erepLink', 'click');
			};
		
			$scope.load();
		} ]);