/**
 * Initialize the one-time run elements of the D3 chart
 */
var xAxis, yAxis, pointCategory;
var xAxisOptions, dayFilterOptions, seasonFilterOptions, rateFilterOptions;
var _selectedFilters = new Array();
var _categoryOptions = new Array();
var _categoryValueMap = new Array();
var _descriptions = new Array();
var _zone, _data, svg, _color;

function getBounds(d, paddingFactor) {
	// Find min and maxes (for the scales)
	paddingFactor = typeof paddingFactor !== 'undefined' ? paddingFactor : 1;

	var keys = _.keys(d[0]), b = {};
	_.each(keys, function(k) {
		b[k] = {};
		_.each(d, function(d) {
			if (isNaN(d[k]))
				return;
			if (b[k].min === undefined || d[k] < b[k].min)
				b[k].min = d[k];
			if (b[k].max === undefined || d[k] > b[k].max)
				b[k].max = d[k];
		});
		b[k].max > 0 ? b[k].max *= paddingFactor : b[k].max /= paddingFactor;
		b[k].min > 0 ? b[k].min /= paddingFactor : b[k].min *= paddingFactor;
	});
	return b;
}

/**
 * Initialize the JQuery-UI slider
 */
function initSlider(zone) {
	$("#year-slider").slider({
		range : "min",
		value : 2013,
		min : 2004,
		max : 2013,
		slide : function(event, ui) {
			$(".demand-year").text(ui.value);
			jsonUrl = zonalJsonUrl(zone, ui.value);
			chartJSON(jsonUrl);
		}
	});
	$(".demand-year").text($("#year-slider").slider("value"));
}

function initLdcChart() {
	// Time-of-Use billing active yes or no categories
	touBillingActiveOptions = [ true, false ];
	_categoryValueMap["touBillingActive"] = touBillingActiveOptions;
	_descriptions["touBillingActive"] = "Time-of-Use billing active";
	_categoryOptions = [ "touBillingActive" ];

	_selectedFilters = touBillingActiveOptions;

	d3.select('#touactive-filter-menu').selectAll('li').data(
			touBillingActiveOptions).enter().append('li').text(function(d) {
		if (d) {
			return "Yes (Post-TOU)";
		} else {
			return "No (Pre-TOU)";
		}
	}).classed('selected', function(d) {
		return _.contains(_selectedFilters, d);
	}).on('click', function(d) {
		// Toggle filters
		if (_.contains(_selectedFilters, d)) {
			var filterIndex = _.indexOf(_selectedFilters, d);
			_selectedFilters.splice(filterIndex, 1);
		} else {
			_selectedFilters.push(d);
		}
		updateChart(_data);
		updateMenus();
	});

	// Generic init
	initChart();

	// Chart zonal data
	chartJSON("/WeatherHistory/ldc/json");
}

function initZonalChart(zone, year) {
	// Generic init
	initChart();

	// Chart zonal data
	jsonUrl = zonalJsonUrl(zone, year);
	chartJSON(jsonUrl);
}

