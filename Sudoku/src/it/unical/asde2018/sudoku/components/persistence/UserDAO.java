package it.unical.asde2018.sudoku.components.persistence;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import it.unical.asde2018.sudoku.model.Match;
import it.unical.asde2018.sudoku.model.User;

@Repository
public class UserDAO extends UtilDAO{


	@PostConstruct
	private void init() {
		save(new User("andrea", "andrea"));
		save(new User("a", "a"));
		save(new User("q", "q"));
	}


	public boolean availableUsername(User user) {
		Session session = sessionFactory.openSession();
		Query<User> query = session.createQuery("from User as u where u.username=:usr", User.class).setParameter("usr",
				user.getUsername());
		boolean result = query.uniqueResult() != null;
		session.close();
		return !result;
	}

	public boolean exists(User user) {
		Session session = sessionFactory.openSession();
		Query<User> query = session.createQuery("from User as u where u.username=:usr and u.password=:p", User.class)
				.setParameter("usr", user.getUsername()).setParameter("p", user.getPassword());

		boolean result = query.uniqueResult() != null;
		session.close();
		return result;
	}

	public User getUser(String username) {
		Session session = sessionFactory.openSession();

		Query<User> query = session.createQuery("from User as u where u.username=:usr", User.class).setParameter("usr",
				username);

		User result = query.uniqueResult();

		result.getMatches().size(); // lazy
		
		
		for(Match m: result.getMatches())
			System.out.println("match: " + m.toString());
		
		session.close();
		return result;
	}

}