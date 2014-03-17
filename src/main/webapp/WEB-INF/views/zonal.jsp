<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>${zoneString} Zone, ${year}</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">

<script src="/WeatherHistory/resources/js/vendor/d3/d3.min.js"></script>
<script src="/WeatherHistory/resources/js/vendor/underscore-1.6.0/underscore-min.js"></script>
<script src="/WeatherHistory/resources/js/interactive-degree-day.js"></script>
<link rel="stylesheet" href="/WeatherHistory/resources/css/main.css" type="text/css" />
</head>
<body>
	<div id="wrapper">
		<div id="title">
			<h1>${zoneString} Zone, ${year}</h1>
		</div>

		<div id="menu">
			<h3>X-Axis</h3>
			<ul id="x-axis-menu"></ul>
			<h3>Color Category</h3>
			<ul id="category-menu"></ul>
			<h3>Day Filters</h3>
			<ul id="day-filter-menu"></ul>
			<h3>Year</h3>
			<ul>
				<c:forEach items="${years}" var="loopYear">
					<li><a href="/WeatherHistory/zone/${zoneString}/year/${loopYear}/html">${loopYear}</a>
				</c:forEach>
			</ul>
			<h3>Zone</h3>
			<ul>
			<c:forEach items="${zoneStrings}" var="loopZone">
				<li><a href="/WeatherHistory/zone/${loopZone}/year/${year}/html">${loopZone}</a>
			</c:forEach>
			</ul>
		</div>
		
		<div id="chart"></div>
	</div>
	<script>
 	initDegreeDayChart("${zoneString}", "${year}");
// 		var margin = {
// 			top : 20,
// 			right : 20,
// 			bottom : 30,
// 			left : 40
// 		}, width = 960 - margin.left - margin.right, height = 500 - margin.top
// 				- margin.bottom;

// 		var x = d3.scale.linear().range([ 0, width ]);

// 		var y = d3.scale.linear().range([ height, 0 ]);

// 		var color = d3.scale.category10();

// 		var xAxis = d3.svg.axis().scale(x).orient("bottom");

// 		var yAxis = d3.svg.axis().scale(y).orient("left");

// 		var svg = d3.select("body").append("svg").attr("width",
// 				width + margin.left + margin.right).attr("height",
// 				height + margin.top + margin.bottom).append("g").attr(
// 				"transform",
// 				"translate(" + margin.left + "," + margin.top + ")");

// 		d3.json("/WeatherHistory/zone/${zoneString}/year/${yearString}/json",
// 				function(error, data) {
// 					data.forEach(function(d) {
// 						d.demand = +d.demand;
// 						d.tempMetric = +d.tempMetric;
// 					});

// 					x.domain(d3.extent(data, function(d) {
// 						return d.tempMetric;
// 					})).nice();
// 					y.domain(d3.extent(data, function(d) {
// 						return d.demand;
// 					})).nice();

// 					svg.append("g").attr("class", "x axis").attr("transform",
// 							"translate(0," + height + ")").call(xAxis).append(
// 							"text").attr("class", "label").attr("x", width)
// 							.attr("y", -6).style("text-anchor", "end").text(
// 									"Hour of Day");

// 					svg.append("g").attr("class", "y axis").call(yAxis).append(
// 							"text").attr("class", "label").attr("transform",
// 							"rotate(-90)").attr("y", 6).attr("dy", ".71em")
// 							.style("text-anchor", "end").text("Demand (MW)")

// 					svg.selectAll(".dot").data(data).enter().append("circle")
// 							.attr("class", "dot").attr("r", 2).attr("cx",
// 									function(d) {
// 										return x(d.tempMetric);
// 									}).attr("cy", function(d) {
// 								return y(d.demand);
// 							}).style("fill", function(d) {
// 								return color(d.timeOfUseRate);
// 							});

// 					var legend = svg.selectAll(".legend").data(color.domain())
// 							.enter().append("g").attr("class", "legend").attr(
// 									"transform", function(d, i) {
// 										return "translate(0," + i * 20 + ")";
// 									});

// 					legend.append("rect").attr("x", width - 18).attr("width",
// 							18).attr("height", 18).style("fill", color);

// 					legend.append("text").attr("x", width - 24).attr("y", 9)
// 							.attr("dy", ".35em").style("text-anchor", "end")
// 							.text(function(d) {
// 								return d;
// 							});

// 				});
	</script>
</body>
</html>