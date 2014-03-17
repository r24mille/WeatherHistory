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

function initDegreeDayChart(zone, year) {
	// Set up default x-axis, y-axis, and color categories
	var xAxis = "tempMetric", yAxis = "demand", pointCategory = "timeOfUseRate";

	// Define all available x-axis options and color categories
	var xAxisOptions = [ "tempMetric", "wallHourNum" ];
	var categoryOptions = [ "timeOfUseRate", "timeOfUseSeason", "weekend",
			"dayOfWeek" ];
	var dayFilterOptions = [ "Sunday", "Monday", "Tuesday", "Wednesday",
			"Thursday", "Friday", "Saturday" ];

	// Initialize all filters to be selected
	var selectedDayFilters = dayFilterOptions.slice(0);

	// Verbose descriptions of axis and category options
	var descriptions = {
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

	d3.json("/WeatherHistory/zone/" + zone + "/year/" + year + "/json",
			function(data) {
				var keys = _.keys(data[0]);
				var bounds = getBounds(data, 1);

				// SVG AND D3 STUFF
				var svg = d3.select("#chart").append("svg").attr("width", 1200)
						.attr("height", 700);
				var xScale, yScale;

				svg.append('g').classed('chart', true).attr('transform',
						'translate(80, -60)');

				// Build menus
				d3.select('#x-axis-menu').selectAll('li').data(xAxisOptions)
						.enter().append('li').text(function(d) {
							return descriptions[d];
						}).classed('selected', function(d) {
							return d === xAxis;
						}).on('click', function(d) {
							xAxis = d;
							updateChart();
							updateMenus();
						});

				d3.select('#category-menu').selectAll('li').data(
						categoryOptions).enter().append('li').text(function(d) {
					return descriptions[d];
				}).classed('selected', function(d) {
					return d === pointCategory;
				}).on('click', function(d) {
					pointCategory = d;
					updateChart();
					updateMenus();
				});

				d3.select('#day-filter-menu').selectAll('li').data(
						dayFilterOptions).enter().append('li').text(
						function(d) {
							return d;
						}).classed('selected', function(d) {
					return _.contains(selectedDayFilters, d);
				}).on('click', function(d) {
					// Toggle filters
					if (_.contains(selectedDayFilters, d)) {
						var dayIndex = _.indexOf(selectedDayFilters, d);
						selectedDayFilters.splice(dayIndex, 1);
					} else {
						selectedDayFilters.push(d);
					}
					updateChart();
					updateMenus();
				});

				// Axis labels
				d3.select('svg g.chart').append('text').attr({
					'id' : 'xLabel',
					'x' : 400,
					'y' : 670,
					'text-anchor' : 'middle'
				}).text(descriptions[xAxis]);

				d3.select('svg g.chart').append('text').attr('transform',
						'translate(-60, 330)rotate(-90)').attr({
					'id' : 'yLabel',
					'text-anchor' : 'middle'
				}).text(yAxis);

				// Render points
				updateScales();
				var color = d3.scale.category10();
				d3.select('svg g.chart').selectAll('circle').data(data).enter()
						.append('circle').attr(
								'cx',
								function(d) {
									return isNaN(d[xAxis]) ? d3.select(this)
											.attr('cx') : xScale(d[xAxis]);
								}).attr(
								'cy',
								function(d) {
									return isNaN(d[yAxis]) ? d3.select(this)
											.attr('cy') : yScale(d[yAxis]);
								}).attr('fill', function(d) {
							return color(d[pointCategory]);
						});

				updateChart();
				updateMenus();

				// Render axes
				d3.select('svg g.chart').append("g").attr('transform',
						'translate(0, 630)').attr('id', 'xAxis')
						.call(makeXAxis);

				d3.select('svg g.chart').append("g").attr('id', 'yAxis').attr(
						'transform', 'translate(-10, 0)').call(makeYAxis);

				// RENDERING FUNCTIONS
				function updateChart() {
					var color = d3.scale.category10(); // Reset colors
					
					updateScales();
					d3.select('svg g.chart').selectAll('circle').transition()
							.duration(1500).ease('quad-out').attr('cx',
									function(d) {
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
												|| !_.contains(
														selectedDayFilters,
														d["dayOfWeek"])) {
											return 0;
										} else {
											return 2;
										}
									}).attr('fill', function(d) {
								return color(d[pointCategory]);
							});

					// Also update the axes
					d3.select('#xAxis').transition().call(makeXAxis);
					d3.select('#yAxis').transition().call(makeYAxis);

					// Update axis labels
					d3.select('#xLabel').text(descriptions[xAxis]);
					d3.select('#yLabel').text(descriptions[yAxis]);

					// Update legend
					svg.selectAll(".legend").data([]).exit().remove();
					var legend = svg.selectAll(".legend").data(color.domain());
										
					legend.enter().append("g").transition()
				      .duration(0).attr("class", "legend").attr(
									"transform", function(d, i) {
										return "translate(0," + i * 20 + ")";
									});

					legend.append("rect").transition()
				      .duration(0).attr("x", 1000 - 18)
							.attr("width", 18).attr("height", 18).style("fill",
									color);

					legend.append("text").transition()
				      .duration(0).attr("x", 1000 - 24).attr("y", 9)
							.attr("dy", ".35em").style("text-anchor", "end")
							.text(function(d) {
								return descriptions[d];
							});
				}

				function updateScales() {
					xScale = d3.scale.linear().domain(
							[ bounds[xAxis].min, bounds[xAxis].max ]).range(
							[ 20, 780 ]);

					yScale = d3.scale.linear().domain(
							[ bounds[yAxis].min, bounds[yAxis].max ]).range(
							[ 600, 100 ]);
				}

				function makeXAxis(s) {
					s.call(d3.svg.axis().scale(xScale).orient("bottom"));
				}

				function makeYAxis(s) {
					s.call(d3.svg.axis().scale(yScale).orient("left"));
				}
				
				function updateLegend() {
					
				}

				// Update 'selected' class in menus
				function updateMenus() {
					d3.select('#x-axis-menu').selectAll('li').classed(
							'selected', function(d) {
								return d === xAxis;
							});
					d3.select('#category-menu').selectAll('li').classed(
							'selected', function(d) {
								return d === pointCategory;
							});

					d3.select('#day-filter-menu').selectAll('li').classed(
							'selected', function(d) {
								return _.contains(selectedDayFilters, d);
							});
				}

			});
}