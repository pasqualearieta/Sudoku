package it.unical.asde2018.sudoku.components.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unical.asde2018.sudoku.components.services.LobbyService;
import it.unical.asde2018.sudoku.components.services.PlayerEventsService;

@Controller
public class ReviewController {

	@Autowired
	private LobbyService lobbyService;
	@Autowired
	private PlayerEventsService eventsService;

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
	 * @param session
	 * @comment This method handle the request of exit from the review page. The
	 *          last player will remove the dependency of the room in the server
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

			int playerThatEndedTheGame = (int) (lobbyService.getNumberOfPlayersInTheRoom(roomId)
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

}
