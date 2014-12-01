package mbank.ejb.logging.persistence;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Message-Driven Bean implementation class for: LogMDB
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/test") })
public class LogMDB implements MessageListener
{

	private static final String UNKNOWN_MESSAGE_TYPE = "LogMDB.onMessage()\nUnknown message type + ";
	@EJB
	private LogDAO logDAOStub;

	public LogMDB()
	{
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message)
	{
		try
		{
			if (message instanceof ObjectMessage)
			{
				Object o = ((ObjectMessage) message).getObject();
				if (o instanceof Log)
				{
					logDAOStub.create((Log) o);
				} 
				else
				{
					System.out.println(UNKNOWN_MESSAGE_TYPE + message.getClass().getName());
				}
			} else
			{
				System.out.println(UNKNOWN_MESSAGE_TYPE + message.getClass().getName());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
