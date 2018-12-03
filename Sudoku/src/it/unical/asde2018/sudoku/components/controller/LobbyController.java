package it.unical.asde2018.sudoku.components.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unical.asde2018.sudoku.components.services.GameStartService;
import it.unical.asde2018.sudoku.components.services.LobbyService;
import it.unical.asde2018.sudoku.components.services.SudokuGeneratorService;
import it.unical.asde2018.sudoku.logic.Room;
import it.unical.asde2018.sudoku.logic.util.Difficulty;
import it.unical.asde2018.sudoku.logic.util.Pagination;
import it.unical.asde2018.sudoku.logic.util.SudokuPuzzles;
import it.unical.asde2018.sudoku.model.User;

@Controller
public class LobbyController {

	@Autowired
	private GameStartService gameStartService;
	@Autowired
	private LobbyService lobbyService;
	@Autowired
	private SudokuGeneratorService sudokuGeneratorService;

	/**
	 * Method that allow the creating of a new room.
	 * 
	 * @param lobbyName
	 * @param difficulty
	 * @param session
	 * @return
	 */
	@PostMapping("create-lobby")
	public String createRoom(@RequestParam String lobbyName, @RequestParam String difficulty, HttpSession session) {

		if (session.getAttribute("sudoku") == null) {
			String username = (String) session.getAttribute("username");
			User user = lobbyService.getUser(username);
			int id_room = lobbyService.createRoom(user, Difficulty.valueOf(difficulty), lobbyName);

			SudokuPuzzles puzzles = sudokuGeneratorService.getSudokuByDifficulty(Difficulty.valueOf(difficulty));
			lobbyService.setSudokuToSolveToTheRoom(id_room, puzzles.getSudokuToSolve());
			lobbyService.setSudokuSolvedToTheRoom(id_room, puzzles.getSudokuSolved());

			session.setAttribute("sudoku", lobbyService.getMatches().get(id_room).getSudokuToSolve());
			session.setAttribute("idRoom", id_room);
		}

		return "waiting";
	}

	/**
	 * Method that allow the user to join in an existing room
	 * 
	 * @param room     to join
	 * @param session
	 * @param response
	 */
	@PostMapping("joinRoom")
	@Async
	public void joinRoom(@RequestParam String room, HttpSession session, HttpServletResponse response) {
		if (session.getAttribute("sudoku") == null) {
			String username = (String) session.getAttribute("username");
			int id_room = Integer.parseInt(room);
			User user = lobbyService.getUser(username);

			lobbyService.joinRoom(user, id_room);

			// set starting date of a match
			lobbyService.getMatch(id_room).setStarting_date(new Date());
			session.setAttribute("sudoku", lobbyService.getMatches().get(id_room).getSudokuToSolve());
			session.setAttribute("idRoom", id_room);
		}

		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}

	/**
	 * Method that updates the view of the playable rooms by organizing them in
	 * different pages
	 * 
	 * @param index   of the last room to show in a single page
	 * @param session
	 * @return
	 */
	@GetMapping("refresh")
	@ResponseBody
	@Async
	public DeferredResult<String> refreshList(@RequestParam String index) {

		int requested_pagination = 1;
		if (index != "")
			requested_pagination = Integer.parseInt(index);
		int finalIndex = requested_pagination * LobbyService.getMatchesToShow();
		Pagination pg = new Pagination(requested_pagination, lobbyService.getTotalRoomPage(),
				lobbyService.getRoomInTheWindow(finalIndex));
		ObjectMapper mapper = new ObjectMapper();
		DeferredResult<String> json = new DeferredResult<>();
		try {
			json.setResult(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pg));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			json.setResult("null");
		}
		return json;
	}

	/**
	 * Method that ensure the visualization of the available room
	 * 
	 * @param index
	 * @param session
	 * @param response
	 * @return
	 */
	@PostMapping("roomPagination")
	@ResponseBody
	public DeferredResult<String> switchRoomPagination(@RequestParam String index,
			HttpServletResponse response, Model model) {

		int requested_pagination = Integer.parseInt(index);
		int finalIndex = requested_pagination * LobbyService.getMatchesToShow();
		model.addAttribute("currentPagination", requested_pagination);
		model.addAttribute("total_room_page", lobbyService.getTotalRoomPage());
		Map<Integer, Room> roomInTheWindow = lobbyService.getRoomInTheWindow(finalIndex);
		DeferredResult<String> json = new DeferredResult<>();
		Pagination pg = new Pagination(requested_pagination, lobbyService.getTotalRoomPage(), roomInTheWindow);
		ObjectMapper mapper = new ObjectMapper();

		try {
			json.setResult(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pg));
		} catch (JsonProcessingException e) {
			json.setResult("null");
		}

		return json;
	}

	/**
	 * Method that allow the user to exit from a room
	 * 
	 * @param idRoom
	 * @param session
	 * @param response
	 */
	@PostMapping("leaveRoom")
	@Async
	public void leaveRoom(@RequestParam String idRoom, HttpSession session, HttpServletResponse response) {

		String username = (String) session.getAttribute("username");
		int room = (int) session.getAttribute("idRoom");

		if (lobbyService.getCreatorOfTheRoom(room).equals(username)) {
			gameStartService.putDeleteEvent(room);

			/**
			 * This call allow to recover the puzzles, in fact if the creator leave the room before the game starts, 
			 * the generated sudoku to this match become again available in the queue.
			 */
			sudokuGeneratorService.addRecoveredSudoku(lobbyService.getMatchDifficulty(room),
					lobbyService.getRoomSudokus(room));
		}

		if (lobbyService.getNumberOfPlayersInTheRoom(room) == 1
				&& lobbyService.getCreatorOfTheRoom(room).equals(username)) {
			lobbyService.removePlayer(room, username);
			lobbyService.removeRoom(room);
		} else {
			lobbyService.removePlayer(room, username);
			lobbyService.getMatches().get(room).setVisible(true);

		}

		session.removeAttribute("sudoku");
		session.removeAttribute("idRoom");
		response.setStatus(HttpServletResponse.SC_ACCEPTED);

	}
}
