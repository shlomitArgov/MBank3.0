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
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "java:/jms/queue/test")
		})
public class LogMDB implements MessageListener {
	
	@EJB
	private LogDAO logDAOStub;
	
    public LogMDB(){}
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) 
    {
		if (message instanceof ObjectMessage)
		{
			logDAOStub.create((Log) message);
		}
		else
		{
			System.out.println("LogMDB.onMessage()\nUnknown message type + " + message.getClass().getName());
		}
    }
}
