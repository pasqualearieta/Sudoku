package it.unical.asde2018.sudoku.components.controller;

import java.util.concurrent.ForkJoinPool;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import it.unical.asde2018.sudoku.components.services.CredentialService;
import it.unical.asde2018.sudoku.components.services.EventsService;
import it.unical.asde2018.sudoku.components.services.LobbyService;
import it.unical.asde2018.sudoku.model.User;

@Controller
public class MatchController {

	@Autowired
	LobbyService lobbyService;

	@Autowired
	CredentialService credentialService;

	@Autowired
	EventsService eventsService;

	@GetMapping("/checkEndGame")
	@ResponseBody
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

			if (lobbyService.getMatches().get(room).getMatch().getDurations().size() > 1) 
				lobbyService.saveMatch(room);
			
		} else
			output = ("WRONG");

		return output;
	}

	@GetMapping("/checkBoardFull")
	@ResponseBody
	public DeferredResult<String> getEvents(HttpSession session) {

		DeferredResult<String> output = new DeferredResult<>();

		ForkJoinPool.commonPool().submit(() -> {
			int room = (int) session.getAttribute("idRoom");
			if (lobbyService.getNumOfPlayersInTheRoom(room) > 1)
				output.setResult("start");
			else
				output.setResult("loop");
		});

		return output;
	}

	@GetMapping("/exitMatch")
	public void exitMatch(HttpSession session, HttpServletResponse response) {

		session.removeAttribute("sudoku");
		session.removeAttribute("idRoom");
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}

	//TODO QUANDO ESCO DA UNA PARTITA VA GESTITA ANCHE LA LISTA DEGLI EVENTI
	//TODO LIVELLO GRAFICO GESTIRE QUANDO INSERISCE TUTTI I NUMERI ED IL SUDOKU è SBAGLIATO
	
	@GetMapping("/exitBefore")
	public void exitBefore(HttpSession session, HttpServletResponse response) {

		// Check for anticipated exit from the game
		int room = (int) session.getAttribute("idRoom");
		if (lobbyService.getMatches().containsKey(room)) 
			lobbyService.removeMatch(room);
		
		
		session.removeAttribute("sudoku");
		session.removeAttribute("idRoom");
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}

	@GetMapping("/requestEvent")
	@ResponseBody
	public DeferredResult<String> addEvent(@RequestParam String number_inserted, HttpSession session,
			HttpServletResponse response) throws NumberFormatException, InterruptedException {

		DeferredResult<String> output = new DeferredResult<>();
		int room = 0;
		String username = new String();
		Integer opponentNumber = new Integer(0);
		int diffNumber = 0;

		try {
			room = (int) session.getAttribute("idRoom");

			if (!eventsService.getSpecialEvent(room)) {
				username = (String) session.getAttribute("username");
				opponentNumber = eventsService.nextEvent(room, username, Integer.parseInt(number_inserted));
				System.err.println(opponentNumber);

				diffNumber = lobbyService.getRoomDifficulty(room).getNumberToRemove();

				// DifficultyNumberToRemove : 100 = (InsertedNumber - (LockedNumber)) : x
				output.setResult(opponentNumber > 0 ? (opponentNumber * 100) / diffNumber + " " : opponentNumber + "");

			} else {
				// In this case the opponent decide to exit before, so instead to send the
				// number of the opponent
				// was sended this number in order to identify that the game can end due to the
				// leaving of the adversary
				output.setResult(400 + "");
			}
		} catch (Exception e) {

		}

		System.out.println(output.toString());
		return output;
	}

	@GetMapping("/leaveMatchBeforeEnd")
	@ResponseBody
	public void leaveMatchBeforeEnd(HttpSession session, HttpServletResponse response)
			throws NumberFormatException, InterruptedException {

		int room = (int) session.getAttribute("idRoom");
		eventsService.addSpecialEvent(room);
		session.removeAttribute("sudoku");
		session.removeAttribute("idRoom");
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}
}
