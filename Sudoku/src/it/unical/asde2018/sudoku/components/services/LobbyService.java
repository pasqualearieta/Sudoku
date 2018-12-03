package it.unical.asde2018.sudoku.components.services;

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
import it.unical.asde2018.sudoku.logic.util.SudokuPuzzles;
import it.unical.asde2018.sudoku.model.Match;
import it.unical.asde2018.sudoku.model.User;

@Service
public class LobbyService {

	private Map<Integer, Room> matches = new LinkedHashMap<>();
	private static final int MATCHES_TO_SHOW = 2;

	@Autowired
	private MatchDAO matchDAO;

	@Autowired
	private UserDAO userDAO;

	private static int idRoom = 0;

	public static int getMatchesToShow() {
		return MATCHES_TO_SHOW;
	}

	public LobbyService() {
		super();
	}

	public Map<Integer, Room> getMatches() {
		return matches;
	}

	public int getMatchesSize() {
		return matches.size();
	}

	public int getVisibleMatchesSize() {
		int count = 0;
		for (Map.Entry<Integer, Room> entry : matches.entrySet()) {
			if (entry.getValue().isVisible())
				count++;
		}
		return count;
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
	}

	public void joinRoom(User player, int idRoomToJoin) {
		matches.get(idRoomToJoin).getMatch().getPlayers().add(player);
		matches.get(idRoomToJoin).setVisible(false);
	}

	public static int getIdRoom() {
		return ++idRoom;
	}

	public String matchWinner(int room) {

		return (getMatches().get(room).getMatch().getDurations().entrySet().stream().filter(p -> p.getValue() != 0).min(Comparator.comparingLong(Map.Entry::getValue)).get().getKey()).getUsername();

	}

	public boolean checkCorrectSudoku(int room, String sudoku) {
		return (getMatches().get(room).getSudokuSolved().equals(sudoku)) ? true : false;
	}

	public void insertDurationOfGame(int room, String username, Date date) {

		Long time_to_inset = (date.getTime() > 0) ? date.getTime() - getMatch(room).getStarting_date().getTime() : 0;
		getMatches().get(room).getMatch().getDurations().put(getUser(username), time_to_inset);
	}

	public Difficulty getMatchDifficulty(int room) {
		return getMatches().get(room).getMatch().getDifficulty();
	}

	public void saveMatch(int room) {

		matchDAO.save(getMatches().get(room).getMatch());

		for (User user : getMatches().get(room).getMatch().getPlayers()) {
			user.getMatches().add(getMatches().get(room).getMatch());
			userDAO.update(user);

		}
	}

	public void removeMatch(int room) {
		getMatches().remove(room);
	}

	/**
	 * 
	 * @param indexOfTheLastRoomToShowInTheWindow
	 * 
	 * @return the rooms to show in a single page of the available room
	 */
	public Map<Integer, Room> getRoomInTheWindow(int indexOfTheLastRoomToShowInTheWindow) {
		Map<Integer, Room> windowed_room = new LinkedHashMap<>();

		int window_to_access = indexOfTheLastRoomToShowInTheWindow / MATCHES_TO_SHOW;
		int pos = (window_to_access * MATCHES_TO_SHOW) - MATCHES_TO_SHOW;
		int index = 0;
		int values = 0;
		for (Map.Entry<Integer, Room> entry : matches.entrySet()) {
			if (values == MATCHES_TO_SHOW)
				break;

			if (entry.getValue().isVisible()) {
				if (index < pos)
					index++;
				else {
					windowed_room.put(entry.getKey(), entry.getValue());
					values++;
				}
			}
		}
		return windowed_room;
	}

	public void setSudokuToSolveToTheRoom(int idRoom, String sudokuToSolve) {
		getMatches().get(idRoom).setSudokuToSolve(sudokuToSolve);
	}

	public void setSudokuSolvedToTheRoom(int idRoom, String sudokuSolved) {
		getMatches().get(idRoom).setSudokuSolved(sudokuSolved);
	}

	public int getNumberOfPlayersInTheRoom(int room) {
		return getMatches().get(room).getMatch().getPlayers().size();
	}

	public void removeRoom(int room) {
		getMatches().remove(room);
	}

	public void removePlayer(int room, String username) {
		getMatch(room).getPlayers().remove(getUser(username));
	}

	public String getCreatorOfTheRoom(int room) {
		return getMatches().get(room).getCreator().getUsername();
	}

	public int getTotalRoomPage() {
		return (int) Math.ceil((double) getVisibleMatchesSize() / getMatchesToShow());
	}

	public Long getNumberOfDisconnectedPlayer(int room) {
		return getMatches().get(room).getMatch().getDurations().entrySet().stream().filter(p -> p.getValue() == 0).collect(Collectors.counting());
	}

	/**
	 * 
	 * @param username
	 *            of user to looking for
	 * 
	 * @return the desired User
	 */
	public User getUser(String username) {
		return userDAO.getUser(username);
	}
	
	public SudokuPuzzles getRoomSudokus(int room) {
		return new SudokuPuzzles(getMatches().get(room).getSudokuToSolve(), getMatches().get(room).getSudokuSolved());
	}
}
