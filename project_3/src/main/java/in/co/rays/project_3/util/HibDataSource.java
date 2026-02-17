package in.co.rays.project_3.util;

import java.util.ResourceBundle;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;

/**
 * Hibernate DataSource is provides the object of session factory and session
 * 
 * 
 * @author Akbar Mansuri
 *
 */
public class HibDataSource {
	private static SessionFactory sessionFactory = null;
	

	public static SessionFactory getSessionFactory() {

		if (sessionFactory == null) {

			ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project_3.bundle.system");

			String jdbcUrl = System.getenv("DATABASE_URL");
			if (jdbcUrl == null || jdbcUrl.trim().isEmpty()) {
				jdbcUrl = rb.getString("url");
			}

			sessionFactory = new Configuration().configure().setProperty("hibernate.connection.url", jdbcUrl)
					.buildSessionFactory();
		}
		return sessionFactory;
	}

	public static Session getSession() {

		Session session = getSessionFactory().openSession();
		return session;

	}

	public static void closeSession(Session session) {

		if (session != null) {
			session.close();
		}
	}
	
	public static void handleException(HibernateException e) throws ApplicationException {
		
		throw new ApplicationException("Data Base Server is down Try After some time....." );
	}
}
