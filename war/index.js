var chartOptions =  {
        chart: {
            zoomType: 'x'
        },
        credits: {
        	enabled: false
        },
        title: {
            text: 'Novos jogadores no eBrasil'
        },
        subtitle: {
            text: 'Selecione uma área para dar zoom'
        },
        xAxis: {
            type: 'datetime',
            minRange: 5 * 24 * 3600000, // 5d
            title: {
                text: 'Data'
            }
        },
        yAxis: {
            title: {
                text: 'Número de jogadores'
            }
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            area: {
                fillColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                    stops: [
                        [0, '#7CC644'],
                        [1, Highcharts.Color('#7CC644').setOpacity(0.1).get('rgba')]
                    ]
                },
                marker: {
                    radius: 2
                },
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            }
        },
        series: [{
            type: 'area',
            name: 'Novos jogadores no eBrasil',
            color: '#2CA81F',
            data: []
        }]
    };

var cache = {};

$(document).ready(function () {
	chartOptions.series[0].data = [];
	$.getJSON( "/get?from=", function(data) {
		data.forEach(function(item){
			var dateArray = item.day.split('-');
			//months are 0-based
			var date = Date.UTC(dateArray[0], dateArray[1] - 1, dateArray[2]);
			chartOptions.series[0].data.push([date, item.newUsers.length]);	
			cache[item.day] = item.newUsers;
		});
		$('#chart').highcharts(chartOptions);
	});
});