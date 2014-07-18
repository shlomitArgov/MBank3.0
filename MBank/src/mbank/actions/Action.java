/**
 * 
 */
package mbank.actions;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import mbank.database.beans.Account;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.Property;
import mbank.database.beans.enums.ClientAttributes;
import mbank.database.beans.enums.ClientType;
import mbank.database.beans.enums.SystemProperties;
import mbank.database.managersImpl.AccountDBManager;
import mbank.database.managersImpl.ActivityDBManager;
import mbank.database.managersImpl.ClientDBManager;
import mbank.database.managersImpl.DepositDBManager;
import mbank.database.managersImpl.PropertyDBManager;
import mbank.database.managersInterface.AccountManager;
import mbank.database.managersInterface.ActivityManager;
import mbank.database.managersInterface.ClientManager;
import mbank.database.managersInterface.DepositManager;
import mbank.database.managersInterface.PropertyManager;
import mbankExceptions.MBankException;

/**
 * @author Shlomit Argov
 *
 */
public abstract class Action
{
	private Connection con;
	private long id;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	public Action(Connection con, long id)
	{
		this.con = con;
		this.id = id;
	}
	
	/**
	 * Updates one or more of the client attributes: Address, Email, Phone.
	 * @param clientId
	 * @param details
	 * @return true upon success, false otherwise
	 * @throws MBankException 
	 */
	public boolean updateClientDetails(String clientId, TableValue... details) throws MBankException 
	{
		ClientManager clientManager = new ClientDBManager();
		//get client from DB
		Client c = new Client(Long.parseLong(clientId), null, null, null, null, null, null, null);
		c = clientManager.query(c, this.getCon());
		
		//update values
		updateValues(c, details);
		//execute update (commit to DB)
		try
		{
		clientManager.update(c, this.getCon());
		} catch (MBankException e)
		{
			//write to log
			return false;
		}
		return true;
	}
 
	protected void updateValues(Client c, TableValue[] details) throws MBankException
	{
		for (int i = 0; i < details.length; i++)
		{
			if(details[i].getColumnName().equals(ClientAttributes.ADDRESS.getAttribute()))
			{
				c.setAddress(details[i].getColumnValue());
			}
			else if(details[i].getColumnName().equals(ClientAttributes.EMAIL.getAttribute()))
			{
				c.setEmail(details[i].getColumnValue());
			}
			else if(details[i].getColumnName().equals(ClientAttributes.PHONE.getAttribute()))	
			{
				c.setPhone(details[i].getColumnValue());
			}
			else
			{
				throw new MBankException("Cannot update client attribute " + details[i].getColumnName());
			}
		}				
	}
	//helper method: get client type according to initial deposit size
		protected ClientType getClientType(double deposit) throws MBankException
		{
			PropertyManager propertyManager = new PropertyDBManager();
			double regularDepositRate = 0;
			try
			{
				regularDepositRate = Double.parseDouble(propertyManager.query(SystemProperties.REGULAR_DEPOSIT_RATE.getPropertyName(), con).getProp_value());

			}catch (NumberFormatException e)
			{
				throw new MBankException("System property format error (expected double)\n" + e.getLocalizedMessage());
			}
			
			double goldDepositRate = Double.parseDouble(propertyManager.query(SystemProperties.GOLD_DEPOSIT_RATE.getPropertyName(), con).getProp_value());
			
			if (deposit > 0 && deposit <= regularDepositRate)
			{
				return ClientType.REGULAR;
			}
			if (deposit <= goldDepositRate)
			{
				return ClientType.GOLD;
			} else
			{
				return ClientType.PLATINUM;
			}
		}

	public Client viewClientDetails(long clientId) throws MBankException
	{
		//Clients can only view their own details - this method is overridden in ClientAction to validate this requirement
		return queryClientDetails(clientId);
		
	}
	

	protected Client queryClientDetails(long clientId) throws MBankException {
		ClientManager clientManager = new ClientDBManager();
		Client client = null;
		try
		{
			client = clientManager.query(clientId, con);	
		}
		catch(MBankException e)
		{
			throw new MBankException("Failed to retrieve client with ID [" + clientId + "]\nPlease consult a system administrator to see if a client with this ID exists");
		}
		return client;		
		}

	/**
	 * 
	 * @param client
	 * @return account details (String) or null if retrieval of details from DB failed
	 */
	public String viewAccountDetails(Client client)
	{
		return queryClientAccount(client.getClient_id());
		
	}
	protected String queryClientAccount(long clientId) {
		AccountManager accountManager = new AccountDBManager();
		try {
			Account account = accountManager.queryAccountByClient(clientId, this.getCon());
			return account.toString();
		} catch (MBankException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		return null;
	}

	public String viewClientDeposits(long clientId)
	{
		return queryClientDeposits(clientId);
	}

	protected String queryClientDeposits(long clientId) {
		DepositManager depositManager = new DepositDBManager();
		List<Deposit> deposits = depositManager.queryDepositsByClient(clientId, this.getCon());
		String string = Arrays.toString(deposits.toArray());
		return string;
	}
	public String viewClientctivities(long clientId)
	{
		return queryClientActivities(clientId);
	}

	protected String queryClientActivities(long clientId) {
		ActivityManager activityManager = new ActivityDBManager();
		List<Activity> clientActivities = activityManager.queryByClientId(clientId, this.getCon());
		return Arrays.toString(clientActivities.toArray());
	}
	public String viewSystemProperty(String propertyName) throws MBankException
	{
		if(SystemProperties.validateString(propertyName))
		{
			PropertyManager propertyManager = new PropertyDBManager();
			Property prop = propertyManager.query(propertyName, this.getCon());	
			return prop.getProp_value();
		}
		else
		{
			throw new MBankException("Unknown system property: " + propertyName);
		}
	}
	public abstract boolean logout();

	public Connection getCon()
	{
		return con;
	}
}
