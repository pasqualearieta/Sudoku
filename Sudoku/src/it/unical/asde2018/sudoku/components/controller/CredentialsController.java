package it.unical.asde2018.sudoku.components.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import it.unical.asde18.serializer.LobbySerializer;
import it.unical.asde2018.sudoku.components.services.ConnectedUsersService;
import it.unical.asde2018.sudoku.components.services.CredentialService;
import it.unical.asde2018.sudoku.components.services.LobbyService;
import it.unical.asde2018.sudoku.logic.Room;

@Controller
public class CredentialsController {

	@Autowired
	private CredentialService credentialService;
	@Autowired
	private LobbyService lobbyService;
	@Autowired
	private ConnectedUsersService connectedUsers;

	@GetMapping("/")
	public String home(HttpSession session) {
		session.setAttribute("viewLobby", "viewLobby");
		session.setAttribute("dashboard", "dashboard");
		session.setAttribute("currentPagination", 1);
		return "home";
	}

	@GetMapping("refresh")
	@ResponseBody
	@Async
	public DeferredResult<String> refreshList(@RequestParam String index, HttpSession session) {

		int requested_pagination = Integer.parseInt(index);
		int finalIndex = requested_pagination * LobbyService.getMatchesToShow();
		session.setAttribute("currentPagination", requested_pagination);
		session.setAttribute("total_room_page", lobbyService.getTotalRoomPage());
		Map<Integer, Room> roomInTheWindow = lobbyService.getRoomInTheWindow(finalIndex);
		LobbySerializer ls = new LobbySerializer(roomInTheWindow);
		DeferredResult<String> json = new DeferredResult<>();
		json.setResult(ls.getJSon());
		return json;
	}

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

	@GetMapping("/logout")
	public String logout(HttpSession session) {

		String userToDisconnect = (String) session.getAttribute("username");
		connectedUsers.removeConnectedUser(userToDisconnect);

		session.invalidate();
		return "redirect:/";

	}

	@PostMapping("/login")
	@ResponseBody
	@Async
	public String loginAttempt(@RequestParam String username, @RequestParam String password, HttpSession session,
			Model model) {

		String result = "";

		if (credentialService.login(username, password)) {
			session.setAttribute("username", username);
			connectedUsers.addConnectedUser(username);
			result = "LOGIN_OK";
		} else
			result = "Username or Password not valid!";

		return result;
	}

	@PostMapping("/register")
	@ResponseBody
	@Async
	public String registrationAttempt(@RequestParam String username, @RequestParam String password,
			@RequestParam String confirm_password, HttpSession session, Model model) {

		if (!confirm_password.equals(password))
			return "PASSWORD";

		if (credentialService.registerUser(username, password)) {
			session.setAttribute("username", username);
			connectedUsers.addConnectedUser(username);
			return "SUCCESS";
		} else
			return "USERNAME";
	}

	@PostMapping("/goToHome")
	@ResponseBody
	@Async
	public void redirectToHome(HttpSession session, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}

}
