/*
 * JavaScript file created by Rockstarapps Concatenation
*/

/*
 * START OF FILE - /JacksonBot/war/JacksonChart.js
 */
var JacksonChart = new function() {

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
			shortMonths : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul',
					'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
			months : [ 'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio',
					'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro',
					'Novembro', 'Dezembro' ],
			weekdays : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta',
					'Sexta', 'Sábado' ]
		}
	});

	var self = this;

	self.chartOptions = {
		chart : {
			zoomType : 'x'
		},
		credits : {
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
		legend : {
			enabled : false
		},
		plotOptions : {
			area : {
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
			}
		},
		series : [ {
			type : 'area',
			name : 'Novos jogadores no eBrasil',
			color : '#2CA81F',
			data : [ [ new Date(), 8 ] ]
		} ]
	};

	self.chart = undefined;
	self.start = function(element, onPointClick) {
		if (onPointClick) {
			self.chartOptions.plotOptions.series = {
				cursor : 'pointer',
				point : {
					events : {
						click : onPointClick
					}
				}
			};
		}
		element.highcharts(self.chartOptions);
		self.chart = element.highcharts();
	};

	self.cache = {};
	self.loadFrom = function(date) {
		self.chart.showLoading();
		$.getJSON("/get?from=" + date, function(data) {
			var newData = [];
			data.forEach(function(item) {
				newData.push([ self.stringToDate(item.day), item.newUsers.length ]);
				self.cache[item.day] = item.newUsers;
			});
			self.chart.series[0].setData(newData, true);
			self.chart.hideLoading();
		});
	};
	
	self.dateToString = function(date) {
		return date.toISOString().substring(0,10);
	};
	
	self.stringToDate = function(string) {
		return Date.parse(string);
	}
};
/*
 * END OF FILE - /JacksonBot/war/JacksonChart.js
 */

/*
 * START OF FILE - /JacksonBot/war/index.js
 */
$(document).ready(function() {
	JacksonChart.start($('#chart'), chartCallback);
	JacksonChart.loadFrom('');

	var max = new Date();
	max.setDate(max.getDate() - 1);
	$('#date').attr('max', JacksonChart.dateToString(max));

	$('#dateForm').submit(function(event) {
		event.preventDefault();
		JacksonChart.loadFrom($('#date').val());
	});
});

var chartCallback = function() {
	// this = http://api.highcharts.com/highcharts#Point
	var key = JacksonChart.dateToString(new Date(this.x));
	var newUsers = JacksonChart.cache[key];
	console.log(newUsers);

	var row = $('#userIdsRow');
	row.empty();
	newUsers.forEach(function(item) {
		var link = $('<a>', {
			href : 'http://www.erepublik.com/en/citizen/profile/' + item,
			target : '_blank',
			text : item
		});
		var div = $('<div>', {
			'class' : 'col-md-2'
		});
		div.append(link);
		row.append(div);
	});

	$('#selectedDay').text(key);
	$('#selectedLength').text(newUsers.length);
	$('#newUsers').modal('toggle');
};
/*
 * END OF FILE - /JacksonBot/war/index.js
 */

/*
 * JavaScript file created by Rockstarapps Concatenation
*/
