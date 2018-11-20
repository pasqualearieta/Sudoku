package it.unical.asde2018.sudoku.components.controller;

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
		if(session.getAttribute("username")!=null)
			return "home";
		else
			return "home";
		
	}

	@PostMapping("/login")
	public String loginAttempt(@RequestParam String username_log_in, @RequestParam String password_log_in,
			HttpSession session, Model model) {
		if (logInService.login(username_log_in, password_log_in)) {
			session.setAttribute("username", username_log_in);
			return "redirect:/";
		} else {
			model.addAttribute("login_failed", "Username or Password not valid!");
			return "home";
		}

	}
}
