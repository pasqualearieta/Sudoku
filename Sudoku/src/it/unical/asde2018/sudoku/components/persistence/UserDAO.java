package it.unical.asde2018.sudoku.components.persistence;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import it.unical.asde2018.sudoku.model.Match;
import it.unical.asde2018.sudoku.model.User;

@Repository
public class UserDAO extends UtilDAO {

	/**
	 * This method check if the username chosen by the new user is available.
	 * 
	 * @param user
	 * 
	 * @return true if the username is available, false if the username is
	 *         already in use
	 */
	public boolean availableUsername(User user) {
		Session session = sessionFactory.openSession();
		Query<User> query = session.createQuery("from User as u where u.username=:usr", User.class).setParameter("usr", user.getUsername());
		boolean result = query.uniqueResult() != null;
		session.close();
		return !result;
	}

	/**
	 * This method check if an user is already registered into the system
	 * 
	 * @param user
	 * @return true if the user is already stored into the system, false
	 *         otherwise
	 */
	public boolean exists(User user) {
		Session session = sessionFactory.openSession();
		Query<User> query = session.createQuery("from User as u where u.username=:usr and u.password=:p", User.class).setParameter("usr", user.getUsername()).setParameter("p", user.getPassword());

		boolean result = query.uniqueResult() != null;
		session.close();
		return result;
	}

	/**
	 * Method that get the user with the username equals to the desired
	 * username
	 * 
	 * @param username
	 *            of the desired user
	 * 
	 * @return the user to looking for
	 */
	public User getUser(String username) {
		Session session = sessionFactory.openSession();

		Query<User> query = session.createQuery("from User as u where u.username=:usr", User.class).setParameter("usr", username);

		User result = query.uniqueResult();

		for (Match m : result.getMatches())
			m.toString();

		session.close();
		return result;
	}

}