function initChart() {
	// Define all available x-axis options and color categories
	xAxisOptions = [ "tempMetric", "wallHourNum" ];
	_categoryOptions = _categoryOptions.concat([ "timeOfUseRate",
			"timeOfUseSeason", "weekend", "dayOfWeek", "wallHourNum" ]);
	rateFilterOptions = [ "OFF_PEAK", "MID_PEAK", "ON_PEAK" ];
	seasonFilterOptions = [ "SUMMER", "WINTER" ];
	weekendFilterOptions = [ "true", "false" ];
	dayFilterOptions = [ "Sunday", "Monday", "Tuesday", "Wednesday",
			"Thursday", "Friday", "Saturday" ];
	hourFilterOptions = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
			16, 17, 18, 19, 20, 21, 22, 23 ];

	// Map all category values so that color/legend remains consistent
	_categoryValueMap["timeOfUseRate"] = rateFilterOptions;
	_categoryValueMap["timeOfUseSeason"] = seasonFilterOptions;
	_categoryValueMap["weekend"] = weekendFilterOptions;
	_categoryValueMap["dayOfWeek"] = dayFilterOptions;
	_categoryValueMap["wallHourNum"] = hourFilterOptions;

	// Initialize all filters to be selected
	_selectedFilters = _selectedFilters.concat(dayFilterOptions,
			hourFilterOptions, seasonFilterOptions, rateFilterOptions);
	// Verbose descriptions of axis and category options
	_descriptions["tempMetric"] = "Outdoor Temperature (Celsius)";
	_descriptions["wallHourNum"] = "Hour-of-Day (hours 0-23)";
	_descriptions["timeOfUseRate"] = "Time-of-Use daily price periods";
	_descriptions["timeOfUseSeason"] = "Time-of-Use seasons";
	_descriptions["weekend"] = "Weekend";
	_descriptions["dayOfWeek"] = "Day of week";
	_descriptions["wallHourNum"] = "Hour of day";
	_descriptions["demand"] = "Electricity Demand (MW)";
	_descriptions["true"] = "Yes";
	_descriptions["false"] = "No";
	_descriptions["ON_PEAK"] = "On-peak hours";
	_descriptions["OFF_PEAK"] = "Off-peak hours";
	_descriptions["MID_PEAK"] = "Mid-peak hours";
	_descriptions["SUMMER"] = "Summer";
	_descriptions["WINTER"] = "Winter";
	_descriptions["Sunday"] = "Sunday";
	_descriptions["Monday"] = "Monday";
	_descriptions["Tuesday"] = "Tuesday";
	_descriptions["Wednesday"] = "Wednesday";
	_descriptions["Thursday"] = "Thursday";
	_descriptions["Friday"] = "Friday";
	_descriptions["Saturday"] = "Saturday";
	_descriptions[0] = "00:00 - 01:00";
	_descriptions[1] = "01:00 - 02:00";
	_descriptions[2] = "02:00 - 03:00";
	_descriptions[3] = "03:00 - 04:00";
	_descriptions[4] = "04:00 - 05:00";
	_descriptions[5] = "05:00 - 06:00";
	_descriptions[6] = "06:00 - 07:00";
	_descriptions[7] = "07:00 - 08:00";
	_descriptions[8] = "08:00 - 09:00";
	_descriptions[9] = "09:00 - 10:00";
	_descriptions[10] = "10:00 - 11:00";
	_descriptions[11] = "11:00 - 12:00";
	_descriptions[12] = "12:00 - 13:00";
	_descriptions[13] = "13:00 - 14:00";
	_descriptions[14] = "14:00 - 15:00";
	_descriptions[15] = "15:00 - 16:00";
	_descriptions[16] = "16:00 - 17:00";
	_descriptions[17] = "17:00 - 18:00";
	_descriptions[18] = "18:00 - 19:00";
	_descriptions[19] = "19:00 - 20:00";
	_descriptions[20] = "20:00 - 21:00";
	_descriptions[21] = "21:00 - 22:00";
	_descriptions[22] = "22:00 - 23:00";
	_descriptions[23] = "23:00 - 00:00";

	// Set up default x-axis, y-axis, and color categories
	xAxis = xAxisOptions[0];
	yAxis = "demand";
	pointCategory = _categoryOptions[0];

	// Initialize all colors for category
	_color = d3.scale.category10();
	_.each(_categoryValueMap[pointCategory], function(d) {
		_color(d);
	});

	// Initialize the SVG chart object
	svg = d3.select("#chart").append("svg").attr("width", 1200).attr("height",
			700);
	svg.append('g').classed('chart', true).attr('transform',
			'translate(90, -100)');

	// Add toggle listeners
	$(".toggle").each(function() {
		$(this).click(function() {
			$(this).toggleClass("toggle_open");
			$(this).toggleClass("toggle_closed");
			$(this).next("ul").toggle("blind", 500);
		});
	});

	// Build menus
	d3.select('#x-axis-menu').selectAll('li').data(xAxisOptions).enter()
			.append('li').text(function(d) {
				return _descriptions[d];
			}).classed('selected', function(d) {
				return d === xAxis;
			}).on('click', function(d) {
				xAxis = d;
				d3.select('#xLabel').text(_descriptions[xAxis]);

				updateChart(_data);
				updateMenus();
			});

	d3.select('#category-menu').selectAll('li').data(_categoryOptions).enter()
			.append('li').text(function(d) {
				return _descriptions[d];
			}).classed('selected', function(d) {
				return d === pointCategory;
			}).on('click', function(d) {
				pointCategory = d;

				// Initialize all colors for category
				_color = d3.scale.category10();
				_.each(_categoryValueMap[pointCategory], function(d) {
					_color(d);
				});

				updateChart(_data);
				updateMenus();
				updateLegend();
			});

	d3.select('#day-filter-menu').selectAll('li').data(dayFilterOptions)
			.enter().append('li').text(function(d) {
				return d;
			}).classed('selected', function(d) {
				return _.contains(_selectedFilters, d);
			}).on('click', function(d) {
				// Toggle filters
				if (_.contains(_selectedFilters, d)) {
					var filterIndex = _.indexOf(_selectedFilters, d);
					_selectedFilters.splice(filterIndex, 1);
				} else {
					_selectedFilters.push(d);
				}
				updateChart(_data);
				updateMenus();
			});

	d3.select('#hour-filter-menu').selectAll('li').data(hourFilterOptions)
			.enter().append('li').text(function(d) {
				return _descriptions[d];
			}).classed('selected', function(d) {
				return _.contains(_selectedFilters, d);
			}).on('click', function(d) {
				// Toggle filters
				if (_.contains(_selectedFilters, d)) {
					var filterIndex = _.indexOf(_selectedFilters, d);
					_selectedFilters.splice(filterIndex, 1);
				} else {
					_selectedFilters.push(d);
				}
				updateChart(_data);
				updateMenus();
			});

	d3.select('#season-filter-menu').selectAll('li').data(seasonFilterOptions)
			.enter().append('li').text(function(d) {
				return _descriptions[d];
			}).classed('selected', function(d) {
				return _.contains(_selectedFilters, d);
			}).on('click', function(d) {
				// Toggle filters
				if (_.contains(_selectedFilters, d)) {
					var filterIndex = _.indexOf(_selectedFilters, d);
					_selectedFilters.splice(filterIndex, 1);
				} else {
					_selectedFilters.push(d);
				}
				updateChart(_data);
				updateMenus();
			});

	d3.select('#rate-filter-menu').selectAll('li').data(rateFilterOptions)
			.enter().append('li').text(function(d) {
				return _descriptions[d];
			}).classed('selected', function(d) {
				return _.contains(_selectedFilters, d);
			}).on('click', function(d) {
				// Toggle filters
				if (_.contains(_selectedFilters, d)) {
					var filterIndex = _.indexOf(_selectedFilters, d);
					_selectedFilters.splice(filterIndex, 1);
				} else {
					_selectedFilters.push(d);
				}
				updateChart(_data);
				updateMenus();
			});

	// Axis labels
	d3.select('svg g.chart').append('text').attr({
		'id' : 'xLabel',
		'x' : 400,
		'y' : 670,
		'text-anchor' : 'middle'
	}).text(_descriptions[xAxis]);
	d3.select('#xLabel').text(_descriptions[xAxis]);

	d3.select('svg g.chart').append('text').attr('transform',
			'translate(-60, 330)rotate(-90)').attr({
		'id' : 'yLabel',
		'text-anchor' : 'middle'
	}).text(yAxis);
	d3.select('#yLabel').text(_descriptions[yAxis]);
}

