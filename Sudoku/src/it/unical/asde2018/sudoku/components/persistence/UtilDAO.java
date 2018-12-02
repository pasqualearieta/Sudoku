package it.unical.asde2018.sudoku.components.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UtilDAO
{
	@Autowired
	protected SessionFactory sessionFactory;

	public void update(Object object) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(object);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}

		session.close();
	}
	
	public void save(Object object) {

		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.save(object);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}

		session.close();

	}


}
