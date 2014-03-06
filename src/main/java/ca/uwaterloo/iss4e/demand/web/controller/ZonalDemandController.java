package ca.uwaterloo.iss4e.demand.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import ca.uwaterloo.iss4e.demand.web.command.ZonalDemandCommand;

@Controller
public class ZonalDemandController {

	@RequestMapping("/zonal")
	public String zonal(@ModelAttribute ZonalDemandCommand command,
			Model model) {
		
		
		model.addAttribute("command", command);
		return "zonal";
	}
}
