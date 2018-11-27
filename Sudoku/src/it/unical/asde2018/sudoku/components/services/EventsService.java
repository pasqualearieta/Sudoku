package it.unical.asde2018.sudoku.components.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.javatuples.Pair;
import org.springframework.stereotype.Service;

@Service
public class EventsService {

	private Map<Integer, BlockingQueue<Pair<String, Integer>>> events = new HashMap<>();
	private Map<Integer, Boolean> specialEvents = new HashMap<>();
	private Map<String, Integer> lastInserted = new HashMap<>();

	public Integer nextEvent(int room, String user, int number_inserted) throws InterruptedException {

		if (!events.containsKey(room)) {
			events.put(room, new LinkedBlockingQueue<Pair<String, Integer>>());
		}

		if (!lastInserted.containsKey(user)) {
			lastInserted.put(user, Integer.valueOf(number_inserted));
			events.get(room).add(new Pair<>(user, number_inserted));
		} else if (lastInserted.get(user).intValue() != number_inserted) {
			lastInserted.put(user, Integer.valueOf(number_inserted));
			events.get(room).add(new Pair<>(user, number_inserted));
		}
		if (!events.get(room).peek().getValue0().equals(user))
			return events.get(room).take().getValue1();
		else
			return -1;
	}

	public void addSpecialEvent(int room) throws InterruptedException {
		if (!specialEvents.containsKey(room)) {
			specialEvents.put(room, true);
		}

		specialEvents.put(room, true);
	}

	public boolean getSpecialEvent(int room) {
		return specialEvents.containsKey(room);
	}
	
	public void removeData(int room) {
		events.remove(room);
		specialEvents.remove(room);
	}
	
	public void removeUserData(String user) {
		lastInserted.remove(user);
	}

}
