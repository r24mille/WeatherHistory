/**
 * Initialize the one-time run elements of the D3 chart
 */
var xAxis, yAxis, pointCategory;
var xAxisOptions, categoryOptions, dayFilterOptions, seasonFilterOptions, rateFilterOptions;
var selectedFilters;
var descriptions;
var _data, svg, _color;

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
			chartJSON(zone, ui.value);
		}
	});
	$(".demand-year").text($("#year-slider").slider("value"));
}

function initChart(zone, year) {
	// Set up default x-axis, y-axis, and color categories
	xAxis = "tempMetric";
	yAxis = "demand";
	pointCategory = "timeOfUseRate";

	// Define all available x-axis options and color categories
	xAxisOptions = [ "tempMetric", "wallHourNum" ];
	categoryOptions = [ "timeOfUseRate", "timeOfUseSeason", "weekend",
			"dayOfWeek" ];
	dayFilterOptions = [ "Sunday", "Monday", "Tuesday", "Wednesday",
			"Thursday", "Friday", "Saturday" ];
	seasonFilterOptions = [ "SUMMER", "WINTER" ];
	rateFilterOptions = [ "OFF_PEAK", "MID_PEAK", "ON_PEAK" ];

	// Initialize all filters to be selected
	selectedFilters = [].concat(dayFilterOptions, seasonFilterOptions,
			rateFilterOptions);
	// Verbose descriptions of axis and category options
	descriptions = {
		"tempMetric" : "Outdoor Temperature (Celsius)",
		"wallHourNum" : "Hour-of-Day (hours 0-23)",
		"timeOfUseRate" : "Time-of-Use daily price periods",
		"timeOfUseSeason" : "Time-of-Use seasons",
		"weekend" : "Weekend",
		"dayOfWeek" : "Day of the week",
		"demand" : "Electricity Demand (MW)",
		"true" : "Weekend",
		"false" : "Weekday",
		"ON_PEAK" : "On-peak hours",
		"OFF_PEAK" : "Off-peak hours",
		"MID_PEAK" : "Mid-peak hours",
		"SUMMER" : "Summer",
		"WINTER" : "Winter",
		"Sunday" : "Sunday",
		"Monday" : "Monday",
		"Tuesday" : "Tuesday",
		"Wednesday" : "Wednesday",
		"Thursday" : "Thursday",
		"Friday" : "Friday",
		"Saturday" : "Saturday"
	};

	_color = d3.scale.category10();

	// Initialize the SVG chart object
	svg = d3.select("#chart").append("svg").attr("width", 1200).attr("height",
			700);
	svg.append('g').classed('chart', true).attr('transform',
			'translate(90, -100)');

	// Build menus
	d3.select('#x-axis-menu').selectAll('li').data(xAxisOptions).enter()
			.append('li').text(function(d) {
				return descriptions[d];
			}).classed('selected', function(d) {
				return d === xAxis;
			}).on('click', function(d) {
				xAxis = d;
				d3.select('#xLabel').text(descriptions[xAxis]);

				updateChart(_data);
				updateMenus();
			});

	d3.select('#category-menu').selectAll('li').data(categoryOptions).enter()
			.append('li').text(function(d) {
				return descriptions[d];
			}).classed('selected', function(d) {
				return d === pointCategory;
			}).on('click', function(d) {
				pointCategory = d;
				_color = d3.scale.category10();
				updateChart(_data);
				updateMenus();
				updateLegend();
			});

	d3.select('#day-filter-menu').selectAll('li').data(dayFilterOptions)
			.enter().append('li').text(function(d) {
				return d;
			}).classed('selected', function(d) {
				return _.contains(selectedFilters, d);
			}).on('click', function(d) {
				// Toggle filters
				if (_.contains(selectedFilters, d)) {
					var filterIndex = _.indexOf(selectedFilters, d);
					selectedFilters.splice(filterIndex, 1);
				} else {
					selectedFilters.push(d);
				}
				updateChart(_data);
				updateMenus();
			});

	d3.select('#season-filter-menu').selectAll('li').data(seasonFilterOptions)
			.enter().append('li').text(function(d) {
				return descriptions[d];
			}).classed('selected', function(d) {
				return _.contains(selectedFilters, d);
			}).on('click', function(d) {
				// Toggle filters
				if (_.contains(selectedFilters, d)) {
					var filterIndex = _.indexOf(selectedFilters, d);
					selectedFilters.splice(filterIndex, 1);
				} else {
					selectedFilters.push(d);
				}
				updateChart(_data);
				updateMenus();
			});

	d3.select('#rate-filter-menu').selectAll('li').data(rateFilterOptions)
			.enter().append('li').text(function(d) {
				return descriptions[d];
			}).classed('selected', function(d) {
				return _.contains(selectedFilters, d);
			}).on('click', function(d) {
				// Toggle filters
				if (_.contains(selectedFilters, d)) {
					var filterIndex = _.indexOf(selectedFilters, d);
					selectedFilters.splice(filterIndex, 1);
				} else {
					selectedFilters.push(d);
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
	}).text(descriptions[xAxis]);
	d3.select('#xLabel').text(descriptions[xAxis]);

	d3.select('svg g.chart').append('text').attr('transform',
			'translate(-60, 330)rotate(-90)').attr({
		'id' : 'yLabel',
		'text-anchor' : 'middle'
	}).text(yAxis);
	d3.select('#yLabel').text(descriptions[yAxis]);

	// Render points
	chartJSON(zone, year);
}

function chartJSON(zone, year) {
	// Make call for JSON data
	d3.json("/WeatherHistory/zone/" + zone + "/year/" + year + "/json",
			function(data) {
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
	_color = d3.scale.category10();
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
								|| !_.contains(selectedFilters, d["dayOfWeek"])
								|| !_.contains(selectedFilters,
										d["timeOfUseSeason"])
								|| !_.contains(selectedFilters,
										d["timeOfUseRate"])) {
							return 0;
						} else {
							return 2;
						}
					}).attr('fill', function(d) {
				return _color(d[pointCategory]);
			});

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
				return descriptions[d];
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
		return _.contains(selectedFilters, d);
	});
}