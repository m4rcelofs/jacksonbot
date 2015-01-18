angular.module('jacksonControllers').controller('JacksonCtrl', [
	    '$localStorage', '$sessionStorage', '$scope',
		'Api', 'ga',
		'Jackson', 'JacksonChart',
		'Modal', 'Util',
		function($storage, $session, $scope, Api, ga, Jackson, JacksonChart,  Modal, Util) {

			$scope.$storage = $storage.$default({
				apiEnabled: true
			});
			$scope.$session = $session;
			$scope.JacksonChart = JacksonChart;
			$scope.Util = Util;
			
			var queryCallback = function(item, data) {
				var response = data[0].data;
				item.query = response;
				item.avatar = Util.urlBkg(response.avatar);
				$scope.$apply(function(){
					item;
				});
			};
			
			$scope.execApi = function() {
				var arr = $scope.selectedNewPlayers; 
				arr.forEach(function(item){
					Api.query(item, queryCallback);
				});
				ga('send', 'event', 'importio', 'exec');
			};

			var loadCallback = function(data) {
				var newData = [];
				$session.cache = {};
				data.forEach(function(item) {
					newData.push([ Util.stringToTime(item.day),
							item.newUsers.length ]);
					$session.cache[item.day] = item.newUsers;
				});
				$scope.selectedDay = data[data.length-1].day;
				JacksonChart.series[0].data = newData;
				JacksonChart.loading = false;
			};
			
			$scope.load = function() {
				JacksonChart.loading = true;
				var param = Util.dateToString($scope.loadDate);
				Jackson.query({
					from : param
				}, loadCallback);
			};
			
			JacksonChart.options.plotOptions.series.point.events.click = function() {
				var date = new Date(this.x);
				$scope.$apply(function(){
					$scope.select(date);
				});
				ga('send', 'event', 'chart', 'click', Util.dateToString(date));
			};
			
			$scope.plusOne = function() {
				var date = Util.stringToDate($scope.selectedDay);
				date.setDate(date.getDate() + 1);
				$scope.select(date);
			};
			
			$scope.minusOne = function() {
				var date = Util.stringToDate($scope.selectedDay);
				date.setDate(date.getDate() - 1);
				$scope.select(date);
			};
			
			$scope.select = function(date) {
				var key = $scope.selectedDay;
				if(date) {
					key = Util.dateToString(date);
					$scope.selectedDay = key;
				}
				if(!$session.cache[key]) {
					return;
				}	
				$scope.selectedNewPlayers = [];
				$session.cache[key].forEach(function(item){
					$scope.selectedNewPlayers.push({
						id: item
					})
				});
				if($storage.apiEnabled) {
					$scope.execApi();	
				}
				Modal.show();
			};
			
			$scope.load();
			Api.init();
	    } ]);