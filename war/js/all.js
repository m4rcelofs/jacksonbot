/*
 * JavaScript file created by Rockstarapps Concatenation
*/

/*
 * START OF FILE - /JacksonBot/war/js/app.js
 */
angular.module('jacksonServices', [ 'ngResource' ]);

angular.module('jacksonControllers', []); 

angular.module('jacksonApp', [ 'highcharts-ng', 'ngStorage',
                       		'ga', 'jacksonControllers', 'jacksonServices' ]);
/*
 * END OF FILE - /JacksonBot/war/js/app.js
 */

/*
 * START OF FILE - /JacksonBot/war/js/lib/ga.js
 */
(function (angular) {
'use strict';

angular.module('ga', [])
    .factory('ga', ['$window', function ($window) {

        var ga = function() {
            if (angular.isArray(arguments[0])) {
                for(var i = 0; i < arguments.length; ++i) {
                    ga.apply(this, arguments[i]);
                }
                return;
            }
            // console.log('ga', arguments);
            if ($window.ga) {
                $window.ga.apply(this, arguments);
            }
        };

        return ga;
    }])
    .run(['$rootScope', '$location', 'ga', function ($rootScope, $location, ga) {
       
        $rootScope.$on('$routeChangeStart', function() {
            ga('set', 'page', $location.url());
        });

    }]) 
    /**
      ga="'send', 'event', 'test'" ga-on="click|hover|init"
      */
    .directive('ga', ['ga', function(ga) {
        return {
          restrict: 'A',
          scope: false,
          link: function($scope, $element, $attrs) {
            var bindToEvent = $attrs.gaOn || 'click',
                command = $attrs.ga;

            var onEvent = function() {
                if (command) {
                    if (command[0] === '\'') command = '[' + command + ']';

                    command = $scope.$eval(command);
                } else {
                    // auto command
                    var href = $element.attr('href');
                    if (href && href === '#') href = '';
                    var category = $attrs.gaCategory ? $scope.$eval($attrs.gaCategory) : 
                            (href && href[0] !== '#' ? (href.match(/\/\//) ? 'link-out' : 'link-in') : 'button'),
                        action = $attrs.gaAction ? $scope.$eval($attrs.gaAction) : 
                            (href ? href : 'click'),
                        label = $attrs.gaLabel ? $scope.$eval($attrs.gaLabel) : 
                            ($element[0].title || ($element[0].tagName.match(/input/i) ? $element.attr('value') : $element.text())).substr(0, 64),
                        value = $attrs.gaValue ? $scope.$eval($attrs.gaValue) : null;
                    command = ['send', 'event', category, action, label];
                    if (value !== null) command.push(value);
                }
                ga.apply(null, command);
            };

            if (bindToEvent === 'init') {
                onEvent();
            } else {
                $element.bind(bindToEvent, onEvent);
            }
          }
        };
      }]);
})(angular);


/*
 * END OF FILE - /JacksonBot/war/js/lib/ga.js
 */

/*
 * START OF FILE - /JacksonBot/war/js/lib/highcharts-ng.min.js
 */
/**
 * highcharts-ng
 * @version v0.0.7 - 2014-08-23
 * @link https://github.com/pablojim/highcharts-ng
 * @author Barry Fitzgerald <>
 * @license MIT License, http://www.opensource.org/licenses/MIT
 */
!function(){"use strict";function a(){return{indexOf:function(a,b,c){void 0===c&&(c=0),0>c&&(c+=a.length),0>c&&(c=0);for(var d=a.length;d>c;c++)if(c in a&&a[c]===b)return c;return-1},prependMethod:function(a,b,c){var d=a[b];a[b]=function(){var a=Array.prototype.slice.call(arguments);return c.apply(this,a),d?d.apply(this,a):void 0}},deepExtend:function a(b,c){if(angular.isArray(c)){b=angular.isArray(b)?b:[];for(var d=0;d<c.length;d++)b[d]=a(b[d]||{},c[d])}else if(angular.isObject(c))for(var e in c)b[e]=a(b[e]||{},c[e]);else b=c;return b}}}function b(a){var b=0,c=function(a){var c=!1;return angular.forEach(a,function(a){angular.isDefined(a.id)||(a.id="series-"+b++,c=!0)}),c},d=["xAxis","yAxis"],e=function(b,c,e){var f={},g={chart:{events:{}},title:{},subtitle:{},series:[],credits:{},plotOptions:{},navigator:{enabled:!1}};return f=e.options?a.deepExtend(g,e.options):g,f.chart.renderTo=c[0],angular.forEach(d,function(c){angular.isDefined(e[c])&&(f[c]=angular.copy(e[c]),(angular.isDefined(e[c].currentMin)||angular.isDefined(e[c].currentMax))&&(a.prependMethod(f.chart.events,"selection",function(a){var d=this;a[c]?b.$apply(function(){b.config[c].currentMin=a[c][0].min,b.config[c].currentMax=a[c][0].max}):b.$apply(function(){b.config[c].currentMin=d[c][0].dataMin,b.config[c].currentMax=d[c][0].dataMax})}),a.prependMethod(f.chart.events,"addSeries",function(){b.config[c].currentMin=this[c][0].min||b.config[c].currentMin,b.config[c].currentMax=this[c][0].max||b.config[c].currentMax})))}),e.title&&(f.title=e.title),e.subtitle&&(f.subtitle=e.subtitle),e.credits&&(f.credits=e.credits),e.size&&(e.size.width&&(f.chart.width=e.size.width),e.size.height&&(f.chart.height=e.size.height)),f},f=function(a,b){var c=a.getExtremes();(b.currentMin!==c.dataMin||b.currentMax!==c.dataMax)&&a.setExtremes(b.currentMin,b.currentMax,!1)},g=function(a,b,c){(b.currentMin||b.currentMax)&&a[c][0].setExtremes(b.currentMin,b.currentMax,!0)},h=function(a){return angular.extend({},a,{data:null,visible:null})};return{restrict:"EAC",replace:!0,template:"<div></div>",scope:{config:"=",disableDataWatch:"="},link:function(b,i){var j={},k=function(d){var e,f=[];if(d){var g=c(d);if(g)return!1;if(angular.forEach(d,function(a){f.push(a.id);var b=l.get(a.id);b?angular.equals(j[a.id],h(a))?(void 0!==a.visible&&b.visible!==a.visible&&b.setVisible(a.visible,!1),b.setData(angular.copy(a.data),!1)):b.update(angular.copy(a),!1):l.addSeries(angular.copy(a),!1),j[a.id]=h(a)}),b.config.noData){var i=!1;for(e=0;e<d.length;e++)if(d[e].data&&d[e].data.length>0){i=!0;break}i?l.hideLoading():l.showLoading(b.config.noData)}}for(e=l.series.length-1;e>=0;e--){var k=l.series[e];"highcharts-navigator-series"!==k.options.id&&a.indexOf(f,k.options.id)<0&&k.remove(!1)}return!0},l=!1,m=function(){l&&l.destroy(),j={};var a=b.config||{},c=e(b,i,a),f=a.func||void 0;l=a.useHighStocks?new Highcharts.StockChart(c,f):new Highcharts.Chart(c,f);for(var h=0;h<d.length;h++)a[d[h]]&&g(l,a[d[h]],d[h]);a.loading&&l.showLoading()};m(),b.disableDataWatch?b.$watchCollection("config.series",function(a){k(a),l.redraw()}):b.$watch("config.series",function(a){var b=k(a);b&&l.redraw()},!0),b.$watch("config.title",function(a){l.setTitle(a,!0)},!0),b.$watch("config.subtitle",function(a){l.setTitle(!0,a)},!0),b.$watch("config.loading",function(a){a?l.showLoading():l.hideLoading()}),b.$watch("config.credits.enabled",function(a){a?l.credits.show():l.credits&&l.credits.hide()}),b.$watch("config.useHighStocks",function(a,b){a!==b&&m()}),angular.forEach(d,function(a){b.$watch("config."+a,function(b,c){b!==c&&b&&(l[a][0].update(b,!1),f(l[a][0],angular.copy(b)),l.redraw())},!0)}),b.$watch("config.options",function(a,b,c){a!==b&&(m(),k(c.config.series),l.redraw())},!0),b.$watch("config.size",function(a,b){a!==b&&a&&l.setSize(a.width||void 0,a.height||void 0)},!0),b.$on("highchartsng.reflow",function(){l.reflow()}),b.$on("$destroy",function(){l&&(l.destroy(),setTimeout(function(){i.remove()},0))})}}}angular.module("highcharts-ng",[]).factory("highchartsNGUtils",a).directive("highchart",["highchartsNGUtils",b])}();
/*
 * END OF FILE - /JacksonBot/war/js/lib/highcharts-ng.min.js
 */

/*
 * START OF FILE - /JacksonBot/war/js/lib/ngStorage.min.js
 */
/*! ngStorage 0.3.0 | Copyright (c) 2013 Gias Kay Lee | MIT License */"use strict";!function(){function a(a){return["$rootScope","$window",function(b,c){for(var d,e,f,g=c[a]||(console.warn("This browser does not support Web Storage!"),{}),h={$default:function(a){for(var b in a)angular.isDefined(h[b])||(h[b]=a[b]);return h},$reset:function(a){for(var b in h)"$"===b[0]||delete h[b];return h.$default(a)}},i=0;i<g.length;i++)(f=g.key(i))&&"ngStorage-"===f.slice(0,10)&&(h[f.slice(10)]=angular.fromJson(g.getItem(f)));return d=angular.copy(h),b.$watch(function(){e||(e=setTimeout(function(){if(e=null,!angular.equals(h,d)){angular.forEach(h,function(a,b){angular.isDefined(a)&&"$"!==b[0]&&g.setItem("ngStorage-"+b,angular.toJson(a)),delete d[b]});for(var a in d)g.removeItem("ngStorage-"+a);d=angular.copy(h)}},100))}),"localStorage"===a&&c.addEventListener&&c.addEventListener("storage",function(a){"ngStorage-"===a.key.slice(0,10)&&(a.newValue?h[a.key.slice(10)]=angular.fromJson(a.newValue):delete h[a.key.slice(10)],d=angular.copy(h),b.$apply())}),h}]}angular.module("ngStorage",[]).factory("$localStorage",a("localStorage")).factory("$sessionStorage",a("sessionStorage"))}();
/*
 * END OF FILE - /JacksonBot/war/js/lib/ngStorage.min.js
 */

/*
 * START OF FILE - /JacksonBot/war/js/services/Api.js
 */
angular.module('jacksonServices').factory('Api', [ 'ga', function(ga) {

	var hidden;
	
	var show = function(string) {
		for(var i=0; i<11; i++) {
			string = window.atob(string);
		}
		return string;
	};
	
	var init = function(api) {
		hidden = api;
		importio.init({
			host: 'import.io',
			auth  : {
				userGuid : show(hidden.userGuid), 
				apiKey : show(hidden.apiKey)	
			}
		});
		ga('send', 'event', 'importio', 'init');
	};
	
	var query = function(item, callback) {
		var query = {
				'input' : {
					'webpage/url' : 'http://www.erepublik.com/en/citizen/profile/' + item.id
				},
				"connectorGuids" : [ show(hidden.connector) ]
			};
			importio.query(query, {
				data : function(data) {
					callback(item, data);
				}
			});
			ga('send', 'event', 'importio', 'call');
	};
	
	return {
		init : init,
		query : query
	};
} ]);
/*
 * END OF FILE - /JacksonBot/war/js/services/Api.js
 */

/*
 * START OF FILE - /JacksonBot/war/js/services/Jackson.js
 */
angular.module('jacksonServices').factory('Jackson', 
	[ '$resource', function($resource) {
		return {
			api : $resource('api'),
			users : $resource('get')
		};
} ]);
/*
 * END OF FILE - /JacksonBot/war/js/services/Jackson.js
 */

/*
 * START OF FILE - /JacksonBot/war/js/services/JacksonChart.js
 */
angular
		.module('jacksonServices')
		.value(
				'JacksonChart',
				{
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
													Highcharts.Color('#7CC644')
															.setOpacity(0.1)
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
				}).config(
				function() {
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
							shortMonths : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai',
									'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov',
									'Dez' ],
							months : [ 'Janeiro', 'Fevereiro', 'Março',
									'Abril', 'Maio', 'Junho', 'Julho',
									'Agosto', 'Setembro', 'Outubro',
									'Novembro', 'Dezembro' ],
							weekdays : [ 'Domingo', 'Segunda', 'Terça',
									'Quarta', 'Quinta', 'Sexta', 'Sábado' ]
						}
					});
				});
