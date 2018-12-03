package it.unical.asde2018.sudoku.components.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Service;

import javafx.util.Pair;

@Service
public class PlayerEventsService {

	private Map<Integer,Boolean> saving = new ConcurrentHashMap<>();
	
	/**
	 * Store for each started room, a queue that contains the new status of the
	 * opponent player
	 */
	private Map<Integer, BlockingQueue<Pair<String, Integer>>> insertedNumberOfThePlayer = new HashMap<>();
	/**
	 * For each user that is playing here is stored the last number of number
	 * inserted
	 */
	private Map<String, Integer> lastNumberInserted = new HashMap<>();

	/**
	 * Identify if in a Room someone decide to leave before
	 */
	private Map<Integer, Boolean> disconnectedEvents = new HashMap<>();
	/**
	 * For each room maintain tracks of the number of player that exited from the
	 * review page
	 */
	private Map<Integer, BlockingQueue<Boolean>> playerExited = new HashMap<>();

	
	
	
	/**
	 * This method add the new player status if it is different from the previous call
	 * 
	 * @param room the room in which the user play
	 * @param user the username of the player
	 * @param currentNumberInserted 
	 * @return the status of the opponent
	 * @throws InterruptedException
	 */
	public Integer playerEvent(int room, String user, int currentNumberInserted) throws InterruptedException {

		if (!insertedNumberOfThePlayer.containsKey(room)) {
			insertedNumberOfThePlayer.put(room, new LinkedBlockingQueue<Pair<String, Integer>>());
		}

		if (!lastNumberInserted.containsKey(user)) {
			lastNumberInserted.put(user, Integer.valueOf(currentNumberInserted));
			insertedNumberOfThePlayer.get(room).add(new Pair<>(user, currentNumberInserted));
		} else if (lastNumberInserted.get(user).intValue() != currentNumberInserted) {
			lastNumberInserted.put(user, Integer.valueOf(currentNumberInserted));
			insertedNumberOfThePlayer.get(room).add(new Pair<>(user, currentNumberInserted));
		}

		/**
		 * If there is a new event from the opponent it will be retrieve. Otherwise -1
		 * was return
		 */
		return (!insertedNumberOfThePlayer.get(room).peek().getKey().equals(user))
				? insertedNumberOfThePlayer.get(room).take().getValue()
				: -1;
	}

	public void addDisconnectedEvent(int room) throws InterruptedException {
		disconnectedEvents.computeIfAbsent(room, val -> true);
	}

	public boolean getDisconnectedPlayerEvent(int room) {
		return disconnectedEvents.containsKey(room);
	}

	public void insertPlayerExitedFromReviewPage(int room) throws InterruptedException {
		if (!playerExited.containsKey(room)) {
			playerExited.put(room, new LinkedBlockingQueue<Boolean>());
		}

		playerExited.get(room).put(true);
	}

	public int getSizeOfPlayerExitedFromReviewPage(int room) {
		return (!playerExited.containsKey(room)) ? 0 : playerExited.get(room).size();
	}

	public void removeDataOfPlayersExitedFromReviewPage(int room) {
		playerExited.remove(room);
	}

	public void removePlayerLastInserted(String user) {
		lastNumberInserted.remove(user);
	}

	public void removeRoomData(int room) {
		insertedNumberOfThePlayer.remove(room);
		disconnectedEvents.remove(room);
	}

	public void insertInSaving (int room)
	{
		if (!saving.containsKey(room))
			saving.put(room, true);
	}
	
	public boolean isSaving (int room)
	{
		return saving.containsKey(room);
	}
}
