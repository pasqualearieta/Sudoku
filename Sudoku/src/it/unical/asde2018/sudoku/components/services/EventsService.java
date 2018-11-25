package it.unical.asde2018.sudoku.components.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Service;

import javafx.util.Pair;

@Service
public class EventsService {

	private Map<Integer, BlockingQueue<Pair<String, Integer>>> events = new HashMap<>();
	private Map<Integer, Boolean> specialEvents = new HashMap<>();

	public Integer nextEvent(int room, String user, int number_inserted) throws InterruptedException {

		if (!events.containsKey(room)) {
			events.put(room, new LinkedBlockingQueue<Pair<String, Integer>>());
		}

		events.get(room).add(new Pair<>(user, number_inserted));

		if (!events.get(room).peek().getKey().equals(user))
			return events.get(room).take().getValue();
		else
			return 0;

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

}
