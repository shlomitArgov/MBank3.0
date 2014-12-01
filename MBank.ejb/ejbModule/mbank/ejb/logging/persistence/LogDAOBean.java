package mbank.ejb.logging.persistence;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class LogDAO
 */
@Stateless
public class LogDAOBean implements LogDAO {

    /**
     * Default constructor. 
     */
    public LogDAOBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public Log create(Log log)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Log> getAllLog()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Log> getLogsByClientId(Long clientId)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
