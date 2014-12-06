package mbank.ejb.logging.persistence;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class LogDAO
 */
@Stateless
public class LogDAOBeanRemote implements LogDAORemote {

	@EJB
	private LogDAOLocal localDaoStub;
	
    public LogDAOBeanRemote() {}

	@Override
	public Log create(Log log)
	{
		localDaoStub.create(log);
		return log;
	}

	@Override
	public List<Log> getAllLog()
	{	
		return localDaoStub.getAllLog();
	}

	@Override
	public List<Log> getLogsByClientId(Long clientId)
	{
		return localDaoStub.getLogsByClientId(clientId);
	}
}
