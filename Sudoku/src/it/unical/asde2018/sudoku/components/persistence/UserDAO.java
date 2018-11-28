package it.unical.asde2018.sudoku.components.persistence;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.unical.asde2018.sudoku.model.Match;
import it.unical.asde2018.sudoku.model.User;

@Repository
public class UserDAO{

	@Autowired
	private SessionFactory sessionFactory;

	@PostConstruct
	private void init() {
		save(new User("andrea", "andrea"));
	}
	
	public void update(User object) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try
		{
			transaction = session.beginTransaction();
			session.update(object);
			transaction.commit();
		} catch (Exception e)
		{
			transaction.rollback();
		}

		session.close();
	}
	
	public void save(User object) {

		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try
		{
			transaction = session.beginTransaction();
			session.save(object);
			transaction.commit();
		} catch (Exception e)
		{
			transaction.rollback();
		}

		session.close();

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
		
		//lazy initialization
		//result.setMatches(new ArrayList<>());
		result.getMatches().size(); //lazy
		/*for(Match m : result.getMatches())
			System.out.println(m.toString());
		*/
		session.close();
		return result;
	}
		
}