package it.unical.asde2018.sudoku.components.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInController
{

	
	@GetMapping("/")
	public String home() {
		return "home";
	}
}
