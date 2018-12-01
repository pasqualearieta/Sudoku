package it.unical.asde2018.sudoku.components.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unical.asde2018.sudoku.components.persistence.MatchDAO;
import it.unical.asde2018.sudoku.components.persistence.UserDAO;
import it.unical.asde2018.sudoku.logic.Room;
import it.unical.asde2018.sudoku.logic.util.Difficulty;
import it.unical.asde2018.sudoku.model.Match;
import it.unical.asde2018.sudoku.model.User;

@Service
public class LobbyService {

	@Autowired
	private MatchDAO matchDAO;

	@Autowired
	private UserDAO userDAO;

	private Map<Integer, Room> matches = new LinkedHashMap<>();
	private static final int MATCHES_TO_SHOW = 2;

	public static int getMatchesToShow() {
		return MATCHES_TO_SHOW;
	}

	private static int idRoom = 0;

	public LobbyService() {
		super();
	}

	public Map<Integer, Room> getMatches() {
		return matches;
	}

	public int getMatchesSize() {
		return matches.size();
	}

	public Match getMatch(int roomId) {
		return getMatches().get(roomId).getMatch();
	}

	public Map<User, Long> getDurationsOfPlayers(int roomId) {
		return getMatches().get(roomId).getMatch().getDurations();
	}

	public int createRoom(User creator, Difficulty difficulty, String name) {

		Match match = new Match(creator, difficulty, name);
		Room room = new Room(match, creator);

		int idCurrentRoom = getIdRoom();

		matches.put(idCurrentRoom, room);

		return idCurrentRoom;
		// TODO reindirizzare a pagina wait
	}

	public void joinRoom(User player, int idRoomToJoin) {
		matches.get(idRoomToJoin).getMatch().getPlayers().add(player);
		// NOW NOBODY CAN SEE THIS AGAIN
		matches.get(idRoomToJoin).setVisible(false);

		// TODO reindirizzare a pagina gioco
	}

	public static int getIdRoom() {
		return ++idRoom;
	}

	public String matchWinner(int room) {

		return (getMatches().get(room).getMatch().getDurations().entrySet().stream().filter(p -> p.getValue() != 0)
				.min(Comparator.comparingLong(Map.Entry::getValue)).get().getKey()).getUsername();

	}

	public boolean checkCorrectSudoku(int room, String sudoku) {
		return (getMatches().get(room).getSudokuSolved().equals(sudoku)) ? true : false;
	}

	public void insertDurationOfGame(int room, String username, Date date) {

		Long time_to_inset = (date.getTime() > 0)
				? date.getTime() - getMatches().get(room).getMatch().getStarting_date().getTime()
				: 0;
		getMatches().get(room).getMatch().getDurations().put(userDAO.getUser(username), time_to_inset);
	}

	public int getNumOfPlayersInTheRoom(int room) {
		return getMatches().get(room).getMatch().getPlayers().size();
	}

	public Difficulty getMatchDifficulty(int room) {
		return getMatches().get(room).getMatch().getDifficulty();
	}

	public void saveMatch(int room) {

		System.err.println("Sto salvando il match");
		matchDAO.save(getMatches().get(room).getMatch());

		for (User user : getMatches().get(room).getMatch().getPlayers()) {
			user.getMatches().add(getMatches().get(room).getMatch());
			userDAO.update(user);

		}

	}

	public void removeMatch(int room) {
	//	System.err.println("rimuovo il match");
		getMatches().remove(room);
	}

	public Map<Integer, Room> getRoomInTheWindow(int indexOfTheLastRoomToShowInTheWindow) {
		Map<Integer, Room> windowed_room = new LinkedHashMap<>();

		int window_to_access = indexOfTheLastRoomToShowInTheWindow / MATCHES_TO_SHOW;

		int pos = (window_to_access * MATCHES_TO_SHOW) - MATCHES_TO_SHOW;

		int index = 0;
		int values = 0;
		for (Map.Entry<Integer, Room> entry : matches.entrySet())
		{
			if (values == MATCHES_TO_SHOW)
				break;

			if (entry.getValue().isVisible())
			{
				if (index < pos)
					index++;
				else
				{
					windowed_room.put(entry.getKey(), entry.getValue());
					values++;
				}
			}
		}
		return windowed_room;
	}

	public int getTotalRoomPage() {
		return (int) Math.ceil((double) getMatchesSize() / getMatchesToShow());
	}

	public Long getNumberOfDisconnectedPlayer(int room) {
		return getMatches().get(room).getMatch().getDurations().entrySet().stream().filter(p -> p.getValue() == 0)
				.collect(Collectors.counting());
	}

}
