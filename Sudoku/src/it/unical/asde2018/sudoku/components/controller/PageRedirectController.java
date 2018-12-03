package it.unical.asde2018.sudoku.components.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.unical.asde2018.sudoku.components.services.LobbyService;

@Controller
public class PageRedirectController {

	@Autowired
	private LobbyService lobbyService;

	/**
	 * Method that redirect to the root of the project
	 * 
	 * @param session
	 * @return home page
	 */
	@GetMapping("/")
	public String home(HttpSession session) {
		session.setAttribute("viewLobby", "viewLobby");
		session.setAttribute("dashboard", "dashboard");
		session.setAttribute("currentPagination", 1);
		return "home";
	}

	/**
	 * Method that redirect to the home page from logo
	 * 
	 * @param response
	 */
	@PostMapping("/goToHome")
	@ResponseBody
	@Async
	public void redirectToHome(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}

	/**
	 * Method that redirect to the lobby page
	 * 
	 * @param session
	 * @return lobby page
	 */
	@RequestMapping(value = "/lobby", method = { RequestMethod.GET, RequestMethod.POST })
	public String goToLobby(HttpSession session) {

		session.removeAttribute("viewLobby");
		session.setAttribute("dashboard", "dashboard");
		if (session.getAttribute("username") != null) {
			session.setAttribute("currentPagination", 1);
			if (lobbyService.getMatchesSize() > 0) {
				if (session.getAttribute("currentPagination") == null
						|| (int) session.getAttribute("currentPagination") == 1)
					session.setAttribute("total_room_page", lobbyService.getTotalRoomPage());
			} else
				session.setAttribute("total_room_page", 1);
		}
		return "lobby";
	}

	/**
	 * Show the waiting page at the user that join in a room
	 * 
	 * @return waiting page
	 */
	@GetMapping("wait")
	@Async
	public String waitBeforePlay(HttpSession session) {
		return "waiting";
	}

	/**
	 * Show the sudoku grid to the player
	 * 
	 * @param session
	 * @return sudoku page
	 */
	@RequestMapping(value = "/gameBoard", method = { RequestMethod.GET, RequestMethod.POST })
	public String goToBoard(HttpSession session) {
		return "sudoku_game_board";
	}

	/**
	 * @return review page
	 */
	@GetMapping("/review")
	public String goToReview() {
		return "review";
	}

}
