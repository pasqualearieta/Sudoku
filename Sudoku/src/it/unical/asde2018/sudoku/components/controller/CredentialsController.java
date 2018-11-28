package it.unical.asde2018.sudoku.components.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import it.unical.asde18.serializer.LobbySerializer;
import it.unical.asde2018.sudoku.components.services.CredentialService;
import it.unical.asde2018.sudoku.components.services.LobbyService;
import it.unical.asde2018.sudoku.logic.Room;

@Controller
public class CredentialsController
{
	@Autowired
	private CredentialService credentialService;

	@Autowired
	private LobbyService lobbyService;

	@GetMapping("/")
	public String home(HttpSession session) {
		session.removeAttribute("dashboard");
		if (session.getAttribute("sudoku") != null)
			return "sudoku_game_board";
		else
		{
			if (session.getAttribute("username") != null)
			{
				session.setAttribute("currentPagination", 1);
				if (lobbyService.getMatchesSize() > 0)
				{
					if (session.getAttribute("currentPagination") == null || (int) session.getAttribute("currentPagination") == 1)
					{
						session.setAttribute("total_room_page", lobbyService.getTotalRoomPage());
						session.setAttribute("available_room", lobbyService.getRoomInTheWindow(LobbyService.getMatchesToShow()));
					}
				}
				else
					session.setAttribute("total_room_page",1);
				return "lobby";

			} else
				return "home";
		}
	}

	@GetMapping("refresh")
	@ResponseBody
	@Async
	public DeferredResult<String> refreshList(HttpSession session) {

		Map<Integer, Room> map = new HashMap<>();

		if (lobbyService.getMatchesSize() > 0)
		{
			if (session.getAttribute("currentPagination") == null || (int) session.getAttribute("currentPagination") == 1)
			{
				session.setAttribute("currentPagination", 1);
				int total = 1;
				if ((lobbyService.getMatchesSize() / LobbyService.getMatchesToShow()) % 2 == 0)
					total = lobbyService.getMatchesSize() / LobbyService.getMatchesToShow();
				else
					total = lobbyService.getMatchesSize() / LobbyService.getMatchesToShow() + 1;
				session.setAttribute("total_room_page", total);
				map = lobbyService.getRoomInTheWindow(LobbyService.getMatchesToShow());
				session.setAttribute("available_room", map);
	
			}
		}
		LobbySerializer ls = new LobbySerializer(map);
		DeferredResult<String> json = new DeferredResult<>();
		json.setResult(ls.getJSon());
		return json;
	}

	@GetMapping("/GoToLobby")
	public String goToLobby() {
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";

	}

	@GetMapping("/dashboard")
	public String viewHistory(HttpSession session) {
		session.setAttribute("dashboard", "dashboard");
		return "dashboard";

	}

	@PostMapping("/login")
	@ResponseBody
	@Async
	public String loginAttempt(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {

		String result = "";

		if (credentialService.login(username, password))
		{
			session.setAttribute("username", username);
			result = "LOGIN_OK";
		} else
			result = "Username or Password not valid!";


		return result;
	}

	@PostMapping("/register")
	@ResponseBody
	@Async
	public String registrationAttempt(@RequestParam String username, @RequestParam String password, @RequestParam String confirm_password,
			HttpSession session, Model model) {

		if (!confirm_password.equals(password))
			return "PASSWORD";

		if (credentialService.registerUser(username, password))
		{
			session.setAttribute("username", username);
			return "SUCCESS";
		} else
			return "USERNAME";
	}

}
