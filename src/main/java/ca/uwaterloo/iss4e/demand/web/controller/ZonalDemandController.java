package ca.uwaterloo.iss4e.demand.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.uwaterloo.iss4e.demand.dao.ieso.ZonalDemandAndWeatherDAO;
import ca.uwaterloo.iss4e.demand.model.ieso.TransmissionZone;
import ca.uwaterloo.iss4e.demand.model.ieso.ZonalDemandAndWeather;
import ca.uwaterloo.iss4e.demand.web.command.ZonalDemandCommand;

@Controller
@RequestMapping("/zone/{zoneString}/year/{year}/**")
public class ZonalDemandController implements ApplicationContextAware {
	Logger logger = LogManager.getLogger(this.getClass());
	private ApplicationContext applicationContext;

	@RequestMapping("/html")
	public String html(@ModelAttribute ZonalDemandCommand command,
			@PathVariable String zoneString, @PathVariable Integer year,
			Model model) {
		List<Integer> years = new ArrayList<Integer>(11);
		for (int i = 2003; i <= 2013; i++) {
			years.add(i);
		}
		model.addAttribute("years", years);
		
		List<String> zoneStrings = new ArrayList<String>(10);
		zoneStrings.add("Bruce");
		zoneStrings.add("East");
		zoneStrings.add("East");
		zoneStrings.add("Niagara");
		zoneStrings.add("Northeast");
		zoneStrings.add("Northwest");
		zoneStrings.add("Ottawa");
		zoneStrings.add("Southwest");
		zoneStrings.add("Toronto");
		zoneStrings.add("West");
		model.addAttribute("zoneStrings", zoneStrings);
		
		model.addAttribute("year", year);
		model.addAttribute("zoneString", zoneString);
		model.addAttribute("command", command);
		return "zonal";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public @ResponseBody
	List<ZonalDemandAndWeather> zoneJson(@PathVariable String zoneString,
			@PathVariable Integer year) {
		ZonalDemandAndWeatherDAO zonalDemandAndWeatherDAO = (ZonalDemandAndWeatherDAO) applicationContext
				.getBean("zonalDemandAndWeatherDAO");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TransmissionZone transmissionZone = TransmissionZone.valueOf(zoneString
				.toUpperCase());

		List<ZonalDemandAndWeather> zonalDemandAndWeathers = null;
		try {
			Date startDate = sdf.parse(year + "-01-01 00:00:00");
			Date endDate = sdf.parse(year + "-12-31 23:59:59");
			zonalDemandAndWeathers = zonalDemandAndWeatherDAO
					.getZonalDemandAndWeather(transmissionZone, startDate,
							endDate);
			logger.debug("zonalDemandAndWeathers size="
					+ zonalDemandAndWeathers.size());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return zonalDemandAndWeathers;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
