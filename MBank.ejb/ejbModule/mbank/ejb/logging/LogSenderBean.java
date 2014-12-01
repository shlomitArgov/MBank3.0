package mbank.ejb.logging;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mbank.ejb.logging.persistence.Log;

/**
 * Session Bean implementation class LogSenderBean
 */
@Stateless
public class LogSenderBean implements LogSender 
{
	private static final String CONNECTION_FACTORY = "ConnectionFactory";
	private static final String QUEUE_NAME = "java:/jms/queue/test";
	private InitialContext context;
	private Queue queue;
	private QueueConnectionFactory factory;
	private QueueConnection connection;
	private QueueSession session;
	private QueueSender sender;
	private ObjectMessage message;
	
	public LogSenderBean() {};
		
	@PostConstruct
	public void init()
	{
		try
		{
			context = new InitialContext();
			// look-up queue and factory
			queue = (Queue)context.lookup(QUEUE_NAME);
			factory = (QueueConnectionFactory) context.lookup(CONNECTION_FACTORY);
			connection = factory.createQueueConnection();
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE); //not transacted, duplicates are ok
			sender = session.createSender(queue);
			message = session.createObjectMessage();
			
			//start connection			
			connection.start();
			System.out.println("LogSenderBean.init()" + connection);
		} 
		catch (NamingException e)
		{
			e.printStackTrace();
		} 
		catch (JMSException e)
		{
			e.printStackTrace();
		}
	}
	
	@PreDestroy
	public void destroy()
	{
		//close sender, session and connection
		try
		{
			connection.stop();
			sender.close();
			session.close();
			connection.close();
		} 
		catch (JMSException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendLog(Log log)
	{
		System.out.println("LogSenderBean.sendLog()\nSending log to queue: " + log.getOperation());
		
		try
		{
			message.setObject(log);
			sender.send(message);
		} 
		catch (JMSException e)
		{
			e.printStackTrace();
		}
	}
}