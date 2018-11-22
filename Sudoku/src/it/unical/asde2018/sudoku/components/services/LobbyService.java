package it.unical.asde2018.sudoku.components.services;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import it.unical.asde2018.sudoku.logic.Room;
import it.unical.asde2018.sudoku.logic.util.Difficulty;
import it.unical.asde2018.sudoku.model.Match;
import it.unical.asde2018.sudoku.model.User;

@Service
public class LobbyService {

	private Map<Integer, Room> matches = new LinkedHashMap<>();

	private static int idRoom = 0;

	public LobbyService() {

	}

	public void createRoom(User creator, Difficulty difficulty, String name) {

		Match match = new Match(creator, difficulty, name);
		Room room = new Room(match, creator.getId());

		matches.put(getIdRoom(), room);

		// TODO reindirizzare a pagina wait
	}

	public void joinRoom(User player, int idRoomToJoin) {
		matches.get(idRoomToJoin).getMatch().getPlayers().add(player);

		// TODO reindirizzare a pagina gioco
	}

	public static int getIdRoom() {
		return ++idRoom;
	}
}
