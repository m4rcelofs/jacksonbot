var JacksonChart = new function() {

	Highcharts.setOptions({
		lang : {
			loading : 'Carregando...',
			// noData : 'Sem dados para exibir ):',
			noData : '',
			printChart : 'Imprimir gráfico',
			downloadJPEG : 'Salvar como JPEG',
			downloadPDF : 'Salvar como PDF',
			downloadPNG : 'Salvar como PNG',
			downloadSVG : 'Salvar como SVG',
			resetZoom : 'Resetar zoom',
			resetZoomTitle : 'Voltar para o zoom inicial'
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
			text : 'Selecione uma área para dar zoom'
		},
		xAxis : {
			type : 'datetime',
			minRange : 5 * 24 * 3600000, // 5d
			title : {
				text : 'Data'
			}
		},
		yAxis : {
			title : {
				text : 'Número de jogadores'
			}
		},
		legend : {
			enabled : false
		},
		plotOptions : {
			area : {
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
			data : []
		} ]
	};

	self.chart = undefined;
	self.start = function(element) {
		element.highcharts(self.chartOptions);
		self.chart = element.highcharts();
	};

	self.cache = {};
	self.loadFrom = function(date) {
		self.chart.series[0].setData([], true);
		self.chart.showLoading();
		$.getJSON("/get?from=" + date, function(data) {
			var newData = [];
			data.forEach(function(item) {
				newData.push([ Date.parse(item.day), item.newUsers.length ]);
				self.cache[item.day] = item.newUsers;
			});
			self.chart.series[0].setData(newData, true);
			self.chart.hideLoading();
		});
	};
};