package mbank.ejb.logging;

import javax.ejb.Remote;
import mbank.ejb.logging.persistence.Log;

@Remote
public interface LogSender 
{
	public void sendLog(Log log);
}
