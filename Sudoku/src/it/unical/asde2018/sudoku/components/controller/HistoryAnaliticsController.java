package it.unical.asde2018.sudoku.components.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.unical.asde2018.sudoku.components.services.CredentialService;
import it.unical.asde2018.sudoku.components.services.HistoryAnalyticsService;
import it.unical.asde2018.sudoku.model.Match;
import it.unical.asde2018.sudoku.model.User;

@Controller
public class HistoryAnaliticsController {

	@Autowired
	private CredentialService credentialService;

	@Autowired
	private HistoryAnalyticsService historyAnalyticsService;

	@GetMapping("/dashboard")
	public String viewHistory(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
		if(username==null)
			return "redirect:/";
		User userObj = credentialService.getUser(username);
		
		List<Match> previousMatches = historyAnalyticsService.getPreviousMatches(userObj);
		Map<String,Double> metrics = historyAnalyticsService.getMetrics(username,previousMatches);
		for(String s : metrics.keySet()) {
			model.addAttribute(s, metrics.get(s));
		}
		session.removeAttribute("dashboard");
		model.addAttribute("viewLobby", "viewLobby");
		model.addAttribute("user", userObj);
		return "dashboard";
	}
}