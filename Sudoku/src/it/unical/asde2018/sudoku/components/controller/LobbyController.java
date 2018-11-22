package it.unical.asde2018.sudoku.components.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.unical.asde2018.sudoku.components.persistence.UserDAO;
import it.unical.asde2018.sudoku.components.services.LobbyService;
import it.unical.asde2018.sudoku.logic.util.Difficulty;
import it.unical.asde2018.sudoku.model.User;

@Controller
public class LobbyController {

	@Autowired
	private LobbyService lobbyService;

	@Autowired
	private UserDAO userDao;

	@PostMapping("create-lobby")
	public String createRoom(@RequestParam String lobbyName, @RequestParam String difficulty, HttpSession session) {

		String username = (String) session.getAttribute("username");
		User user = userDao.getUser(username);
		lobbyService.createRoom(user, Difficulty.valueOf(difficulty), lobbyName);
		return "waiting";
	}
	
	
}
