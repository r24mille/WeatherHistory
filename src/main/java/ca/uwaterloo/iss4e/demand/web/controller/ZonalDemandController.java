package ca.uwaterloo.iss4e.demand.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.google.gson.Gson;

import ca.uwaterloo.iss4e.demand.dao.ieso.ZonalDemandAndWeatherDAO;
import ca.uwaterloo.iss4e.demand.dao.ieso.ZonalDemandSummaryDAO;
import ca.uwaterloo.iss4e.demand.model.ieso.TransmissionZone;
import ca.uwaterloo.iss4e.demand.model.ieso.ZonalDemand;
import ca.uwaterloo.iss4e.demand.model.ieso.ZonalDemandAndWeather;
import ca.uwaterloo.iss4e.demand.model.ieso.ZonalDemandSummary;
import ca.uwaterloo.iss4e.demand.web.command.ZonalDemandCommand;

@Controller
@RequestMapping("/zone/{zoneString}/year/{yearString}/**")
public class ZonalDemandController implements ApplicationContextAware {
	Logger logger = LogManager.getLogger(this.getClass());
	private ApplicationContext applicationContext;

	@RequestMapping("/html")
	public String html(@ModelAttribute ZonalDemandCommand command,
			@PathVariable String zoneString, @PathVariable String yearString,
			Model model) {
		logger.debug("html hit");
		model.addAttribute("zoneString", zoneString);
		model.addAttribute("yearString", yearString);
		model.addAttribute("command", command);
		return "zonal";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public @ResponseBody
	List<ZonalDemandAndWeather> zoneJson(@PathVariable String zoneString,
			@PathVariable String yearString) {
		ZonalDemandAndWeatherDAO zonalDemandAndWeatherDAO = (ZonalDemandAndWeatherDAO) applicationContext
				.getBean("zonalDemandAndWeatherDAO");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TransmissionZone transmissionZone = TransmissionZone.valueOf(zoneString
				.toUpperCase());

		List<ZonalDemandAndWeather> zonalDemandAndWeathers = null;
		try {
			Date startDate = sdf.parse(yearString + "-01-01 00:00:00");
			Date endDate = sdf.parse(yearString + "-12-31 23:59:59");
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
