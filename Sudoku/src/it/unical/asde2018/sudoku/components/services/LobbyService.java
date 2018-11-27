package it.unical.asde2018.sudoku.components.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unical.asde2018.sudoku.components.persistence.MatchDAO;
import it.unical.asde2018.sudoku.components.persistence.UserDAO;
import it.unical.asde2018.sudoku.logic.Room;
import it.unical.asde2018.sudoku.logic.util.Difficulty;
import it.unical.asde2018.sudoku.model.Match;
import it.unical.asde2018.sudoku.model.User;

@Service
public class LobbyService
{

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

	public LobbyService()
	{
		super();
	}

	public Map<Integer, Room> getMatches() {
		return matches;
	}

	public int getMatchesSize() {
		return matches.size();
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
		//NOW NOBODY CAN SEE THIS AGAIN
		matches.get(idRoomToJoin).setVisible(false);

		// TODO reindirizzare a pagina gioco
	}

	public static int getIdRoom() {
		return ++idRoom;
	}

	public String matchWinner(int room) {
		String winner = null;
		Long minValue = Long.MAX_VALUE;

		for (Entry<User, Long> entry : getMatches().get(room).getMatch().getDurations().entrySet())
		{
			Long value = entry.getValue();
			if (value < minValue)
			{
				winner = entry.getKey().getUsername();
				minValue = value;
			}
		}

		System.err.println(
				minValue + " DURATAAAAAAAAAAAAA " + winner + " WINNER" + " SIZE: " + getMatches().get(room).getMatch().getDurations().size());

		return winner;
	}

	public boolean checkCorrectSudoku(int room, String sudoku) {
		return (getMatches().get(room).getSudokuSolved().equals(sudoku)) ? true : false;
	}

	public void insertDurationOfGame(int room, User user) {
		getMatches().get(room).getMatch().getDurations().put(user, new Date().getTime());
	}

	public int getNumOfPlayersInTheRoom(int room) {
		return getMatches().get(room).getMatch().getPlayers().size();
	}

	public Difficulty getRoomDifficulty(int room) {
		return getMatches().get(room).getMatch().getDifficulty();
	}

	public void saveMatch(int room) {
		matchDAO.save(getMatches().get(room).getMatch());

		for (User user : getMatches().get(room).getMatch().getPlayers())
		{
			user.getMatches().add(getMatches().get(room).getMatch());
			userDAO.update(user);

//			for (Match m : user.getMatches())
//				System.out.println(m.toString());
		}

		removeMatch(room);
	}

	public void removeMatch(int room) {
		getMatches().remove(room);
	}

	public Map<Integer, Room> getRoomInTheWindow(int indexOfTheLastRoomToShowInTheWindow) {
		Map<Integer, Room> windowed_room = new LinkedHashMap<>();

		int window_to_access = indexOfTheLastRoomToShowInTheWindow / MATCHES_TO_SHOW;
		int pos = (window_to_access * MATCHES_TO_SHOW) - MATCHES_TO_SHOW;

		for (int i = 0; i < MATCHES_TO_SHOW; i++)
		{
			try
			{
				int key = (new ArrayList<>(matches.keySet())).get(pos);
				Room val = (new ArrayList<>(matches.values())).get(pos);
				if (val.isVisible())
					windowed_room.put(key, val);
				pos++;
			} catch (Exception e)
			{
				break;
			}
		}
		return windowed_room;
	}

	public int getTotalRoomPage() {
		return (int) Math.ceil((double) getMatchesSize() / getMatchesToShow());
	}

}
