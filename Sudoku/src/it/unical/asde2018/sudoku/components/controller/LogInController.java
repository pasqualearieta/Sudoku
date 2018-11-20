package it.unical.asde2018.sudoku.components.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.unical.asde2018.sudoku.components.services.LogInService;

@Controller
public class LogInController {

	@Autowired
	private LogInService logInService;

	@GetMapping("/")
	public String home(HttpSession session) {
		if (session.getAttribute("username") != null)
			return "home";
		else
			return "home";

	}

	@PostMapping("/login")
	public void loginAttempt(@RequestParam String username, @RequestParam String password, HttpSession session,
			Model model, HttpServletResponse response) {

		if (logInService.login(username, password)) {
			session.setAttribute("username", username);
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

	}
}
