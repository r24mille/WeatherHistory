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
			<div id="filter-menu">
				<h3>Day Filters</h3>
				<ul id="day-filter-menu">
				</ul>
				<h3>Season Filters</h3>
				<ul id="season-filter-menu">
				</ul>
				<h3>Rate Filters</h3>
				<ul id="rate-filter-menu">
				</ul>
			</div>
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
	</script>
</body>
</html>