function chartJSON(jsonUrl) {
	// Make call for JSON data
	d3.json(jsonUrl).header("Cache-Control", "max-age=3600, min-fresh=0").get(
			function(error, data) {
				_data = data;
				chartData(data);
				updateChart(data);
				updateMenus();
				updateLegend();

				// Render axes
				d3.select('svg g.chart').append("g").attr('transform',
						'translate(0, 630)').attr('id', 'xAxis')
						.call(makeXAxis);

				d3.select('svg g.chart').append("g").attr('id', 'yAxis').attr(
						'transform', 'translate(-10, 0)').call(makeYAxis);

			});
}

function chartData(data) {
	// Render points
	updateScales(data);

	// Initialize all colors for category
	_color = d3.scale.category10();
	_.each(_categoryValueMap[pointCategory], function(d) {
		_color(d);
	});

	$("#xAxis").remove();
	$("#yAxis").remove();
	var circles = d3.select('svg g.chart').selectAll('circle').data(data);
	circles.enter().append('circle').attr('cx', function(d) {
		if (isNaN(d[xAxis])) {
			d3.select(this).attr('cx');
		} else {
			xScale(d[xAxis]);
		}
	}).attr('cy', function(d) {
		if (isNaN(d[yAxis])) {
			d3.select(this).attr('cy');
		} else {
			yScale(d[yAxis]);
		}
	}).attr('fill', function(d) {
		return _color(d[pointCategory]);
	});
	circles.exit().remove();
}

