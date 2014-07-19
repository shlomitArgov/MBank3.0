/**
 * 
 */
package mbank.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
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
import mbank.sequences.AccountIdSequence;
import mbank.sequences.ActivityIdSequence;
import mbank.sequences.ClientIdSequence;
import mbankExceptions.MBankException;

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
	public AdminAction(Connection con, long adminId)
	{
		super(con, adminId);
	}

	/* (non-Javadoc)
	 * @see mbank.actions.Action#updateClientDetails(java.lang.String, mbank.actions.tableValue[][])
	 */
	

	@Override
	//Admins can also update client type
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
	public String viewAccountDetails(Client client)
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
		try{
			testUniqueClientnamePasswordCombination(clientManager, clientName, String.valueOf(clientPassword));
		}
		catch(MBankException e)
		{
			throw e;
		}
		
		// add the new client
		long clientId = ClientIdSequence.getNext(); //get unique ID for client
		long accountId;
		ClientType clientType = getClientType(deposit); 
		double creditLimit = getCreditLimit(clientType);
		Client client = new Client(clientId, clientName, String.valueOf(clientPassword),clientType, clientAddress, clientEmail, clientPhone, "Created client[" + clientId + "]"); // refactor to use properties file
		//Add new account for the client
		try{
		accountId = CreateNewAccount(clientId, deposit, creditLimit);
		}
		catch(MBankException e)
		{
			throw new MBankException(e.getLocalizedMessage());
		}
		try
		{
			clientManager.insert(client, this.getCon());
		}
		catch(MBankException e)
		{
			//clean up added account in case adding a new client fails
			AccountManager accountManager = new AccountDBManager();
			String sqlError="";
			try
			{
				accountManager.delete(accountId, this.getCon());	
			}
			catch(MBankException e1)
			{/* Retrieve exception information in case of problem when trying to delete the account that was 
			created for the user that could not be added*/
				sqlError+=e1.getLocalizedMessage();
			}
			
			throw new MBankException(e.getLocalizedMessage() + "\nFailed to remove account with ID [" + accountId + "]:\n" + sqlError);
		}
		return clientId;
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
		ArrayList<Client> clientsList = clientManager.queryAllClients(this.getCon());
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
	private long CreateNewAccount(long clientID, double deposit, double creditLimit) throws MBankException
	{
		long accountID = AccountIdSequence.getNext();//get unique ID for account
		if(deposit < 0)
		{
			throw new MBankException("Deposit value must be a non-negative number.\nValue entered: " + deposit);
		}
		Account account = new Account(accountID, clientID, deposit, creditLimit, "Created account[" + accountID + "] for client[" + clientID + "]");// refactor to use properties file
		//insert account into DB
		AccountManager accountManager = new AccountDBManager();
		try{
			accountManager.insert(account, this.getCon());
		}
		catch(MBankException e)
		{
			throw new MBankException(e.getLocalizedMessage());
		}
		
		return account.getAccount_id();
	}
	
