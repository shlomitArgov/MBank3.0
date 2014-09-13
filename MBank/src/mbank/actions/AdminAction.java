/**
 * 
 */
package mbank.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mbank.database.beans.Account;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.Property;
import mbank.database.beans.enums.ActivityType;
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
/**
 * @author Shlomit
 *
 */
public class AdminAction extends Action
{
	public AdminAction(long adminId)
	{
		super(adminId);
	}

	/* (non-Javadoc)
	 * @see mbank.actions.Action#updateClientDetails(java.lang.String, mbank.actions.tableValue[][])
	 */
	

	
	//Admins can also update client type
	@Override
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
			else if(details[i].getColumnName().equals(ClientAttributes.CLIENT_TYPE.getAttribute()))
			{
				c.setType(ClientType.getEnumFromString(details[i].getColumnValue()));
				//TODO change client's account limit
			}
			else if(details[i].getColumnName().equals(ClientAttributes.COMMENT.getAttribute()))
			{
				c.setComment(details[i].getColumnValue());
			}
			else
			{
				throw new MBankException("Cannot update client attribute " + details[i].getColumnName());
			}
		}		
	}

	@Override
	public Account viewAccountDetails(Client client)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean logout()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public long addNewClient(String clientName, char[] clientPassword,
			String clientAddress, String clientEmail, String clientPhone,
			double deposit) throws MBankException
	{
		// Make sure that the client-name-password combination is unique
		ClientManager clientManager = new ClientDBManager();
		
		testUniqueClientnamePasswordCombination(clientManager, clientName, String.valueOf(clientPassword));
		
		// add the new client
		ClientType clientType = getClientType(deposit); 
		double creditLimit = getCreditLimit(clientType);
		if (deposit < 0)
//		if (deposit < creditLimit) //this makes no sense since for Platinum clients - the limit is infinity
		{
			throw new MBankException("Initial deposit must be non-negative");
		}
		Client client = new Client(clientName, String.valueOf(clientPassword),clientType, clientAddress, clientEmail, clientPhone, "Created client with name [" + clientName + "]"); // refactor to use properties file
		boolean addClientSucceeded = false;
		client.setClient_id(clientManager.insert(client));
		/* If we reached this point then the client was added successfully */
		addClientSucceeded = true;
		ActivityDBManager activityManager = new ActivityDBManager();
		Activity createNewClientActivity = new Activity(client.getClient_id(), deposit, new Date(), 0, ActivityType.ADD_NEW_CLIENT, "Added new client with id [" + client.getClient_id() + "]");
		activityManager.insert(createNewClientActivity);
		if(addClientSucceeded)
		{
			//Add new account for the client
			try
			{
			CreateNewAccount(client.getClient_id(), deposit, creditLimit);
			}
			catch(MBankException e)
			{
				/* Delete the client since an account could not be created for it */
				clientManager.delete(client.getClient_id());
				throw new MBankException("\nFailed to add an for the new client\nClient has not been added");
			}
		}
		return client.getClient_id();
	}

/**
 * @param clientManager
 * @param clientName
 * @param clientPassword
 * @return true if clientname-password combination is unique
 */
private void testUniqueClientnamePasswordCombination(
			ClientManager clientManager, String clientName,
			String clientPassword) throws MBankException
	{
		ArrayList<Client> clientsList = clientManager.queryAllClients();
		Iterator<Client> clientIterator = clientsList.iterator();
		while (clientIterator.hasNext())
		{
			Client c = clientIterator.next();
			if(c.getClient_name().equalsIgnoreCase(clientName) && c.getPassword().equalsIgnoreCase(clientPassword))
			{
				throw new MBankException("Client name/password combination already exists"); //refactor to use properties file later
			}
		}
	}

	//executed as part of the AddNewClient method - therefore defined as private
	public Account CreateNewAccount(long clientID, double deposit, double creditLimit) throws MBankException
	{
		if(deposit < 0)
		{
			throw new MBankException("Deposit value must be a non-negative number.\nValue entered: " + deposit);
		}
		Account account = new Account(clientID, deposit, creditLimit, "Created account for client[" + clientID + "]");// refactor to use properties file
		//insert account into DB
		AccountManager accountManager = new AccountDBManager();
		account.setAccount_id(accountManager.insert(account));
		return account;
	}
	
