package it.unical.asde2018.sudoku.components.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class GameStartService {

	/**
	 * Indicate which of the available rooms can be started
	 */
	private Map<Integer, Boolean> roomToBeStarted = new HashMap<>();

	/**
	 * Indicates which of the available room must be deleted
	 */
	private Map<Integer, Boolean> roomToBeDeleted = new HashMap<>();

	/**
	 * Method that change the status of the room to playable.
	 * 
	 * @param room
	 *            Room that can be playable.
	 */
	public void putStartEvent(int room) {
		if (!roomToBeStarted.containsKey(room))
			roomToBeStarted.put(room, true);
	}

	/**
	 * Method that ensure if a room can be playable.
	 * 
	 * @param room
	 * @return true if the room can be started, false otherwise.
	 */
	public boolean getStartEvent(int room) {
		return !roomToBeStarted.containsKey(room) ? false : roomToBeStarted.get(room);
	}

	/**
	 * Method that change the status of the room to deleted.
	 * 
	 * @param room
	 *            Room that can be deleted.
	 */
	public void putDeleteEvent(int room) {
		if (!roomToBeDeleted.containsKey(room))
			roomToBeDeleted.put(room, true);
	}

	/**
	 * Method that ensure if a room can be deleted.
	 * 
	 * @param room
	 * @return true if the room can be deleted, false otherwise.
	 */
	public boolean getDeleteEvent(int room) {
		return !roomToBeDeleted.containsKey(room) ? false : roomToBeDeleted.get(room);
	}

}
