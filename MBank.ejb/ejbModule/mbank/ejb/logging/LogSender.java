package mbank.ejb.logging;

import javax.ejb.Local;
import javax.ejb.Remote;

import mbank.ejb.logging.persistence.Log;

//@Remote
@Local
public interface LogSender 
{
	public void sendLog(Log log);
}
