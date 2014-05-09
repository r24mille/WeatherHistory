package ca.uwaterloo.iss4e.demand.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.uwaterloo.iss4e.demand.dao.ldc.EpcDemandAndWeatherDAO;
import ca.uwaterloo.iss4e.demand.model.ldc.LdcDemandAndWeather;

@Controller
@RequestMapping("/ldc/**")
public class LdcDemandController implements ApplicationContextAware {
	Logger logger = LogManager.getLogger(this.getClass());
	private ApplicationContext applicationContext;

	@RequestMapping("/html")
	public String html(Model model) {

		List<Integer> hours = new ArrayList<Integer>(24);
		for (int i = 0; i < 24; i++) {
			hours.add(i);
		}
		model.addAttribute("hours", hours);

		return "ldc";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public ResponseEntity<List<LdcDemandAndWeather>> ldcJson() {
		EpcDemandAndWeatherDAO epcDemandAndWeatherDAO = (EpcDemandAndWeatherDAO) applicationContext
				.getBean("epcDemandAndWeatherDAO");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		List<LdcDemandAndWeather> ldcDemandAndWeathers = null;
		try {
			Date startDate = sdf.parse("2011-01-01 00:00:00");
			Date endDate = sdf.parse("2012-12-31 23:59:59");
			ldcDemandAndWeathers = epcDemandAndWeatherDAO
					.getEpcDemandAndWeather(startDate, endDate);
			logger.debug("ldcDemandAndWeathers size="
					+ ldcDemandAndWeathers.size());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Attempt to mark JSON content cacheable to client since it doesn't
		// change often
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setCacheControl("public, max-age=3600");
		responseHeaders.setExpires(DateTime.now().plusHours(1).getMillis());

		return new ResponseEntity<List<LdcDemandAndWeather>>(
				ldcDemandAndWeathers, responseHeaders, HttpStatus.CREATED);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
