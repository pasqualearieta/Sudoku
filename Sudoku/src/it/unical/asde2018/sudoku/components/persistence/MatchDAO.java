package it.unical.asde2018.sudoku.components.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.unical.asde2018.sudoku.model.Match;

@Repository
public class MatchDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void save(Match match) {

		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.save(match);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}

		session.close();

	}

}