//helper method: get credit limit according to client type
	private double getCreditLimit(ClientType clientType) throws MBankException
	{
		PropertyManager propertyManager = new PropertyDBManager();
		double regularCreditLimit = Double.parseDouble(propertyManager.query(SystemProperties.REGULAR_CREDIT_LIMIT.getPropertyName()).getProp_value());
		double goldCreditLimit = Double.parseDouble(propertyManager.query(SystemProperties.GOLD_CREDIT_LIMIT.getPropertyName()).getProp_value());
		double platinumCreditLimit = Double.parseDouble(propertyManager.query(SystemProperties.PLATINUM_CREDIT_LIMIT.getPropertyName()).getProp_value());
		switch (clientType)
		{
		case REGULAR:
			return regularCreditLimit;
		case GOLD:
			return goldCreditLimit;
		case PLATINUM:
			return platinumCreditLimit;
		default:
			return regularCreditLimit;
		}
	}
	
	/**
	 * @param client
	 * Remove client along with its accounts and deposits
	 * If deposits are being closed before their end-date - charge commission from the deposits before removing them
	 * @throws MBankException 
	 */
	public void removeClient(Client client) throws MBankException
	{
		ClientManager clientManager = new ClientDBManager();
		DepositManager depositManager = new DepositDBManager();
		PropertyManager propertyManager = new PropertyDBManager();
		
		//remove client
		clientManager.delete(client.getClient_id());			
		String removeClientComment = "removed client with id "+ client.getClient_id();			
		/* If removal was successful - update the activity table */
		ActivityManager activityManager = new ActivityDBManager();
		Activity deleteClientActivity = new Activity(client.getClient_id(), 0, new java.util.Date(System.currentTimeMillis()), 0, ActivityType.REMOVE_CLIENT, removeClientComment);
		activityManager.insert(deleteClientActivity);
		
		// remove the client's account
		AccountDBManager accountManager = new AccountDBManager();
		double accountCommission = 0;
		Account clientAccount = accountManager.queryAccountByClient(client.getClient_id());
		if(clientAccount.getBalance() < 0) // commission in case the client owes the bank money is equal to the amount the user owes
		{
			accountCommission = clientAccount.getBalance();
		}
		RemoveAccount(client.getClient_id(), accountCommission);
		
		double depositCommission = 0;
		
		//remove client deposits if any exist
		ArrayList<Deposit> clientDeposits = depositManager.queryDepositsByClient(client.getClient_id());
		if (clientDeposits != null)
		{
			for (Iterator<Deposit> depositIterator = clientDeposits.iterator(); depositIterator.hasNext();)
			{
				Deposit d  = depositIterator.next();
				//Check if the deposit has not reached its end-date yet
				if(d.getClosing_date().after(new java.util.Date(System.currentTimeMillis())))
				{
					switch(client.getType())
					{
					case REGULAR: 
					{
						depositCommission = Double.parseDouble(propertyManager.query(SystemProperties.REGULAR_DEPOSIT_COMMISSION.getPropertyName()).getProp_value());
						break;
					}
					case GOLD: 
					{
						depositCommission = Double.parseDouble(propertyManager.query(SystemProperties.GOLD_DEPOSIT_COMMISSION.getPropertyName()).getProp_value());
						break;
					}	
					case PLATINUM: 
					{
						depositCommission = Double.parseDouble(propertyManager.query(SystemProperties.PLATINUM_DEPOSIT_COMMISSION.getPropertyName()).getProp_value());
						break;
					}
					}
				}
				
			//update the activities table
			double chargeAmount = depositCommission*(d.getBalance() + 1);
			String removeDepositComment;
			if (chargeAmount > 0)
				{
				removeDepositComment=  "removed deposit with id " + d.getClient_id() + " and Charged a commission of " + chargeAmount + " for opening deposit before end-date (on client[" + client.getClient_id() + "] removal)";
				}
			else
			{
				removeDepositComment= "removed deposit with id "+ d.getClient_id();
			}
			// remove deposit
			depositManager.delete(d);
			/* If removal was successful - update the activity table */
			Activity deleteDepositActivity = new Activity(client.getClient_id(), d.getBalance(), new java.util.Date(System.currentTimeMillis()), chargeAmount, ActivityType.REMOVE_DEPOSIT, removeDepositComment );
			activityManager.insert(deleteDepositActivity);
			}
		}

	}
	
	public void RemoveAccount(long clientId, double commission) throws MBankException
	{
		AccountManager accountManager = new AccountDBManager();
		Account account = accountManager.queryAccountByClient(clientId);
		double accountBalance = account.getBalance();
		if(accountBalance < 0)//the client owes the bank money
		{
			ActivityManager activityManager = new ActivityDBManager();
			Activity activity = new Activity(clientId, accountBalance, new java.util.Date(System.currentTimeMillis()), commission * accountBalance * (-1), ActivityType.REMOVE_CLIENT,"Commission charged due to negative balance account upon client removal(client ID: " + clientId + ")");
			try
			{
				activityManager.insert(activity);
			}catch (MBankException e)
			{
				throw new MBankException("Failed to insert activity for account[" + account.getAccount_id() + "]  removal");
			}
		}
		accountManager.delete(account.getAccount_id());
	}
	/**
	 *
	 * @return string containing all clients' toString() values 
	 * @throws MBankException 
	 */
	public List<Client> ViewAllClientDetails() throws MBankException
	{
		ClientManager clientManager = new ClientDBManager();
		List<Client> clients = clientManager.queryAllClients();
		return clients;
	}
	
	public List<Account> viewAllAccountsDetails() throws MBankException
	{
		AccountManager accountManager = new AccountDBManager();
		List<Account> accounts = accountManager.queryAllAccounts();
		return accounts;
	}
	
	public List<Deposit> viewAllDepositsDetails() throws MBankException
	{
		DepositManager depositManager = new DepositDBManager();
		List<Deposit> deposits = depositManager.queryAllDeposits();
		return deposits;
	}
	
	public List<Activity> viewAllActivitiesDetails() throws MBankException
	{
		ActivityManager activityManager = new ActivityDBManager();
		List<Activity> activities = activityManager.queryAllActivities();
		return activities;
	}
	
	public ArrayList<Property> viewSystemProperties() throws MBankException
	{
		PropertyManager propertyManager = new PropertyDBManager();
		return propertyManager.queryAllProperties();
	}
	
	public void updateSystemProperty(Property property) throws MBankException
	{
		PropertyManager propertyManager = new PropertyDBManager();
		propertyManager.update(property);
	}
}