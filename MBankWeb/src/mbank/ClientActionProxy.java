/**
 * 
 */
package mbank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mbank.actions.ClientActionInterface;
import mbank.actions.TableValue;
import mbank.database.beans.Account;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.Property;
import mbank.ejb.logging.LogSender;
import mbank.ejb.logging.persistence.Log;
import mbank.exceptions.MBankException;

/**
 * @author Shlomit Argov
 *
 */
public class ClientActionProxy implements ClientActionInterface
{
	private ClientActionInterface clientAction;
	private long clientId;
	@EJB
	private LogSender logSender;
	
	public ClientActionProxy(ClientActionInterface clientAction, long clientId)
	{
		this.clientAction = clientAction;
		this.clientId = clientId;
		
		//get LogSender bean
		try
		{
			InitialContext context = new InitialContext();
			logSender = (LogSender) context.lookup("java:global/MBank.app/MBank.ejb/LogSenderBean!mbank.ejb.logging.LogSender");
		} catch (NamingException e)
		{
			e.printStackTrace();
		}
	}

	public long getClientId()
	{
		return clientId;
	}

	@Override
	public void withdraw(double amount) throws MBankException
	{
		System.out.println("ClientActionProxy.withdraw()"); 
		
		try
		{
			this.clientAction.withdraw(amount);
			// log action
			System.out.println("ClientActionProxy.withdraw() - logSender: " + logSender);
			logSender.sendLog(new Log(clientId, "Widthdrew " + amount + " from client[ id = " + clientId + "]'s account"));
		}
		catch (MBankException e)
		{
			throw e;
		}
	}

	@Override
	public void deposit(double amount) throws MBankException
	{
		System.out.println("ClientActionProxy.deposit()");
		try
		{
			this.clientAction.deposit(amount);
			logSender.sendLog(new Log(clientId, "Deposited " + amount + "$ to client[id = " + clientId + "]'s account"));
		} catch (MBankException e)
		{
			throw e;
		}
		
	}

	@Override
	public long getClientID()
	{
		return this.clientId;
	}

	@Override
	public void updateClientDetails(TableValue... details) throws MBankException
	{
		this.clientAction.updateClientDetails(details);
		
	}

	@Override
	public Account viewAccountDetails() throws MBankException
	{
		return this.clientAction.viewAccountDetails();
	}

	@Override
	public Client viewClientDetails() throws MBankException
	{
		return this.clientAction.viewClientDetails();
	}

	@Override
	public void preOpenDeposit(long depositId) throws MBankException
	{
		this.clientAction.preOpenDeposit(depositId);
	}

	@Override
	public Deposit createNewDeposit(double depositAmount, Date closeDate) throws MBankException
	{
		return this.createNewDeposit(depositAmount, closeDate);
	}

	@Override
	public boolean logout()
	{
		return this.clientAction.logout();
	}

	@Override
	public List<Activity> viewClientActivities() throws MBankException
	{
		return this.clientAction.viewClientActivities();
	}

	@Override
	public List<Deposit> viewClientDeposits() throws MBankException
	{
		return this.clientAction.viewClientDeposits();
	}

	@Override
	public ArrayList<Property> viewSystemProperties() throws MBankException
	{
		return this.clientAction.viewSystemProperties();
	}

	@Override
	public String viewSystemProperty(String propertyName) throws MBankException
	{
		return this.clientAction.viewSystemProperty(propertyName);
	}
}