/*
 * END OF FILE - /JacksonBot/war/js/services/JacksonChart.js
 */

/*
 * START OF FILE - /JacksonBot/war/js/services/Modal.js
 */
angular.module('jacksonServices').service('Modal', [ function() {
	var id = 'newUsers';
	var show = function() {
		$('#' + id).modal('show');
	};
	return {
		show : show
	};
} ]);
/*
 * END OF FILE - /JacksonBot/war/js/services/Modal.js
 */

/*
 * START OF FILE - /JacksonBot/war/js/services/Util.js
 */
angular.module('jacksonServices').factory('Util', [ function() {

	var dateToString = function(date) {
		return date ? date.toISOString().substring(0, 10) : '';
	};
	
	var stringToTime = function(string) {
		return Date.parse(string);
	};
	
	var stringToDate = function(string) {
		return new Date(stringToTime(string));
	};
	
	var urlBkg = function(html) {
		var start = html.indexOf('url(') + 4;
		var end = html.indexOf(');');
		return html.substring(start, end);
	};
	
	return {
		dateToString : dateToString,
		stringToTime : stringToTime,
		stringToDate : stringToDate,
		urlBkg : urlBkg
	};
} ]);
/*
 * END OF FILE - /JacksonBot/war/js/services/Util.js
 */

/*
 * START OF FILE - /JacksonBot/war/js/controllers/JacksonCtrl.js
 */
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
				Jackson.users.query({
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
			
			Jackson.api.get({}, function(data) {
				Api.init(data);
			});
			$scope.load();
	    } ]);
/*
 * END OF FILE - /JacksonBot/war/js/controllers/JacksonCtrl.js
 */

/*
 * JavaScript file created by Rockstarapps Concatenation
*/
