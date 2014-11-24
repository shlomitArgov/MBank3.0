package mbank.ejb.logging;

import javax.ejb.Remote;

@Remote
public interface LogSender {
	public void sendLog(Log log);
}
