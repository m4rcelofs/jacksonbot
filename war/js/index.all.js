/*
 * JavaScript file created by Rockstarapps Concatenation
*/

/*
 * START OF FILE - /JacksonBot/war/js/chartOptions.js
 */
Highcharts.setOptions({
	lang : {
		contextButtonTitle : 'Exportar gráfico',
		loading : 'Carregando...',
		noData : 'Sem dados para exibir ):',
		printChart : 'Imprimir gráfico',
		downloadJPEG : 'Salvar como JPEG',
		downloadPDF : 'Salvar como PDF',
		downloadPNG : 'Salvar como PNG',
		downloadSVG : 'Salvar como SVG',
		resetZoom : 'Resetar zoom',
		resetZoomTitle : 'Voltar para o zoom inicial',
		shortMonths : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago',
				'Set', 'Out', 'Nov', 'Dez' ],
		months : [ 'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
				'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro',
				'Dezembro' ],
		weekdays : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta',
				'Sexta', 'Sábado' ]
	}
});

var jacksonChartOptions = {
	options : {
		chart : {
			zoomType : 'x'
		},
		legend : {
			enabled : false
		},
		plotOptions : {
			areaspline : {
				allowPointSelect : true,
				fillColor : {
					linearGradient : {
						x1 : 0,
						y1 : 0,
						x2 : 0,
						y2 : 1
					},
					stops : [
							[ 0, '#7CC644' ],
							[
									1,
									Highcharts.Color('#7CC644').setOpacity(0.1)
											.get('rgba') ] ]
				},
				marker : {
					radius : 2
				},
				lineWidth : 1,
				states : {
					hover : {
						lineWidth : 1
					}
				},
				threshold : null
			},
			series : {
				cursor : 'pointer',
				point : {
					events : {}
				}
			}
		}
	},
	credits : {
		enabled : true,
		text : 'thanks to ereptools.tk',
		href : 'http://ereptools.tk/'
	},
	title : {
		text : 'Novos jogadores no eBrasil'
	},
	subtitle : {
		text : 'Selecione uma área para dar zoom, clique em um dia para ver jogadores'
	},
	xAxis : {
		type : 'datetime',
		minRange : 5 * 24 * 3600000, // 5d
		title : {
			text : 'Data'
		}
	},
	yAxis : {
		allowDecimals : false,
		min : 0,
		title : {
			text : 'Número de jogadores'
		}
	},
	series : [ {
		type : 'areaspline',
		name : 'Novos jogadores no eBrasil',
		color : '#2CA81F',
		data : [ [ new Date(), 8 ] ]
	} ]
};
/*
 * END OF FILE - /JacksonBot/war/js/chartOptions.js
 */

/*
 * START OF FILE - /JacksonBot/war/js/services.js
 */
var jacksonServices = angular.module('jacksonServices', [ 'ngResource' ]);

jacksonServices.factory('Jackson', [ '$resource', function($resource) {
	return $resource('get');
} ]);
/*
 * END OF FILE - /JacksonBot/war/js/services.js
 */

/*
 * START OF FILE - /JacksonBot/war/js/controllers.js
 */
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
/*
 * END OF FILE - /JacksonBot/war/js/controllers.js
 */

/*
 * START OF FILE - /JacksonBot/war/js/app.js
 */
var jacksonApp = angular.module('jacksonApp', [ 'highcharts-ng',
		'jacksonControllers', 'jacksonServices' ]);
/*
 * END OF FILE - /JacksonBot/war/js/app.js
 */

/*
 * JavaScript file created by Rockstarapps Concatenation
*/
