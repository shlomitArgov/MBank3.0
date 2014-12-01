package mbank.ejb.logging.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class LogDAO
 */
@Stateless
public class LogDAOBean implements LogDAO {

	@PersistenceContext(unitName = "LogDB")
	private EntityManager entityManager;
	
    public LogDAOBean() {}

	@Override
	public Log create(Log log)
	{
		entityManager.persist(log);
		return log;
	}

	@Override
	public List<Log> getAllLog()
	{	
		return entityManager.createNamedQuery("LogGetAll", Log.class).getResultList();
	}

	@Override
	public List<Log> getLogsByClientId(Long clientId)
	{
		return entityManager.createNamedQuery("LogGetByClientId", Log.class).setParameter("clientId", clientId).getResultList();
	}
}
