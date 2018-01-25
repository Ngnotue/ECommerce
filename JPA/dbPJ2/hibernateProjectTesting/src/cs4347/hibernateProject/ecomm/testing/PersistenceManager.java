package cs4347.hibernateProject.ecomm.testing;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Interesting method of implementing a Singleton.
 */
public class PersistenceManager {

	private static EntityManagerFactory emFactory;

	private PersistenceManager()
	{}

	public synchronized static EntityManager getEntityManager()
	{
		if(emFactory == null) {
			emFactory = Persistence.createEntityManagerFactory("jpa-simple_company");
		}
		return emFactory.createEntityManager();
	}

	public synchronized static void close()
	{
		if(emFactory != null) {
			emFactory.close();
			emFactory = null;
		}
	}
}