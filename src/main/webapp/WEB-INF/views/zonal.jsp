<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Transmission Zone Demand</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">

<script src="/WeatherHistory/resources/js/vendor/d3/d3.min.js"></script>
<script
	src="/WeatherHistory/resources/js/vendor/underscore-1.6.0/underscore-min.js"></script>
<script src="/WeatherHistory/resources/js/interactive-degree-day.js"></script>
<link rel="stylesheet" href="/WeatherHistory/resources/css/main.css"
	type="text/css" />
<link rel="stylesheet"
	href="/WeatherHistory/resources/css/vendor/ui-lightness/jquery-ui-1.10.4.custom.min.css">

<script
	src="/WeatherHistory/resources/js/vendor/jquery-ui-1.10.4.custom/jquery-1.10.2.js"></script>
<script
	src="/WeatherHistory/resources/js/vendor/jquery-ui-1.10.4.custom/jquery-ui-1.10.4.custom.min.js"></script>
</head>
<body>
	<div id="wrapper">
		<div id="title">
			<h1>
				<span id="demand-zone">${zoneString}</span> Zone, <span
					class="demand-year"></span>
			</h1>
		</div>

		<div id="menu">
			<h3>Independent Variable</h3>
			<ul id="x-axis-menu"></ul>
			<h3>Color Category</h3>
			<ul id="category-menu"></ul>
			<div id="filter-menu">
				<h3>Filter Days</h3>
				<ul id="day-filter-menu">
				</ul>
				<h3>Filter Hours</h3>
				<ul id="hour-filter-menu">
				</ul>
				<h3>Filter Seasons</h3>
				<ul id="season-filter-menu">
				</ul>
				<h3>Filter Rates</h3>
				<ul id="rate-filter-menu">
				</ul>
			</div>
			<h3>Transmission Zone</h3>
			<ul id="zone-menu">
				<c:forEach items="${zoneStrings}" var="loopZone">
					<c:choose>
						<c:when test="${fn:toLowerCase(loopZone) == fn:toLowerCase(zoneString)}">
							<li class="selected">${loopZone}</li>
						</c:when>
						<c:otherwise>
							<li>${loopZone}</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
			<script>
				$("#zone-menu li").click(
						function() {
							$("#zone-menu li").removeClass("selected");
							$("#demand-zone").text($(this).text());
							chartJSON($(this).text(), $("#year-slider").slider(
									"value"));
							$(this).addClass("selected");
						});
			</script>
		</div>

		<div id="chart"></div>
		<h2>
			Pick a year: <span class="demand-year"></span>
		</h2>
		<div id="year-slider"></div>
	</div>
	<script>
		initSlider("${zoneString}");
		initChart("${zoneString}", 2013);
	</script>
</body>
</html>