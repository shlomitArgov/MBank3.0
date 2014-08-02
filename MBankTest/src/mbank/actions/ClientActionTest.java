package mbank.actions;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;

import mbank.Util;
import mbank.database.beans.Account;
import mbank.database.beans.Client;
import mbank.database.beans.enums.ClientType;
import mbank.database.managersImpl.AccountDBManager;
import mbank.database.managersImpl.ActivityDBManager;
import mbank.database.managersImpl.ClientDBManager;
import mbankExceptions.MBankException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClientActionTest {
	private static Connection con;
	private static Client client;
	private static ClientDBManager clientManager;
	private static AccountDBManager accountManager;
	private static ActivityDBManager activityManager;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String url = Util.DB_URL;
		con = DriverManager.getConnection(url);
		
		clientManager = new ClientDBManager();
		
		activityManager = new ActivityDBManager();
		
		accountManager = new AccountDBManager();
		
		client = createAndInsertTempClient("ClientActionTestClient", ClientType.REGULAR);
	}

	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testViewClientDetails() throws MBankException 
	{
		Client tempClient = createAndInsertTempClient("testViewClientDetails", ClientType.REGULAR);
		ClientAction clientAction = new ClientAction(con, tempClient.getClient_id());
		
		try 
		{
			clientAction.viewClientDetails(tempClient.getClient_id());
		} catch (MBankException e) 
		{
			e.printStackTrace();
			Assert.fail("Failed to retrieve client details with ClientAction");
		}
		
		Client clientDetails = null;
		try
		{
			clientDetails = clientAction.viewClientDetails(client.getClient_id());
		}
		catch (MBankException e)
		{
			Assert.assertNull("Authorization error - succeeded in retrieving client details for different client with ClientAction", clientDetails);
		}
		
		/* cleanup */
		clientManager.delete(tempClient, con);
	}

	@Test
	public void testViewAccountDetails() throws MBankException 
	{
		Client tempClient = createAndInsertTempClient("testViewAccountDetailsClient", ClientType.REGULAR);
		Account tempAccount = createAndInsertTempAccount("testViewAccountDetailsAccount", tempClient);
		
		ClientAction clientAction = new ClientAction(con, tempClient.getClient_id());

		try 
		{
			clientAction.viewAccountDetails(tempClient);
		} catch (MBankException e) 
		{
			e.printStackTrace();
			Assert.fail("Failed to retrieve client account details with ClientAction");
		}
		Account accountDetails = null;
		try
		{
			accountDetails = clientAction.viewAccountDetails(client);
		}
		catch (MBankException e)
		{
			Assert.assertNull("Authorization error - succeeded in retrieving client account details for a different client's account with ClientAction", accountDetails);
		}
		
		/* cleanup */
		clientManager.delete(tempClient, con);
		accountManager.delete(tempAccount, con);
	}

	@Test
	public void testViewClientDeposits() {
		fail("Not yet implemented");
	}

	@Test
	public void testViewClientActivities() {
		fail("Not yet implemented");
	}

	@Test
	public void testWithdrawFromAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testDepositToAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateNewDeposit() {
		fail("Not yet implemented");
	}

	@Test
	public void testPreOpenDeposit() {
		fail("Not yet implemented");
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
	
	private static Account createAndInsertTempAccount(String comment, Client client)
	{
		/* Create a temp account for this test */
		Account tempAccount = null;
		tempAccount = new Account(client.getClient_id(), 1000, 10000, comment);
		
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
