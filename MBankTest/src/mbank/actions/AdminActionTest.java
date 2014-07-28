/**
 * 
 */
package mbank.actions;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import mbank.Util;
import mbank.actions.AdminAction;
import mbank.database.beans.Account;
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
import mbank.database.managersInterface.ActivityManager;
import mbank.database.managersInterface.ClientManager;
import mbank.database.managersInterface.DepositManager;
import mbankExceptions.MBankException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Shlomit Argov
 * 
 */
public class AdminActionTest {
	private static Connection con;
	private static ClientManager clientManager;
	private static ActivityManager activityManager;
	private static Client client;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String url = Util.DB_URL;
		con = DriverManager.getConnection(url);
		
		clientManager = new ClientDBManager();	
		client = new Client("testAccountClient", "pass", ClientType.REGULAR, "address", "email", "phone", "comment");
		clientManager.insert(client, con);
		
		activityManager = new ActivityDBManager();
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

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		clientManager.delete(client, con);
	}
	//TODO fix this test
	@Ignore
	@Test
	public void testAddNewClient() {
		/* Create clients for testing */
		Client client1 = null;
		Client client2 = null;
		try {
			client1 = new Client("moshe1", "pass1", ClientType.REGULAR, "address1", "email1", "phone1", "comment1");
			client2 = new Client("Moshe2", "Pass2", ClientType.GOLD, "address2", "email2","phone2", "comment2");
		} catch (MBankException e1) {
			e1.printStackTrace();
		}
		
		AdminAction adminAction = new AdminAction(con, 1);
		
		try 
		{
			client1.setClient_id(adminAction.addNewClient(client1.getClient_name(), client1.getPassword().toCharArray(), client1.getAddress(), client1.getEmail(),	client1.getPhone(), 512.12));
		} catch (MBankException e) 
		{
			Assert.fail("Failed to add new client via AdminAction");
			e.printStackTrace();
		}
		
		try {
			client2.setClient_id(adminAction.addNewClient(client2.getClient_name(), client2.getPassword().toCharArray(), client2.getAddress(), client2.getEmail(), client2.getPhone(), 1024.12));
		} catch (MBankException e) {
			Assert.fail("Failed to add new client with existing name and different password than in the DB");
			e.printStackTrace();
		}
		try {//try to add a client with an existing username/password combination (not permitted)
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
	public void testUpdateClientDetails()
	{
		/* Create an AdminAction */
		AdminAction adminAction =  new AdminAction(con, client.getClient_id());
		/* Create an array of details to use for updating the client's details */
		TableValue[] details = new TableValue[]{new TableValue(ClientAttributes.ADDRESS.getAttribute(), "updated address"),new TableValue(ClientAttributes.PHONE.getAttribute(),"updated phone"),new TableValue(ClientAttributes.EMAIL.getAttribute(), "updated email"),  new TableValue(ClientAttributes.CLIENT_TYPE.getAttribute(), ClientType.PLATINUM.getTypeStringValue())};
		/* Attempt to update the client details */
		try
		{
			adminAction.updateClientDetails(Long.toString(client.getClient_id()), details);
		}
		catch(MBankException e)
		{
			Assert.fail("AdminAction: updateClientDetails method failed");
			e.printStackTrace();
		}
		
		/* Check that the client details have been updated */
		Client c = null;
		try {
			c = clientManager.query(client.getClient_id(), con);
		} catch (MBankException e) {
			e.printStackTrace();
		}
		Assert.assertTrue("AdminAction: updateClientDetails method failed", c.getAddress().equalsIgnoreCase(details[0].getColumnValue()) 
		&& c.getPhone().equalsIgnoreCase(details[1].getColumnValue()) 
		&& c.getEmail().equalsIgnoreCase(details[2].getColumnValue())
		&& c.getType().getTypeStringValue().equalsIgnoreCase(details[3].getColumnValue()));
	}
	
	//TODO fix this test
	@Ignore
	@Test
	public void testRemoveClient() throws MBankException {
		AdminAction adminAction = new AdminAction(con, 1);
		Account testAccount = new Account(client.getClient_id(), 500.0, 500.0, "comment1");
		Deposit testDeposit = new Deposit(client.getClient_id(), 500.0, DepositType.LONG, 5000L, new java.util.Date(System.currentTimeMillis()), new java.util.Date(System.currentTimeMillis() + 99999999));
		
		AccountManager accountManager = new AccountDBManager();
		accountManager.insert(testAccount, con);
	
		DepositManager depositManager = new DepositDBManager();
		depositManager.insert(testDeposit, con);
		
		adminAction.removeClient(client);
		
		//Refactor - add test of properties table update
		activityManager.query(ActivityType.REMOVE_CLIENT, client.getClient_id(), con);
//		Assert.assertTrue("Bank balance was not updated before client deposits were removed", MBank.getBankBalance().equals(2000.0 + 2000.0*0.015));
		
		Assert.assertTrue("Client accounts were not removed", accountManager.queryAccountByClient(client.getClient_id(), con) == null);
		Assert.assertTrue("Client deposits were not removed", depositManager.queryDepositsByClient(client.getClient_id(), con) == null);
		Assert.assertTrue("Client was not removed from Clients table", clientManager.query(client, con) == null);
	}	
}
