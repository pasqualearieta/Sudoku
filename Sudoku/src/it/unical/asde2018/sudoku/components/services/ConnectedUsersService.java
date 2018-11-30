package it.unical.asde2018.sudoku.components.services;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Service;

@Service
public class ConnectedUsersService {

	private Queue<String> connectedUsers = new LinkedBlockingQueue<>();

	public void addConnectedUser(String usedID) {
		connectedUsers.add(usedID);
	}

	public void removeConnectedUser(String userID) {
		connectedUsers.remove(userID);
	}

	public Queue<String> getConnectedUsers() {
		return connectedUsers;
	}
}
