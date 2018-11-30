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

import it.unical.asde2018.sudoku.components.persistence.UserDAO;
import it.unical.asde2018.sudoku.components.services.CredentialService;
import it.unical.asde2018.sudoku.components.services.GameStartService;
import it.unical.asde2018.sudoku.components.services.LobbyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unical.asde2018.sudoku.components.services.PlayerEventsService;


@Controller
public class MatchController {

	@Autowired
	private GameStartService gameStartService;

	@Autowired
	private LobbyService lobbyService;

	@Autowired
	private PlayerEventsService eventsService;

	/**
	 * @return review page
	 */
	@GetMapping("/review")
	public String goToReview() {
		return "review";
	}



	@Autowired
	private UserDAO userDao;

	@GetMapping("wait")
	@Async
	public String waitBeforePlay(HttpSession session) {
		

		return "waiting";
	}

	@PostMapping("startRoom")
	@Async
	public String startMatch(HttpSession session) {

		int room = (int) session.getAttribute("idRoom");
		gameStartService.putStartEvent(room);

		return "redirect:/";
	}


	/**
	 * @param session
	 * @comment This method handle the request of exit from the review page. The
	 *          last player will remove the dependecy of the room in the server
	 * @exception All the players except one are in the review panel. The last one
	 *                leave the room before end the game, the match was saved on the
	 *                db and removed from the availableRoom.
	 */
	@PostMapping("/exitGame")
	@ResponseBody
	@Async
	public void handleExitFromReviewPage(HttpSession session) {
		int roomId = (int) session.getAttribute("idRoom");

		try {

			eventsService.insertPlayerExitedFromReviewPage(roomId);

			int playerThatEndedTheGame = (int) (lobbyService.getNumOfPlayersInTheRoom(roomId)
					- lobbyService.getNumberOfDisconnectedPlayer(roomId));

			if (eventsService.getSizeOfPlayerExitedFromReviewPage(roomId) == playerThatEndedTheGame) {
				eventsService.removeDataOfPlayersExitedFromReviewPage(roomId);
				lobbyService.removeMatch(roomId);
			}

		} catch (InterruptedException | NullPointerException e) {
			session.removeAttribute("idRoom");

		}

		session.removeAttribute("idRoom");
	}

	/**
	 * @param session
	 * @return This method will return "winner" if the current user was the winner
	 *         of the match, "loser" otherwise
	 * @exception Captured when the other players were disconnected before end the
	 *                     game. In the case the player in the review page manually
	 *                     refresh the page, it receive a message.
	 */
	@PostMapping("/getWinner")
	@ResponseBody
	@Async
	public String checkIfTheCurrentUserIsTheWinner(HttpSession session) {

		String output = new String();

		int roomId = (int) session.getAttribute("idRoom");
		String username = (String) session.getAttribute("username");

		try {
			String winner = lobbyService.matchWinner(roomId);
			output = ((username.equals(winner)) ? "winner" : "loser");
		} catch (Exception e) {
			output = "allOut";

		}
		return output;
	}

	/**
	 *
	 * @param session
	 * @return The serialization of the match information
	 * @exception Captured when all the others player were disconnected and match
	 *                     information are not available in the server anymore
	 */
	@PostMapping("/getMatchInfo")
	@ResponseBody
	@Async
	public DeferredResult<String> getMatchInformation(HttpSession session) {

		DeferredResult<String> matchParsed = new DeferredResult<>();

		ObjectMapper matchMapper = new ObjectMapper();

		int roomId = (int) session.getAttribute("idRoom");

		try {
			matchParsed.setResult(
					matchMapper.writerWithDefaultPrettyPrinter().writeValueAsString(lobbyService.getMatch(roomId)));
		} catch (JsonProcessingException | NullPointerException e) {
			matchParsed.setResult("AllOut");
		}

		return matchParsed;
	}

