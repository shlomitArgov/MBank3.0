/**
 * 
 */
package mbank.actions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import mbank.Util;
import mbank.actions.AdminAction;
import mbank.database.beans.Account;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.Property;
import mbank.database.beans.enums.ActivityType;
import mbank.database.beans.enums.ClientAttributes;
import mbank.database.beans.enums.ClientType;
import mbank.database.beans.enums.DepositType;
import mbank.database.beans.enums.SystemProperties;
import mbank.database.managersImpl.AccountDBManager;
import mbank.database.managersImpl.ActivityDBManager;
import mbank.database.managersImpl.ClientDBManager;
import mbank.database.managersImpl.DepositDBManager;
import mbank.database.managersImpl.PropertyDBManager;
import mbank.database.managersInterface.AccountManager;
import mbank.database.managersInterface.DepositManager;
import mbankExceptions.MBankException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Shlomit Argov
 * 
 */
public class AdminActionTest {
	private static Connection con;
	private static ClientDBManager clientManager;
	private static AccountDBManager accountManager;
	private static ActivityDBManager activityManager;
	private static AdminAction adminAction; // admin user ID is always 1
	private static Client client;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String url = Util.DB_URL;
		con = DriverManager.getConnection(url);
		adminAction = new AdminAction(con, 1);
		clientManager = new ClientDBManager();
		activityManager = new ActivityDBManager();
		accountManager = new AccountDBManager();
		
		client = new Client("testAccountClient", "pass", ClientType.REGULAR, "address", "email", "phone", "comment");
		
