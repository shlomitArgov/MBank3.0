package mbank.ejb.logging;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class LogSenderBean
 */
@Stateless
public class LogSenderBean implements LogSender 
{
	public LogSenderBean() {}

	@PostConstruct
	public void init()
	{
		
	}
	
	@PreDestroy
	public void destroy()
	{
		
	}
	
	@Override
	public void sendLog(Log log)
	{
		System.out.println("LogSenderBean.sendLog()\n" + log.getOperation());
	}
}