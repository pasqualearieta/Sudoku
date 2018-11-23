package it.unical.asde2018.sudoku.components.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String createRoom(@RequestParam String lobbyName, @RequestParam String difficulty, HttpSession session, Model model) {

		String username = (String) session.getAttribute("username");
		User user = userDao.getUser(username);
		int id_room = lobbyService.createRoom(user, Difficulty.valueOf(difficulty), lobbyName);
		// if(session.getAttribute("sudoku") == null)
		model.addAttribute("sudoku", lobbyService.getMatches().get(id_room).getSudokuToSolve());
		model.addAttribute("idRoom", id_room);
		return "sudoku_game_board";
	}

	@PostMapping("roomPagination")
	public void switchRoomPagination(@RequestParam String index, HttpSession session, HttpServletResponse response) {
		int requested_pagination = Integer.parseInt(index);
		int finalIndex = requested_pagination * LobbyService.getMatchesToShow();

		session.setAttribute("currentPagination", requested_pagination);
		session.setAttribute("total_room_page", lobbyService.getTotalRoomPage());
		session.setAttribute("available_room", lobbyService.getRoomInTheWindow(finalIndex));

		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}

	@PostMapping("leaveRoom")
	public String leaveRoom(@RequestParam String idRoom, Model model) {

		lobbyService.getMatches().remove(Integer.parseInt(idRoom));
		model.addAttribute("sudoku", null);
		model.addAttribute("idRoom", null);
		return "redirect:/";
	}

}
