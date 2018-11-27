package it.unical.asde2018.sudoku.components.controller;

import java.util.Date;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unical.asde18.serializer.LobbySerializer;
import it.unical.asde2018.sudoku.components.services.EventsService;
import it.unical.asde2018.sudoku.components.services.LobbyService;
import it.unical.asde2018.sudoku.model.User;

@Controller
public class MatchController {

	@Autowired
	private LobbyService lobbyService;

	@Autowired
	private EventsService eventsService;

	@GetMapping("/review")
	public String goToReview() {
		return "review";
	}
	
	@PostMapping("/getWinner")
	@ResponseBody
	@Async
	public DeferredResult<String> getWinner(HttpSession session) {

		DeferredResult<String> output = new DeferredResult<>();
		int room = (int) session.getAttribute("idRoom");

		output.setResult(lobbyService.matchWinner(room));
		return output;
	}

	@PostMapping("/getMatchInfo")
	@ResponseBody
	@Async
	public DeferredResult<String> getMatchInfo(HttpSession session) {

		DeferredResult<String> output = new DeferredResult<>();
		ObjectMapper mapper = new ObjectMapper();
		
		int room = (int) session.getAttribute("idRoom");
		
		
		
		try {
			output.setResult(mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(lobbyService.getMatches().get(room).getMatch()));
		} catch (JsonProcessingException e) {
			output.setResult("");
		}

		return output;
	}

	@PostMapping("/checkEndGame")
	@ResponseBody
	@Async
	public String checkEndGame(@RequestParam String puzzle, HttpSession session) {
		String output = new String();

		int room = (int) session.getAttribute("idRoom");
		String username = (String) session.getAttribute("username");

		if (lobbyService.checkCorrectSudoku(room, puzzle)) {
			lobbyService.insertDurationOfGame(room, username, new Date());
			/*
			if (username.equals(lobbyService.matchWinner(room)))
				output = "You Win!!!";
			else
				output = "You Lose!!!";

			 * if (lobbyService.getMatches().get(room).getMatch().getDurations().size() > 1)
			 * { eventsService.removeData(room); lobbyService.saveMatch(room); }
			 */

			output = "Game Ended!!";
			session.removeAttribute("sudoku");
			// session.removeAttribute("idRoom");
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
		if (lobbyService.getNumOfPlayersInTheRoom(room) > 1)
			output.setResult("start");
		else
			output.setResult("loop");

		return output;
	}

	

	// TODO LIVELLO GRAFICO GESTIRE QUANDO INSERISCE TUTTI I NUMERI ED IL SUDOKU �
	// SBAGLIATO

	@PostMapping("/requestEvent")
	@ResponseBody
	@Async
	public String addEvent(@RequestParam String number_inserted, HttpSession session, HttpServletResponse response)
			throws NumberFormatException, InterruptedException {

		String output = new String();
		int room = 0;
		String username = new String();
		Integer opponentNumber = new Integer(0);
		int diffNumber = 0;

		try {
			room = (int) session.getAttribute("idRoom");
			username = (String) session.getAttribute("username");

			if (!eventsService.getSpecialEvent(room)) {
				opponentNumber = eventsService.nextEvent(room, username, Integer.parseInt(number_inserted));

				diffNumber = lobbyService.getRoomDifficulty(room).getNumberToRemove();

				// DifficultyNumberToRemove : 100 = (InsertedNumber - (LockedNumber)) : x
				output = (opponentNumber > 0 ? (opponentNumber * 100) / diffNumber + " " : opponentNumber + "");

			} else {
				// In this case the opponent decide to exit before, so instead to send the
				// number of the opponent
				// was sended this number in order to identify that the game can end due to the
				// leaving of the adversary

				eventsService.removeData(room);
				eventsService.removeUserData(username);

				output = (400 + "");
			}
		} catch (Exception e) {

		}

		return output;
	}

	@PostMapping("/leaveMatchBeforeEnd")
	@ResponseBody
	@Async
	public void leaveMatchBeforeEnd(HttpSession session, HttpServletResponse response)
			throws NumberFormatException, InterruptedException {

		int room = (int) session.getAttribute("idRoom");
		String username = (String) session.getAttribute("username");

		if (lobbyService.getNumberOfDisconnectedPlayer(room) == 0) {
			lobbyService.insertDurationOfGame(room, username, new Date(0));
			eventsService.addSpecialEvent(room);
		} else {
			lobbyService.removeMatch(room);
			eventsService.removeData(room);
			eventsService.removeUserData(username);
		}

		eventsService.removeUserData((String) session.getAttribute("username"));
		session.removeAttribute("sudoku");
		session.removeAttribute("idRoom");
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}
}
