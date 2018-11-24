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

import it.unical.asde2018.sudoku.components.services.EventsService;
import it.unical.asde2018.sudoku.components.services.LobbyService;
import it.unical.asde2018.sudoku.logic.SudokuGrid;

@Controller
public class MatchController {

	@Autowired
	LobbyService lobbyService;

	@Autowired
	EventsService eventsService;

	@GetMapping("/checkBoardFull")
	@ResponseBody
	public DeferredResult<String> getEvents(HttpSession session) {

		DeferredResult<String> output = new DeferredResult<>();
		ForkJoinPool.commonPool().submit(() -> {
			int room = (int) session.getAttribute("idRoom");
			if (lobbyService.getMatches().get(room).getMatch().getPlayers().size() > 1)
				output.setResult("start");
			else
				output.setResult("loop");
		});

		return output;
	}

	@GetMapping("/requestEvent")
	@ResponseBody
	public DeferredResult<String> addEvent(@RequestParam String number_inserted, HttpSession session,
			HttpServletResponse response) throws NumberFormatException, InterruptedException {

		DeferredResult<String> output = new DeferredResult<>();

		Integer opponentNumber = eventsService.nextEvent((int) session.getAttribute("idRoom"),
				(String) session.getAttribute("username"), Integer.parseInt(number_inserted));
		int diffNumber = lobbyService.getMatches().get((int) session.getAttribute("idRoom")).getMatch().getDifficulty()
				.getNumberToRemove();

		output.setResult(opponentNumber != 0 ? (opponentNumber * 100) / diffNumber + " " : opponentNumber + "");

		return output;
	}
}
