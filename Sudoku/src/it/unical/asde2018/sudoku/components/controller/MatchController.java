package it.unical.asde2018.sudoku.components.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import it.unical.asde2018.sudoku.components.persistence.UserDAO;
import it.unical.asde2018.sudoku.components.services.CredentialService;
import it.unical.asde2018.sudoku.components.services.EventsService;
import it.unical.asde2018.sudoku.components.services.GameStartService;
import it.unical.asde2018.sudoku.components.services.LobbyService;
import it.unical.asde2018.sudoku.model.User;

@Controller
public class MatchController {

	@Autowired
	private GameStartService gameStartService;

	@Autowired
	private LobbyService lobbyService;

	@Autowired
	CredentialService credentialService;

	@Autowired
	private EventsService eventsService;

	@Autowired
	private UserDAO userDao;

	@GetMapping("wait")
	@Async
	public String waitBeforePlay(HttpSession session) {
		System.out.println("MatchController.waitBeforePlay()");

		return "waiting";
	}

	@PostMapping("startRoom")
	@Async
	public String startMatch(HttpSession session) {

		int room = (int) session.getAttribute("idRoom");
		gameStartService.putStartEvent(room);

		return "redirect:/";
	}

	@PostMapping("/checkEndGame")
	@ResponseBody
	@Async
	public String checkEndGame(@RequestParam String puzzle, HttpSession session) {
		String output = new String();

		int room = (int) session.getAttribute("idRoom");
		String username = (String) session.getAttribute("username");
		User user = credentialService.getUser(username);

		if (lobbyService.checkCorrectSudoku(room, puzzle)) {
			lobbyService.insertDurationOfGame(room, user);

			if (username.equals(lobbyService.matchWinner(room)))
				output = "You Win!!!";
			else
				output = "You Lose!!!";

			if (lobbyService.getMatches().get(room).getMatch().getDurations().size() > 1) {
				lobbyService.saveMatch(room);
				eventsService.removeData(room);
			}

			eventsService.removeUserData(username);

		} else
			output = ("WRONG");

		return output;
	}

	@PostMapping("/checkBoardFull")
	@ResponseBody
	@Async
	public DeferredResult<String> getEvents(HttpSession session) {

		DeferredResult<String> output = new DeferredResult<>();

		int room = (int) session.getAttribute("idRoom");
		String username = (String) session.getAttribute("username");

		System.err.println(lobbyService.getNumOfPlayersInTheRoom(room));

		if (lobbyService.getNumOfPlayersInTheRoom(room) == 2) {
			lobbyService.getMatches().get(room).setVisible(false);

			if (lobbyService.getMatches().get(room).getCreator().getUsername().equals(username)) {
				output.setResult("start_match");
			} else {
				if (gameStartService.getStartEvent(room))
					output.setResult("go_to_board");
				else
					output.setResult("loop");
			}
		} else if (!lobbyService.getMatches().get(room).getCreator().getUsername().equals(username) && gameStartService.getDeleteEvent(room)) {
			lobbyService.getMatches().get(room).getMatch().getPlayers().remove(userDao.getUser(username));
			if (lobbyService.getMatches().get(room).getMatch().getPlayers().isEmpty())
				lobbyService.getMatches().remove(room);

			session.removeAttribute("sudoku");
			session.removeAttribute("idRoom");
			output.setResult("chiudi");
		} else {
			output.setResult("loop");
		}

		return output;
	}

	@PostMapping("/exitMatch")
	@Async
	public void exitMatch(HttpSession session, HttpServletResponse response) {

		session.removeAttribute("sudoku");
		session.removeAttribute("idRoom");
		eventsService.removeUserData((String) session.getAttribute("username"));
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}

	// TODO LIVELLO GRAFICO GESTIRE QUANDO INSERISCE TUTTI I NUMERI ED IL SUDOKU è
	// SBAGLIATO

	@PostMapping("/exitBefore")
	@Async
	public void exitBefore(HttpSession session, HttpServletResponse response) {

		// Check for anticipated exit from the game
		int room = (int) session.getAttribute("idRoom");
		if (lobbyService.getMatches().containsKey(room)) {
			lobbyService.removeMatch(room);
			eventsService.removeData(room);
			eventsService.removeUserData((String) session.getAttribute("user"));
		}

		session.removeAttribute("sudoku");
		session.removeAttribute("idRoom");
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}

	@PostMapping("/requestEvent")
	@ResponseBody
	@Async
	public String addEvent(@RequestParam String number_inserted, HttpSession session, HttpServletResponse response) throws NumberFormatException, InterruptedException {

		String output = new String();
		int room = 0;
		String username = new String();
		Integer opponentNumber = new Integer(0);
		int diffNumber = 0;

		try {
			room = (int) session.getAttribute("idRoom");

			if (!eventsService.getSpecialEvent(room)) {
				username = (String) session.getAttribute("username");
				opponentNumber = eventsService.nextEvent(room, username, Integer.parseInt(number_inserted));

				diffNumber = lobbyService.getRoomDifficulty(room).getNumberToRemove();

				// DifficultyNumberToRemove : 100 = (InsertedNumber - (LockedNumber)) : x
				output = (opponentNumber > 0 ? (opponentNumber * 100) / diffNumber + " " : opponentNumber + "");

			} else {
				// In this case the opponent decide to exit before, so instead to send the
				// number of the opponent
				// was sended this number in order to identify that the game can end due to the
				// leaving of the adversary
				output = (400 + "");
			}
		} catch (Exception e) {

		}

		return output;
	}

	@PostMapping("/leaveMatchBeforeEnd")
	@ResponseBody
	@Async
	public void leaveMatchBeforeEnd(HttpSession session, HttpServletResponse response) throws NumberFormatException, InterruptedException {

		int room = (int) session.getAttribute("idRoom");
		eventsService.addSpecialEvent(room);
		eventsService.removeUserData((String) session.getAttribute("username"));
		session.removeAttribute("sudoku");
		session.removeAttribute("idRoom");
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}
}
