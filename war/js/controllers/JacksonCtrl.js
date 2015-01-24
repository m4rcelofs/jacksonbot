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
			
			$scope.user;
			$scope.selectedDay = '';
			$scope.selectedNewPlayers = [];
			
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
				if(data.length > 0) {
					$scope.selectedDay = data[data.length-1].day;
					JacksonChart.series[0].data = newData;					
				}
				JacksonChart.loading = false;
			};
			
			$scope.load = function() {
				JacksonChart.loading = true;
				var param = Util.dateToString($scope.loadDate);
				Jackson.players.query({
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
				}
				if(!$session.cache[key]) {
					return;
				}
				$scope.selectedDay = key;
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
			
			if(!$storage.search) {
				$storage.search = {};	
			}
			
			$scope.doSearch = function(item){
				if(!item.query) { return true; } //not loaded yet

				var result = true;
				if(result && $storage.search.avatar) {
					result = item.avatar && (item.avatar.indexOf('default_male.gif') == -1);
				}
				if(result && $storage.search.party) {
					result = item.query.party || false;
				}
				if(result && $storage.search.newspaper) {
					result = item.query.newspaper || false;
				}
				if(result && $storage.search.mu) {
					result = item.query.mu || false;
				}
				if(result && $storage.search.alive) {
					result = !item.query.dead && !item.query.banned;
				}
				if(result && $storage.search.level) {
					result = item.query.level && parseInt(item.query.level) > 1;
				}
				if(result && $storage.search.friends) {
					result = item.query.friends && parseInt(item.query.friends) > 1;
				}
				if(result && $storage.search.messaged) {
					//TODO
				}
				return result;
			};
			
			$scope.maxDate = Util.dateToString(new Date());

			Jackson.user.get({}, function(data){
				$scope.user = data;
			});
			
			Jackson.api.get({}, function(data) {
				Api.init(data);
			});
			
			$scope.load();
	    } ]);