	/**
	 * This method check if the sudoku submitted from the user was correct. The last
	 * player enable the match save on the db and remove the reference of the room
	 * from the server
	 *
	 * @param SudokuPuzzle
	 * @param session
	 * @return String
	 */
	@PostMapping("/checkEndGame")
	@ResponseBody
	@Async
	public String checkEndGame(@RequestParam String puzzle, HttpSession session) {
		String output = new String();

		int roomId = (int) session.getAttribute("idRoom");
		String username = (String) session.getAttribute("username");

		if (lobbyService.checkCorrectSudoku(roomId, puzzle)) {
			lobbyService.insertDurationOfGame(roomId, username, new Date());

			int playerThatEndedTheGame = (int) (lobbyService.getNumOfPlayersInTheRoom(roomId));

			if (lobbyService.getDurationsOfPlayers(roomId).size() == playerThatEndedTheGame) {
				eventsService.removeRoomData(roomId);
				lobbyService.saveMatch(roomId);
			}

			output = "Game Ended!!";
			session.removeAttribute("sudoku");
			eventsService.removePlayerLastInserted(username);

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

	/**
	 * This method firstly add the number inserted of the player in the sudoku only
	 * if the previous adding is different from the current one.
	 *
	 * @param number  Inserted from the player
	 * @param session
	 * @return The status of the opponent player, or return a string indicating that
	 *         the opponent was disconnected
	 */
	@PostMapping("/requestEvent")
	@ResponseBody
	@Async
	public String addPlayerStatus(@RequestParam String number_inserted, HttpSession session)
			throws NumberFormatException, InterruptedException {

		String output = new String();
		int roomId = 0;
		String username = new String();
		Integer opponentInsertedNumber = new Integer(0);
		int difficultyNumber = 0;

		try {
			roomId = (int) session.getAttribute("idRoom");
			username = (String) session.getAttribute("username");

			if (!eventsService.getDisconnectedPlayerEvent(roomId)) {
				opponentInsertedNumber = eventsService.playerEvent(roomId, username, Integer.parseInt(number_inserted));

				difficultyNumber = lobbyService.getMatchDifficulty(roomId).getNumberToRemove();

				/**
				 * @MathOperation DifficultyNumberToRemove : 100 = InsertedNumber : x
				 */
				output = (opponentInsertedNumber > 0 ? (opponentInsertedNumber * 100) / difficultyNumber + " "
						: opponentInsertedNumber + "");

			} else {

				/**
				 * In this case the opponent decide to exit before, instead to send the opponent
				 * number was sended this number in order to identify this event
				 *
				 */

				eventsService.removeRoomData(roomId);
				eventsService.removePlayerLastInserted(username);

				output = (400 + "");
			}
		} catch (Exception e) {
			output = "";
		}

		return output;
	}

	/**
	 *
	 * This method handle the exit of the game from a player before he complete the
	 * sudoku. In the case that all the players decide to exit, the last one remove
	 * all the reference from the server and the game is deleted. Otherwise it add
	 * the disconnected event and return to the lobby page
	 *
	 * @param session
	 * @param response
	 *
	 */
	@PostMapping("/leaveMatchBeforeEnd")
	@ResponseBody
	@Async
	public void leaveMatchBeforeEnd(HttpSession session, HttpServletResponse response)
			throws NumberFormatException, InterruptedException {

		int room = (int) session.getAttribute("idRoom");
		String username = (String) session.getAttribute("username");

		if (lobbyService.getNumberOfDisconnectedPlayer(room) < lobbyService.getNumOfPlayersInTheRoom(room) - 1) {

			/**
			 * Duration equal to 0 indicate that a user was disconnected
			 */
			lobbyService.insertDurationOfGame(room, username, new Date(0));
			eventsService.addDisconnectedEvent(room);

			/**
			 * @SpecialCase In this case all the other players ended the game(Not
			 *              Disconnected). The last one decide to disconnect so the match
			 *              was saved on the DB and all the reference from the server were
			 *              deleted.
			 *
			 *              It cause a Capture of the exception in the method
			 * @see handleExitFromReviewPage(HttpSession)
			 * @see checkIfTheCurrentUserIsTheWinner(HttpSession)
			 * @see getMatchInformation(HttpSession)
			 *
			 *      (This only if the user refresh the review page)
			 *
			 */
			if (lobbyService.getDurationsOfPlayers(room).size() == lobbyService.getNumOfPlayersInTheRoom(room)
					&& lobbyService.getNumberOfDisconnectedPlayer(room) < lobbyService.getNumOfPlayersInTheRoom(room)) {

				eventsService.removeRoomData(room);
				lobbyService.saveMatch(room);
				lobbyService.removeMatch(room);
			}

		} else {
			lobbyService.removeMatch(room);
			eventsService.removeRoomData(room);
		}

		eventsService.removePlayerLastInserted(username);
		session.removeAttribute("sudoku");
		session.removeAttribute("idRoom");
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}
}
