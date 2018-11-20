package it.unical.asde2018.sudoku.components.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unical.asde2018.sudoku.components.persistence.UserDAO;
import it.unical.asde2018.sudoku.model.User;

@Service
public class LogInService {

	@Autowired
	private UserDAO userDAO;

	public boolean login(String username, String password) {
		return userDAO.exists(new User(username, password));
	}

}
