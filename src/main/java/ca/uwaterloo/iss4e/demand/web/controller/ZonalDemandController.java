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

import ca.uwaterloo.iss4e.demand.dao.ieso.IesoDemandDAO;
import ca.uwaterloo.iss4e.demand.model.ieso.ZonalDemandSummary;
import ca.uwaterloo.iss4e.demand.web.command.ZonalDemandCommand;

@Controller
@RequestMapping("/zonal/**")
public class ZonalDemandController implements ApplicationContextAware {
	Logger logger = LogManager.getLogger(this.getClass());
	private ApplicationContext applicationContext;

	@RequestMapping("/html")
	public String html(@ModelAttribute ZonalDemandCommand command, Model model) {
		Gson gson = new Gson();
		IesoDemandDAO iesoDemandDAO = (IesoDemandDAO) applicationContext
				.getBean("iesoDemandDAO");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startDate = sdf.parse("2004-01-01 00:00:00");
			Date endDate = sdf.parse("2004-12-31 23:59:59");
			List<ZonalDemandSummary> zonalDemandSummaries = iesoDemandDAO
					.getZonalDemandSummariesForRange(startDate, endDate);
			logger.debug("zonalDemandSummaries size="
					+ zonalDemandSummaries.size());

			model.addAttribute("zonalDemandSummaries",
					gson.toJson(zonalDemandSummaries));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("command", command);
		return "zonal";
	}

	@RequestMapping(value = "/json/startDate/{startDateString}/endDate/{endDateString}", method = RequestMethod.GET)
	public @ResponseBody
	List<ZonalDemandSummary> json(@PathVariable String startDateString, @PathVariable String endDateString) {
		IesoDemandDAO iesoDemandDAO = (IesoDemandDAO) applicationContext
				.getBean("iesoDemandDAO");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

		List<ZonalDemandSummary> zonalDemandSummaries = null;
		try {
			Date startDate = sdf.parse(startDateString + " 00:00:00");
			Date endDate = sdf.parse(endDateString + " 23:59:59");
			zonalDemandSummaries = iesoDemandDAO
					.getZonalDemandSummariesForRange(startDate, endDate);
			logger.debug("zonalDemandSummaries size="
					+ zonalDemandSummaries.size());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return zonalDemandSummaries;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
