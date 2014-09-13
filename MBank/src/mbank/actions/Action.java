/**
 * 
 */
package mbank.actions;

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
import mbank.exceptions.MBankException;

/**
 * @author Shlomit Argov
 *
 */
public abstract class Action
{
//	private Connection con;
	private long clientId;
	private static final String TMP_STR = "tmpVal";
	/**
	 * @return the clientId
	 */
	public long getClientId() {
		return clientId;
	}

	public Action(long id)
	{
//		this.con = con;
		this.clientId = id;
	}
	
	/**
	 * Updates one or more of the client attributes: Address, Email, Phone.
	 * @param clientId
	 * @param details
	 * @return true upon success, false otherwise
	 * @throws MBankException 
	 */
	public void updateClientDetails(String clientId, TableValue... details) throws MBankException 
	{
		ClientManager clientManager = new ClientDBManager();
		/* get client from DB */
		Client c = new Client(Long.parseLong(clientId), TMP_STR, TMP_STR, null, null, null, null, null);
//		c = clientManager.query(c, this.getCon());
		c = clientManager.query(c);
		
		/* update values */
		updateValues(c, details);
		if(c.getPassword().equalsIgnoreCase(TMP_STR) || c.getClient_name().equalsIgnoreCase(TMP_STR))
		{
			throw new MBankException("Client and password fields must not be empty");
		}
		/* execute update (commit to DB) */
		try
		{
//			clientManager.update(c, this.getCon());
			clientManager.update(c);
		} catch (MBankException e)
		{
			throw e;
		}
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
				throw new MBankException("Cannot update client attribute '" + details[i].getColumnName() + "' - Unauthorized action") ;
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
//				regularDepositRate = Double.parseDouble(propertyManager.query(SystemProperties.REGULAR_DEPOSIT_RATE.getPropertyName(), con).getProp_value());
				regularDepositRate = Double.parseDouble(propertyManager.query(SystemProperties.REGULAR_DEPOSIT_RATE.getPropertyName()).getProp_value());

			}catch (NumberFormatException e)
			{
				throw new MBankException("System property format error (expected double)\n" + e.getLocalizedMessage());
			}
			
//			double goldDepositRate = Double.parseDouble(propertyManager.query(SystemProperties.GOLD_DEPOSIT_RATE.getPropertyName(), con).getProp_value());
			double goldDepositRate = Double.parseDouble(propertyManager.query(SystemProperties.GOLD_DEPOSIT_RATE.getPropertyName()).getProp_value());
			
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
//			client = clientManager.query(clientId, con);
			client = clientManager.query(clientId);
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
	 * @throws MBankException 
	 */
	public Account viewAccountDetails(Client client) throws MBankException
	{
		return queryClientAccount(client.getClient_id());
		
	}
	protected Account queryClientAccount(long clientId) throws MBankException 
	{
		AccountManager accountManager = new AccountDBManager();
//		Account account = accountManager.queryAccountByClient(clientId, this.getCon());
		Account account = accountManager.queryAccountByClient(clientId);
		return account;
	}

	public List<Deposit> viewClientDeposits(long clientId) throws MBankException
	{
		return queryClientDeposits(clientId);
	}

	protected List<Deposit> queryClientDeposits(long clientId) throws MBankException {
		DepositManager depositManager = new DepositDBManager();
//		List<Deposit> deposits = depositManager.queryDepositsByClient(clientId, this.getCon());
		List<Deposit> deposits = depositManager.queryDepositsByClient(clientId);
		return deposits;
	}
	
	public List<Activity> viewClientActivities(long clientId) throws MBankException
	{
		return queryClientActivities(clientId);
	}

	protected List<Activity> queryClientActivities(long clientId) throws MBankException {
		ActivityManager activityManager = new ActivityDBManager();
//		List<Activity> clientActivities = activityManager.queryByClientId(clientId, this.getCon());
		List<Activity> clientActivities = activityManager.queryByClientId(clientId);
		return clientActivities;
	}
	public String viewSystemProperty(String propertyName) throws MBankException
	{
		if(SystemProperties.validateString(propertyName))
		{
			PropertyManager propertyManager = new PropertyDBManager();
//			Property prop = propertyManager.query(propertyName, this.getCon());
			Property prop = propertyManager.query(propertyName);	

			return prop.getProp_value();
		}
		else
		{
			throw new MBankException("Unknown system property: " + propertyName);
		}
	}
	public abstract boolean logout();

//	public Connection getCon()
//	{
//		return con;
//	}
}