//helper method: get credit limit according to client type
	private double getCreditLimit(ClientType clientType) throws MBankException
	{
		PropertyManager propertyManager = new PropertyDBManager();
		double regularCreditLimit = Double.parseDouble(propertyManager.query(SystemProperties.REGULAR_CREDIT_LIMIT.getPropertyName(), this.getCon()).getProp_value());
		double goldCreditLimit = Double.parseDouble(propertyManager.query(SystemProperties.GOLD_CREDIT_LIMIT.getPropertyName(), this.getCon()).getProp_value());
		double platinumCreditLimit = Double.parseDouble(propertyManager.query(SystemProperties.PLATINUM_CREDIT_LIMIT.getPropertyName(), this.getCon()).getProp_value());
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
		clientManager.delete(client, this.getCon());
		double commission = 0;
		//remove client deposits
		ArrayList<Deposit> clientDeposits = depositManager.queryDepositsByClient(client.getClient_id(), this.getCon());
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
					commission = Double.parseDouble(propertyManager.query(SystemProperties.REGULAR_DEPOSIT_COMMISSION.getPropertyName(), this.getCon()).getProp_value());
					break;
				}
				case GOLD: 
				{
					commission = Double.parseDouble(propertyManager.query(SystemProperties.GOLD_DEPOSIT_COMMISSION.getPropertyName(), this.getCon()).getProp_value());
					break;
				}	
				case PLATINUM: 
				{
					commission = Double.parseDouble(propertyManager.query(SystemProperties.PLATINUM_DEPOSIT_COMMISSION.getPropertyName(), this.getCon()).getProp_value());
					break;
				}
				}

				//update bank balance by updating the activities table
				ActivityManager activityManager = new ActivityDBManager();
				double chargeAmount = commission*(d.getBalance() + 1);
				Activity deleteAccountActivity = new Activity(ActivityIdSequence.getNext(), client.getClient_id(), d.getBalance(), new java.util.Date(System.currentTimeMillis()), chargeAmount, ActivityType.REMOVE_ACCOUNT, "Charged deposit[" + d.getDeposit_id() + "] with commission of " + chargeAmount + "for opening deposit before end-date (on client[" + client.getClient_id() + "] removal"); 
				activityManager.insert(deleteAccountActivity, this.getCon());

			}
			depositManager.delete(d, this.getCon());
		}
		
		//remove client 
		RemoveAccount(client.getClient_id(), commission);
	}
	
	public void RemoveAccount(long clientId, double commission) throws MBankException
	{
		AccountManager accountManager = new AccountDBManager();
		Account account = accountManager.queryAccountByClient(clientId, this.getCon());
		double accountBalance = account.getBalance();
		if(accountBalance < 0)//the client owes the bank money
		{
			ActivityManager activityManager = new ActivityDBManager();
			Activity activity = new Activity(ActivityIdSequence.getNext(), clientId, accountBalance, new java.util.Date(System.currentTimeMillis()), commission * accountBalance * (-1), ActivityType.REMOVE_CLIENT,"Commission charged due to negative balance account upon client removal(client ID: " + clientId + ")");
			if(!(activityManager.insert(activity, this.getCon())))
			{
				throw new MBankException("Failed to insert activity for account[" + account.getAccount_id() + "]  removal");
			}
		}
		accountManager.delete(account.getAccount_id(), this.getCon());
	}
	/**
	 *
	 * @return string containing all clients' toString() values 
	 */
	public String ViewAllClientDetails()
	{
		ClientManager clientManager = new ClientDBManager();
		List<Client> clients = clientManager.queryAllClients(this.getCon());
		Iterator<Client> clientsIt = clients.iterator();
		String string = "";
		while(clientsIt.hasNext())
		{
			Client c = clientsIt.next();
			string += c.toString() + "\n";
		}
		return string;
	}
	
	public String viewAllAccountsDetails()
	{
		AccountManager accountManager = new AccountDBManager();
		List<Account> accounts = accountManager.queryAllAccounts(this.getCon());
		return Arrays.toString(accounts.toArray());
	}
	
	public String viewAllDepositsDetails()
	{
		DepositManager depositManager = new DepositDBManager();
		List<Deposit> deposits = depositManager.queryAllDeposits(this.getCon());
		return Arrays.toString(deposits.toArray());
	}
	
	public String viewAllActivitiesDetails()
	{
		ActivityManager activityManager = new ActivityDBManager();
		List<Activity> activities = activityManager.queryAllActivities(this.getCon());
		return Arrays.toString(activities.toArray());
	}
	
	public ArrayList<Property> viewSystemProperties() throws MBankException
	{
		PropertyManager propertyManager = new PropertyDBManager();
		return propertyManager.queryAllProperties(this.getCon());
	}
	
	public void updateSystemProperty(Property property) throws MBankException
	{
		PropertyManager propertyManager = new PropertyDBManager();
		propertyManager.update(property, this.getCon());
	}
}