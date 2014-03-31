package com.winchannel.core.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.winchannel.core.utils.SpringContext;


/**
 * @author xianghui
 *
 */
public class HibernatePersister extends HibernateBaseDao {
	
	public static final String DEFAULT_SESSION_FACTORY_NAME = "sessionFactory";
	private Session session;
	private Transaction tx;
	
	public HibernatePersister() {
		this(DEFAULT_SESSION_FACTORY_NAME);
	}
	
	public HibernatePersister(String sessionFactoryName) {
		SessionFactory sessionFactory = (SessionFactory) SpringContext.getBean(sessionFactoryName);
		super.setSessionFactory(sessionFactory);
		session = sessionFactory.openSession();
	}

	@Override
	public Session getSession() {
		return session;
	}
	
	public void beginTransaction() {
		tx = session.beginTransaction();
	}

	public void commit() {
		tx.commit();
	}
	
	public void rollback() {
		if (tx != null) {
			tx.rollback();
			tx = null;
		}
	}
	
	public void close() {
		if (session != null) {
			session.close();
			session = null;
		}
	}

}
