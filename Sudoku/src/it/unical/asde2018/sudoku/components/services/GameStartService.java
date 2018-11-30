package it.unical.asde2018.sudoku.components.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class GameStartService {

	private Map<Integer, Boolean> roomToBeStarted = new HashMap<>();
	private Map<Integer, Boolean> roomToBeDeleted = new HashMap<>();

	public void putStartEvent(int room) {
		if (!roomToBeStarted.containsKey(room)) {
			roomToBeStarted.put(room, true);
		}
	}

	public boolean getStartEvent(int room) {
		return !roomToBeStarted.containsKey(room) ? false : roomToBeStarted.get(room);
	}

	public void putDeleteEvent(int room) {
		if (!roomToBeDeleted.containsKey(room)) {
			roomToBeDeleted.put(room, true);
		}
	}

	public boolean getDeleteEvent(int room) {
		return !roomToBeDeleted.containsKey(room) ? false : roomToBeDeleted.get(room);
	}

}
