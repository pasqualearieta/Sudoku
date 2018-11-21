package it.unical.asde2018.sudoku.components.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import it.unical.asde2018.sudoku.components.services.LogInService;

@Controller
public class LogInController {

	@Autowired
	private LogInService logInService;

	@GetMapping("/")
	public String home(HttpSession session) {
		if (session.getAttribute("username") != null)
			return "lobby";
		else
			return "home";
		
		

	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";

	}


	@GetMapping("/dashboard")
	public String viewHistory(HttpSession session) {
		return "dashboard";

	}

	@PostMapping("/login")
	@ResponseBody
	public String loginAttempt(@RequestParam String username, @RequestParam String password, HttpSession session,
			Model model) {

		String result = new String();

		if (logInService.login(username, password)) {
			session.setAttribute("username", username);
			result = "LOGIN_OK";
		} else {
			result = "Username or Password not valid!";

		}

		return result;

	}
}