// RENDERING FUNCTIONS
function updateChart(data) {
	updateScales(data);
	d3.select('svg g.chart').selectAll('circle').transition().duration(1250)
			.ease('quad-out').attr('cx', function(d) {
				if (isNaN(d[xAxis])) {
					return d3.select(this).attr('cx');
				} else {
					return xScale(d[xAxis]);
				}
			}).attr('cy', function(d) {
				if (isNaN(d[yAxis])) {
					return d3.select(this).attr('cy');
				} else {
					return yScale(d[yAxis]);
				}
			}).attr(
					'r',
					function(d) {
						if (isNaN(d[xAxis])
								|| isNaN(d[yAxis])
								|| !_.contains(_selectedFilters,
										d["dayOfWeek"])
								|| !_.contains(_selectedFilters,
										d["timeOfUseSeason"])
								|| !_.contains(_selectedFilters,
										d["timeOfUseRate"])
								|| !_.contains(_selectedFilters,
										d["wallHourNum"])
								|| !_.contains(_selectedFilters,
										d["touBillingActive"])) {
							return 0;
						} else {
							return 1.75;
						}
					}).attr('fill', function(d) {
				return _color(d[pointCategory]);
			}).style("opacity", 0.5);
	// Removing date/MW title for now
	// .attr('title', function(d) {
	// return d.demand + ' MW at ' + new Date(d.dateDst);
	// });

	// Also update the axes
	d3.select('#xAxis').transition().call(makeXAxis);
	d3.select('#yAxis').transition().call(makeYAxis);
}

function updateLegend() {
	// Update legend
	svg.selectAll(".legend").data([]).exit().remove();
	var legend = svg.selectAll(".legend").data(_color.domain());

	legend.enter().append("g").attr("class", "legend").attr("transform",
			function(d, i) {
				return "translate(0," + i * 20 + ")";
			});

	legend.append("rect").attr("x", 1000 - 18).attr("width", 18).attr("height",
			18).style("fill", _color);

	legend.append("text").attr("x", 1000 - 24).attr("y", 9).attr("dy", ".35em")
			.style("text-anchor", "end").text(function(d) {
				return _descriptions[d];
			});
}

function updateScales(data) {
	var bounds = getBounds(data, 1);
	xScale = d3.scale.linear().domain([ bounds[xAxis].min, bounds[xAxis].max ])
			.range([ 0, 780 ]);

	yScale = d3.scale.linear().domain([ bounds[yAxis].min, bounds[yAxis].max ])
			.range([ 600, 100 ]);
}

function makeXAxis(s) {
	s.call(d3.svg.axis().scale(xScale).orient("bottom"));
}

function makeYAxis(s) {
	s.call(d3.svg.axis().scale(yScale).orient("left"));
}

// Update 'selected' class in menus
function updateMenus() {
	d3.select('#x-axis-menu').selectAll('li').classed('selected', function(d) {
		return d === xAxis;
	});
	d3.select('#category-menu').selectAll('li').classed('selected',
			function(d) {
				return d === pointCategory;
			});

	d3.select('#filter-menu').selectAll('li').classed('selected', function(d) {
		return _.contains(_selectedFilters, d);
	});
}

function zonalJsonUrl(zone, year) {
	_zone = zone;
	return "/WeatherHistory/zone/" + _zone + "/year/" + year + "/json";
}