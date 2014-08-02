/**
 * 
 */
package mbank.actions;

import java.sql.Connection;
import java.sql.DriverManager;

import mbank.Util;
import mbank.actions.AdminAction;
import mbank.database.beans.Account;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.enums.ActivityType;
import mbank.database.beans.enums.ClientAttributes;
import mbank.database.beans.enums.ClientType;
import mbank.database.beans.enums.DepositType;
import mbank.database.managersImpl.AccountDBManager;
import mbank.database.managersImpl.ActivityDBManager;
import mbank.database.managersImpl.ClientDBManager;
import mbank.database.managersImpl.DepositDBManager;
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
	private static AdminAction adminAction;
	private static Client client;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String url = Util.DB_URL;
		con = DriverManager.getConnection(url);
		
		adminAction = new AdminAction(con, 1);
		
		activityManager = new ActivityDBManager();
		
		accountManager = new AccountDBManager();
		
		client = new Client("testAccountClient", "pass", ClientType.REGULAR, "address", "email", "phone", "comment");
		clientManager = new ClientDBManager();	
		
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
		clientManager.delete(client, con);
	}
	
	@Test 
	public void testUpdateClientDetails() throws MBankException 
	{
		/* Create a temp client for this test */
		Client tempClient = new Client("testUpdateClientDetails", "pass", ClientType.REGULAR, "address", "email", "phone", "comment");
		
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
		
		/* Create an AdminAction */
		AdminAction adminAction =  new AdminAction(con, 1); // admin user ID is always 1
	
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
		clientManager.delete(tempClient, con);
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
		
		/* Create an AdminAction */
		AdminAction adminAction = new AdminAction(con, 1); //administrator ID is always 1
		
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
			clientManager.delete(client1, con);
			clientManager.delete(client2, con);
		} catch (MBankException e) {
			e.printStackTrace();
		}
		
	}

	
	@Test
	public void testRemoveClient() throws MBankException {
		
		/* Create a temp client for this test */
		Client tempClient = new Client("testRemoveClient", "pass", ClientType.REGULAR, "address", "email", "phone", "comment");
		
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
		
		/* Create a temp client for this test */
		Client tempClient = new Client("testCreateNewAccout", "pass", ClientType.REGULAR, "address", "email", "phone", "comment");
		
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
		clientManager.delete(tempClient, con);
		accountManager.delete(account, con);
	}
	
	@Test
	public void testRemoveAccount() throws MBankException {
		
		/* Create a temp client for this test */
		Client tempClient = new Client("testRemoveAccout", "pass", ClientType.REGULAR, "address", "email", "phone", "comment");
		
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
		
		/* Create a temp account for this test */
		Account tempAccount = new Account(tempClient.getClient_id(), 1000, 5000, "testRemoveAccout");
		
		/* Insert the temp account into the DB */
		try
		{
			tempAccount.setAccount_id((accountManager.insert(tempAccount, con)));	
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to insert client into the Clients table");
		}
		
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
		clientManager.delete(tempClient, con);
	}
}
