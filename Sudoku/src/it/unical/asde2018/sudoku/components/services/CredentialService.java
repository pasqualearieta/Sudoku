package it.unical.asde2018.sudoku.components.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unical.asde2018.sudoku.components.persistence.UserDAO;
import it.unical.asde2018.sudoku.model.User;

@Service
public class CredentialService {

	@Autowired
	private UserDAO userDAO;

	/**
	 * Method that check if the user that try to login in the system is
	 * registered in the system
	 * 
	 * @param username
	 *            of the user
	 * @param password
	 *            of the user
	 * @return
	 */
	public boolean login(String username, String password) {
		return userDAO.exists(new User(username, password));
	}

	/**
	 * Method that store the new user
	 * 
	 * @param username
	 *            chosen by the new user
	 * @param password
	 *            chosen by the new user
	 * @return
	 */
	public boolean registerUser(String username, String password) {
		User newUser = new User(username, password);
		if (!userDAO.availableUsername(newUser))
			return false;
		userDAO.save(newUser);
		return true;
	}

	public User getUser(String username) {
		return userDAO.getUser(username);
	}

}