		try
		{
			client.setClient_id(clientManager.insert(client, con));	
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to insert client into the Clients table");
		}	
	}

	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		clientManager.delete(client.getClient_id(), con);
	}
	
	@Test 
	public void testUpdateClientDetails() throws MBankException 
	{
		/* Create a temp client for this test and insert it into the database*/
		Client tempClient = createAndInsertTempClient("testUpdateClientDetails", ClientType.REGULAR);
	
		/* Create an array of details to use for updating the client's details */
		TableValue[] details = new TableValue[]{new TableValue(ClientAttributes.ADDRESS.getAttribute(), "updated address"),new TableValue(ClientAttributes.PHONE.getAttribute(),"updated phone"),new TableValue(ClientAttributes.EMAIL.getAttribute(), "updated email"),  new TableValue(ClientAttributes.CLIENT_TYPE.getAttribute(), ClientType.PLATINUM.getTypeStringValue())};
		
		/* Attempt to update the client's details */
		try
		{
			adminAction.updateClientDetails(Long.toString(tempClient.getClient_id()), details);
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("AdminAction: updateClientDetails method failed");
		}
		
		/* Check that the client details have been updated */
		Client c = null;
		try {
			c = clientManager.query(tempClient.getClient_id(), con);
		} catch (MBankException e) {
			e.printStackTrace();
		}
		Assert.assertTrue("AdminAction: updateClientDetails method failed", c.getAddress().equalsIgnoreCase(details[0].getColumnValue()) 
		&& c.getPhone().equalsIgnoreCase(details[1].getColumnValue()) 
		&& c.getEmail().equalsIgnoreCase(details[2].getColumnValue())
		&& c.getType().getTypeStringValue().equalsIgnoreCase(details[3].getColumnValue()));
		
		/* cleanup */
		clientManager.delete(tempClient.getClient_id(), con);
	}
	

	@Test
	public void testAddNewClient() {
		/* Create clients for testing */
		Client client1 = null;
		Client client2 = null;
		try {
			client1 = new Client("testAddNewClient1", "pass1", ClientType.REGULAR, "address1", "email1", "phone1", "comment1");
			client2 = new Client("testAddNewClient2", "Pass2", ClientType.GOLD, "address2", "email2","phone2", "comment2");
		} catch (MBankException e1) {
			e1.printStackTrace();
		}
		
		/* Try to add a new client to the system's database */
		try 
		{
			client1.setClient_id(adminAction.addNewClient(client1.getClient_name(), client1.getPassword().toCharArray(), client1.getAddress(), client1.getEmail(),	client1.getPhone(), 512.12));
		} catch (MBankException e) 
		{
			e.printStackTrace();
			Assert.fail("Failed to add new client via AdminAction");
		}
		
		/* Try to add a new client with an existing name but a different password into the system's database (legal action) */
		try {
			client2.setClient_id(adminAction.addNewClient(client2.getClient_name(), client2.getPassword().toCharArray(), client2.getAddress(), client2.getEmail(), client2.getPhone(), 1024.12));
		} catch (MBankException e) {
			e.printStackTrace();
			Assert.fail("Failed to add new client with existing name and different password than in the DB");
		}
		/* Try to add a new client with an existing username-password combination into the system's database (illegal action) */
		try {
		adminAction.addNewClient(client2.getClient_name(), client2.getPassword().toCharArray(), client2.getAddress(), client2.getEmail(), client2.getPhone(), 1024.12);
		} catch (MBankException e) {
			Assert.assertTrue("Succeeded in adding a client with an existing client-name/pasword combination", e.getMessage().equalsIgnoreCase("Client name/password combination already exists"));
		}
		
		/* cleanup */
		try {
			clientManager.delete(client1.getClient_id(), con);
			clientManager.delete(client2.getClient_id(), con);
		} catch (MBankException e) {
			e.printStackTrace();
		}
		
	}

	
	@Test
	public void testRemoveClient() throws MBankException {
		
		/* Create a temp client for this test and insert it into the database */
		Client tempClient = createAndInsertTempClient("testRemoveClient", ClientType.REGULAR);

		Account testAccount = new Account(tempClient.getClient_id(), 500.0, 500.0, "comment1");
		Deposit testDeposit1 = new Deposit(tempClient.getClient_id(), 500.0, DepositType.LONG, 5000L, new java.util.Date(System.currentTimeMillis()), new java.util.Date(System.currentTimeMillis()));
		Deposit testDeposit2 = new Deposit(tempClient.getClient_id(), 800.0, DepositType.LONG, 5000L, new java.util.Date(System.currentTimeMillis()), new java.util.Date(System.currentTimeMillis() + 999999));

		AccountManager accountManager = new AccountDBManager();
		testAccount.setAccount_id(accountManager.insert(testAccount, con));
	
		DepositManager depositManager = new DepositDBManager();
		testDeposit1.setDeposit_id(depositManager.insert(testDeposit1, con));
		testDeposit2.setDeposit_id(depositManager.insert(testDeposit2, con));

		try
		{
			adminAction.removeClient(tempClient);
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to remove client from MBank database");
		}
		/* Make sure the client was removed from the clients table */
		Assert.assertTrue("Client was not removed from Clients table", clientManager.query(tempClient, con) == null);
		/* Make sure the activity table was updated */
		Activity activity = activityManager.query(ActivityType.REMOVE_CLIENT, tempClient.getClient_id(), con);
		Assert.assertTrue("Activity table was not updated regarding removal of a client", activity.getActivityType().equals(ActivityType.REMOVE_CLIENT));
		/* Make sure the client's account was removed along with the client */
		Assert.assertTrue("Client accounts were not removed", accountManager.queryAccountByClient(tempClient.getClient_id(), con) == null);
		/* Make sure the client's deposits were removed along with the client */
		Assert.assertTrue("Client deposits were not removed", depositManager.queryDepositsByClient(tempClient.getClient_id(), con) == null);
	
	}
	
	@Test
	public void testCreateNewAccount() throws MBankException {
		
		/* Create a temp client for this test and insert it into the database */
		Client tempClient = createAndInsertTempClient("testCreateNewAccount", ClientType.REGULAR);
		
		Account account = null;
		try
		{
			account = adminAction.CreateNewAccount(tempClient.getClient_id(), 1000, 10000);	
		} catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to create new account with AdminAction");
		}
		Assert.assertTrue("Failed to create new account with AdminAction", account != null);
		
		
		/* cleanup */
		clientManager.delete(tempClient.getClient_id(), con);
		accountManager.delete(account, con);
	}
	
	@Test
	public void testRemoveAccount() throws MBankException {
		
		/* Create a temp client for this test and insert it into the database */
		Client tempClient = createAndInsertTempClient("testRemoveAccount", ClientType.REGULAR);
		
		/* Create a temp account for this test and insert it into the database */
		@SuppressWarnings("unused") //used in removal of account associated to client
		Account tempAccount = createAndInsertTempAccount("testRemoveAccount", tempClient, 1000);
		
		try
		{
			adminAction.RemoveAccount(tempClient.getClient_id(), 500);	
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to remove account");
		}
		
		Assert.assertNull("Failed to remove account", accountManager.queryAccountByClient(tempClient.getClient_id(), con));
		
		/* cleanup */
		clientManager.delete(tempClient.getClient_id(), con);
	}
	

	@Test
	public void testViewAllClientDetails() throws MBankException {
		List<Client> clients = null;
		try
		{
			clients = adminAction.ViewAllClientDetails();	
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to get all client details");
		}
		Assert.assertNotNull("Failed to get all client details", clients);
		
		String sql = "SELECT COUNT(*) FROM CLIENTS";
		PreparedStatement ps;
		int numRows = 0;
		try 
		{
			ps = con.prepareStatement(sql);
			ps.execute();
			if(ps.getResultSet() != null)
			{
				if(ps.getResultSet().next())
				{
					numRows = ps.getResultSet().getInt(1);		
				}
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
				 
		Assert.assertTrue("Failed to get all client details", numRows == clients.size());
		}
	

	@Test
	public void testViewAllAccountDetails() throws MBankException {
		List<Account> accounts = null;
		try
		{
			accounts = adminAction.viewAllAccountsDetails();	
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to get all accounts details");
		}
		Assert.assertNotNull("Failed to get all accounts details", accounts);
		
		String sql = "SELECT COUNT(*) FROM ACCOUNTS";
		PreparedStatement ps;
		int numRows = 0;
		try 
		{
			ps = con.prepareStatement(sql);
			ps.execute();
			if(ps.getResultSet() != null)
			{
				if(ps.getResultSet().next())
				{
					numRows = ps.getResultSet().getInt(1);		
				}
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
				 
		Assert.assertTrue("Failed to get all accounts details", numRows == accounts.size());
	}
	@Test
	public void testViewAllActivitiesDetails() throws MBankException {
		List<Activity> activities = null;
		try
		{
			activities = adminAction.viewAllActivitiesDetails();	
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to get all activities details");
		}
		Assert.assertNotNull("Failed to get all activities details", activities);
		
		String sql = "SELECT COUNT(*) FROM ACTIVITY";
		PreparedStatement ps;
		int numRows = 0;
		try 
		{
			ps = con.prepareStatement(sql);
			ps.execute();
			if(ps.getResultSet() != null)
			{
				if(ps.getResultSet().next())
				{
					numRows = ps.getResultSet().getInt(1);		
				}
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
				 
		Assert.assertTrue("Failed to get all activities details", numRows == activities.size());
	}

	@Test
	public void testViewAllDepositsDetails() throws MBankException {
		List<Deposit> deposits = null;
		try
		{
			deposits = adminAction.viewAllDepositsDetails();	
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to get all deposits details");
		}
		Assert.assertNotNull("Failed to get all deposits details", deposits);
		
		String sql = "SELECT COUNT(*) FROM DEPOSITS";
		PreparedStatement ps;
		int numRows = 0;
		try 
		{
			ps = con.prepareStatement(sql);
			ps.execute();
			if(ps.getResultSet() != null)
			{
				if(ps.getResultSet().next())
				{
					numRows = ps.getResultSet().getInt(1);		
				}
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
				 
		System.out.println("num rows = " + numRows);
		System.out.println("size of rs = " + deposits.size());		
		Assert.assertTrue("Failed to get all deposits details", numRows == deposits.size());
	}
	
	@Test
	public void testUpdateSystemProperty() throws MBankException {
	
		/* save property value before temporary change needed for testing */
		PropertyDBManager propertyManager = new PropertyDBManager();
		String OriginalPropVal = null;
		try
		{
			OriginalPropVal = propertyManager.query(SystemProperties.GOLD_CREDIT_LIMIT.getPropertyName(), con).getProp_value();	
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to read system property from database");
		}
		
		Property testProperty = new Property(SystemProperties.GOLD_CREDIT_LIMIT.getPropertyName(), "6666");
		
		try
		{
			adminAction.updateSystemProperty(testProperty);
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to update system property");
		}
		Assert.assertTrue("Failed to update system property", testProperty.getProp_value().equalsIgnoreCase(adminAction.viewSystemProperty(testProperty.getProp_key())));
		
		 /* cleanup */
		if (OriginalPropVal != null)
		{
			testProperty.setProp_value(OriginalPropVal);	
		}
		adminAction.updateSystemProperty(testProperty);
	}
	

	private static Client createAndInsertTempClient(String name, ClientType type)
	{
		/* Create a temp client for this test */
		Client tempClient = null;
		try {
			tempClient = new Client(name, "pass", type, "address", "email", "phone", "comment");
		} catch (MBankException e1) {
			e1.printStackTrace();
			Assert.fail("Failed to create client");
		}
		
		/* Insert the temp client into the DB */
		try
		{
			tempClient.setClient_id(clientManager.insert(tempClient, con));	
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to insert client into the Clients table");
		}
		
		return tempClient;
	}
	
	
	private static Account createAndInsertTempAccount(String comment, Client client, double balance)
	{
		/* Create a temp account for this test */
		Account tempAccount = null;
		tempAccount = new Account(client.getClient_id(), balance, 10000, comment);
		
		/* Insert the temp account into the DB */
		try
		{
			tempAccount.setAccount_id(accountManager.insert(tempAccount, con));	
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to insert account into the Accounts table");
		}
		
		return tempAccount;
	}
}

