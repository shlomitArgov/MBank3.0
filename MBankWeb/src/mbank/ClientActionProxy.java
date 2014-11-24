/**
 * 
 */
package mbank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mbank.actions.ClientActionInterface;
import mbank.actions.TableValue;
import mbank.database.beans.Account;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.Property;
import mbank.exceptions.MBankException;

/**
 * @author Shlomit Argov
 *
 */
public class ClientActionProxy implements ClientActionInterface
{
	private ClientActionInterface clientAction;
	private long clientId;
	
	public ClientActionProxy(ClientActionInterface clientAction, long clientId)
	{
		this.clientAction = clientAction;
		this.clientId = clientId;
	}

	public long getClientId()
	{
		return clientId;
	}

	@Override
	public void withdraw(double amount) throws MBankException
	{
		this.clientAction.withdraw(amount);
	}

	@Override
	public void deposit(double amount) throws MBankException
	{
		this.clientAction.deposit(amount);
		
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
		return this.viewSystemProperty(propertyName);
	}
}
