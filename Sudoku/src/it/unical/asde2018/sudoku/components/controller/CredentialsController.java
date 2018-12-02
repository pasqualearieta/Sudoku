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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.unical.asde2018.sudoku.components.services.ConnectedUsersService;
import it.unical.asde2018.sudoku.components.services.CredentialService;
import it.unical.asde2018.sudoku.components.services.LobbyService;

@Controller
public class CredentialsController {

	@Autowired
	private CredentialService credentialService;
	@Autowired
	private LobbyService lobbyService;
	@Autowired
	private ConnectedUsersService connectedUsers;

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
				if (session.getAttribute("currentPagination") == null || (int) session.getAttribute("currentPagination") == 1)
					session.setAttribute("total_room_page", lobbyService.getTotalRoomPage());
			} else
				session.setAttribute("total_room_page", 1);
		}
		return "lobby";
	}

	/**
	 * Method that allow the log-out of the user
	 * 
	 * @param session
	 * 
	 * @return
	 */
	@GetMapping("/logout")
	public String logout(HttpSession session) {

		String userToDisconnect = (String) session.getAttribute("username");
		connectedUsers.removeConnectedUser(userToDisconnect);

		session.invalidate();
		return "redirect:/";

	}

	/**
	 * Method that allow the log-in of the registered user
	 * 
	 * @param username
	 *            of the user
	 * @param password
	 *            of the user
	 * @param session
	 * 
	 * @return the status of the log-in
	 */
	@PostMapping("/login")
	@ResponseBody
	@Async
	public String loginAttempt(@RequestParam String username, @RequestParam String password, HttpSession session) {

		String result = "";

		if (credentialService.login(username, password)) {
			session.setAttribute("username", username);
			connectedUsers.addConnectedUser(username);
			result = "LOGIN_OK";
		} else
			result = "Username or Password not valid!";

		return result;
	}

	/**
	 * Method that allow the registration to the system of the new user
	 * 
	 * @param username
	 *            chosen by the user
	 * @param password
	 *            that the user will use to log-in in the system
	 * @param confirm_password
	 *            the same password chosen early
	 * @param session
	 * 
	 * @return if the user has registered correctly, he will be redirect to the
	 *         home page
	 */
	@PostMapping("/register")
	@ResponseBody
	@Async
	public String registrationAttempt(@RequestParam String username, @RequestParam String password, @RequestParam String confirm_password, HttpSession session) {

		if (!confirm_password.equals(password))
			return "PASSWORD";

		if (credentialService.registerUser(username, password)) {
			session.setAttribute("username", username);
			connectedUsers.addConnectedUser(username);
			return "SUCCESS";
		} else
			return "USERNAME";
	}

	/**
	 * Method that redirect to the home page
	 * 
	 * @param response
	 */
	@PostMapping("/goToHome")
	@ResponseBody
	@Async
	public void redirectToHome(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}

}
