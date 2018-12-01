package it.unical.asde2018.sudoku.components.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public String viewHistory(HttpSession session) {
		String username = (String) session.getAttribute("username");
		if(username==null)
			return "redirect:/";
		User userObj = credentialService.getUser(username);
		
		System.err.println(userObj.toString());
		
		
		List<Match> previousMatches = historyAnalyticsService.getPreviousMatches(userObj);
		Map<String,Double> metrics = historyAnalyticsService.getMetrics(username,previousMatches);
		for(String s : metrics.keySet()) {
			session.setAttribute(s, metrics.get(s));
		}
		session.removeAttribute("dashboard");
		session.setAttribute("viewLobby", "viewLobby");
		session.setAttribute("user", userObj);
		return "dashboard";
	}
}