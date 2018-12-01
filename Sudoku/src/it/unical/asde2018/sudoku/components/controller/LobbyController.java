package it.unical.asde2018.sudoku.components.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unical.asde18.serializer.LobbySerializer;
import it.unical.asde18.serializer.Pagination;
import it.unical.asde2018.sudoku.components.persistence.UserDAO;
import it.unical.asde2018.sudoku.components.services.GameStartService;
import it.unical.asde2018.sudoku.components.services.LobbyService;
import it.unical.asde2018.sudoku.components.services.SudokuGeneratorService;
import it.unical.asde2018.sudoku.logic.Room;
import it.unical.asde2018.sudoku.logic.util.Difficulty;
import it.unical.asde2018.sudoku.logic.util.SudokuPuzzles;
import it.unical.asde2018.sudoku.model.User;

@Controller
public class LobbyController
{

	@Autowired
	private GameStartService gameStartService;
	@Autowired
	private LobbyService lobbyService;
	@Autowired
	private SudokuGeneratorService sudokuGeneratorService;
	@Autowired
	private UserDAO userDao;

	@PostMapping("create-lobby")
	public String createRoom(@RequestParam String lobbyName, @RequestParam String difficulty, HttpSession session) {

		if (session.getAttribute("sudoku") == null)
		{
			String username = (String) session.getAttribute("username");
			User user = userDao.getUser(username);
			int id_room = lobbyService.createRoom(user, Difficulty.valueOf(difficulty), lobbyName);

			SudokuPuzzles puzzles = sudokuGeneratorService.getSudokuByDifficulty(Difficulty.valueOf(difficulty));
			lobbyService.getMatches().get(id_room).setSudokuToSolve(puzzles.getSudokuToSolve());
			lobbyService.getMatches().get(id_room).setSudokuSolved(puzzles.getSudokuSolved());

			session.setAttribute("sudoku", lobbyService.getMatches().get(id_room).getSudokuToSolve());
			session.setAttribute("idRoom", id_room);
		}

		return "waiting";
	}

	@PostMapping("joinRoom")
	@Async
	public void joinRoom(@RequestParam String room, HttpSession session, HttpServletResponse response) {
		if (session.getAttribute("sudoku") == null)
		{
			String username = (String) session.getAttribute("username");
			User user = userDao.getUser(username);

			lobbyService.joinRoom(user, Integer.parseInt(room));

			// set starting date of a match
			lobbyService.getMatches().get(Integer.parseInt(room)).getMatch().setStarting_date(new Date());
			session.setAttribute("sudoku", lobbyService.getMatches().get(Integer.parseInt(room)).getSudokuToSolve());
			session.setAttribute("idRoom", Integer.parseInt(room));
		}

		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}

	@PostMapping("roomPagination")
	@ResponseBody
	public DeferredResult<String> switchRoomPagination(@RequestParam String index, HttpSession session, HttpServletResponse response) {

		int requested_pagination = Integer.parseInt(index);
		int finalIndex = requested_pagination * LobbyService.getMatchesToShow();
		session.setAttribute("currentPagination", requested_pagination);
		session.setAttribute("total_room_page", lobbyService.getTotalRoomPage());
		Map<Integer, Room> roomInTheWindow = lobbyService.getRoomInTheWindow(finalIndex);
		DeferredResult<String> json = new DeferredResult<>();
		Pagination pg = new Pagination(requested_pagination, lobbyService.getTotalRoomPage(), roomInTheWindow);
		ObjectMapper mapper = new ObjectMapper();

		try
		{
			json.setResult(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pg));
		} catch (JsonProcessingException e)
		{
			json.setResult("null");
		}

		return json;
	}

	@PostMapping("leaveRoom")
	@Async
	public void leaveRoom(@RequestParam String idRoom, HttpSession session, HttpServletResponse response) {

		String username = (String) session.getAttribute("username");
		int room = (int) session.getAttribute("idRoom");

		if (lobbyService.getMatches().get(room).getCreator().getUsername().equals(username))
		{
			gameStartService.putDeleteEvent(room);
		}

		if (lobbyService.getMatches().get(room).getMatch().getPlayers().size() == 1
				&& lobbyService.getMatches().get(room).getCreator().getUsername().equals(username))
		{
			lobbyService.getMatches().get(room).getMatch().getPlayers().remove(userDao.getUser(username));
			lobbyService.getMatches().remove(room);
		} else
		{
			lobbyService.getMatches().get(room).getMatch().getPlayers().remove(userDao.getUser(username));
			lobbyService.getMatches().get(room).setVisible(true);

		}

		session.removeAttribute("sudoku");
		session.removeAttribute("idRoom");
		response.setStatus(HttpServletResponse.SC_ACCEPTED);

	}
}
