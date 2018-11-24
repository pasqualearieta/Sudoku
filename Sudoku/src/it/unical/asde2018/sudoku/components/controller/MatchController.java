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

@Controller
public class MatchController {

	@Autowired
	private LobbyService lobbyService;

	@Autowired
	private EventsService eventsService;

	// @GetMapping("/generateSudoku")
	// public DeferredResult<String> generateSudoku(HttpSession session) {
	//
	// DeferredResult<String> result = new DeferredResult<>();
	//
	// if (session.getAttribute("sudoku") == null) {
	// int room = (int) session.getAttribute("idRoom");
	//
	// if (!lobbyService.getMatches().get(room).isSudokuInCreation()) {
	// lobbyService.getMatches().get(room).setSudokuInCreation(true);
	//
	// lobbyService.getMatches().get(room).generateSudoku();
	// }
	//
	// result.setResult(lobbyService.getMatches().get(room).getSudokuToSolve());
	// session.setAttribute("sudoku", lobbyService.getMatches().get(room).getSudokuToSolve());
	// }
	//
	// return result;
	//
	// }

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
	public DeferredResult<String> addEvent(@RequestParam String number_inserted, HttpSession session, HttpServletResponse response) throws NumberFormatException, InterruptedException {

		DeferredResult<String> output = new DeferredResult<>();
		ForkJoinPool.commonPool().submit(() -> {
			try {
				output.setResult(new String(eventsService.nextEvent((int) session.getAttribute("idRoom"), (String) session.getAttribute("username"), Integer.parseInt(number_inserted)) + ""));
			} catch (InterruptedException e) {
				output.setResult("Nothing");
			}
		});

		return output;
	}
}
