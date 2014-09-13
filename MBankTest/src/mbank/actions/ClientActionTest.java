package mbank.actions;

import static org.junit.Assert.*;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mbank.Util;
import mbank.database.beans.Account;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.enums.ActivityType;
import mbank.database.beans.enums.ClientType;
import mbank.database.beans.enums.DepositType;
import mbank.database.managersImpl.AccountDBManager;
import mbank.database.managersImpl.ActivityDBManager;
import mbank.database.managersImpl.ClientDBManager;
import mbank.database.managersImpl.DepositDBManager;
import mbank.exceptions.MBankException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClientActionTest {
	private static Client client;
	private static ClientDBManager clientManager;
	private static AccountDBManager accountManager;
	private static DepositDBManager depositManager;
	private static ActivityDBManager activityManager;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String url = Util.DB_URL;
		DriverManager.getConnection(url);
		
		clientManager = new ClientDBManager();
		activityManager = new ActivityDBManager();
		accountManager = new AccountDBManager();
		depositManager = new DepositDBManager();
		
		client = createAndInsertTempClient("ClientActionTestClient", ClientType.REGULAR);
	}

	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testViewClientDetails() throws MBankException 
	{
		Client tempClient = createAndInsertTempClient("testViewClientDetails", ClientType.REGULAR);
		ClientAction clientAction = new ClientAction(tempClient.getClient_id());
		
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
		clientManager.delete(tempClient.getClient_id());
	}

	@Test
	public void testViewAccountDetails() throws MBankException 
	{
		Client tempClient = createAndInsertTempClient("testViewAccountDetailsClient", ClientType.REGULAR);
		Account tempAccount = createAndInsertTempAccount("testViewAccountDetailsAccount", tempClient, 1000);
		
		ClientAction clientAction = new ClientAction(tempClient.getClient_id());

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
		clientManager.delete(tempClient.getClient_id());
		accountManager.delete(tempAccount);
	}

	@Test
	public void testViewClientDeposits() throws MBankException {
	
		Client tempClient = createAndInsertTempClient("testViewAccountDetailsClient", ClientType.REGULAR);
		/* Populate the Deposits table with several activities */
		List<Deposit> tempDeposits =  CreateTempDeposits(tempClient.getClient_id(), "testViewClientDeposits", DepositType.SHORT);

		List<Deposit> deposits = null;
		ClientAction clientAction = new ClientAction(tempClient.getClient_id());
		try
		{
			deposits = clientAction.viewClientDeposits(tempClient.getClient_id());
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to retrieve client deposits details with ClientAction");
		}
		
		Assert.assertTrue("Failed to retrieve client deposits details with ClientAction", (deposits != null) && deposits.equals(tempDeposits));
		
		List<Deposit> deposits2 = null;
		try
		{
			deposits2 = clientAction.viewClientDeposits(client.getClient_id());
		}
		catch (MBankException e)
		{
			Assert.assertNull("Authorization error - succeeded in retrieving client deposits details for a different client with ClientAction", deposits2);
		}
		
		/* cleanup */
		clientManager.delete(tempClient.getClient_id());
		for (int i = 0; i < deposits.size() ; i++)
		{
			deposits.remove(i);
		}
	}

	@Test
	public void testViewClientActivities() throws MBankException {
		Client tempClient = createAndInsertTempClient("testViewAccountDetailsClient", ClientType.REGULAR);
		/* Populate the Activity table with several activities */
		List<Activity> tempActivities =  CreateTempActivities(tempClient.getClient_id(), "testViewClientActivities");

		ClientAction clientAction = new ClientAction(tempClient.getClient_id());

		List<Activity> activities = null;
		try
		{
			activities = clientAction.viewClientActivities(tempClient.getClient_id());
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to retrieve client activities details with ClientAction");
		}
		
		Assert.assertTrue("Failed to retrieve client activities details with ClientAction", (activities != null) && activities.equals(tempActivities));
		
		List<Activity> activities2 = null;
		try
		{
			activities2 = clientAction.viewClientActivities(client.getClient_id());
		}
		catch (MBankException e)
		{
			Assert.assertNull("Authorization error - succeeded in retrieving client activities details for a different client with ClientAction", activities2);
		}
		
		/* cleanup */
		clientManager.delete(tempClient.getClient_id());
		for (int i = 0; i < activities.size() ; i++)
		{
			activities.remove(i);
		}
	}

	@Test
	public void testWithdrawFromAccount() throws MBankException {
		/* Create a client and an account for this test and associate the account with the client */
		Client tempClient = createAndInsertTempClient("testWithdrowFromAccountClient", ClientType.REGULAR);
		Account tempAccount = createAndInsertTempAccount("testWithdrowFromAccount", tempClient, 50000);
		ClientAction clientAction = new ClientAction(tempClient.getClient_id());

		try
		{
			clientAction.withdrawFromAccount(tempClient, 500);	
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to withdraw from account with ClientAction");
		}
		
		/* try to overdraw the account */
		try
		{
			clientAction.withdrawFromAccount(tempClient, 999999999);	
		}
		catch (MBankException e)
		{
			assertTrue("Authorization error - succeeded in overdrawing client account", e.getLocalizedMessage().equalsIgnoreCase("Ilegal Action - withdrawal exceeds client limit"));
		}
		
		/* try to withdraw negative amount */
		try
		{
			clientAction.withdrawFromAccount(tempClient, -3);	
		}
		catch (MBankException e)
		{
			assertTrue("Authorization error - succeeded in overdrawing client account", e.getLocalizedMessage().equalsIgnoreCase("Cannot withdraw negative amount"));
		}
		
		/* cleanup */
		clientManager.delete(tempClient.getClient_id());
		accountManager.delete(tempAccount);
	}

	@Test
	public void testDepositToAccount() throws MBankException {
		/* Create a client and an account for this test and associate the account with the client */
		Client tempClient = createAndInsertTempClient("testDepositToAccountClient", ClientType.REGULAR);
		Account tempAccount = createAndInsertTempAccount("testDepositToAccount", tempClient, 10000);
		ClientAction clientAction = new ClientAction(tempClient.getClient_id());
		
		/* deposit amount that will not change the client type */
		try 
		{
			clientAction.depositToAccount(tempClient, 550);
		} 
		catch (MBankException e) 
		{
			e.printStackTrace();
			Assert.fail("Failed to deposit to client account");
		}
	
		/* deposit amount that will change the client type */
		try 
		{
			clientAction.depositToAccount(tempClient, 100000);
		} 
		catch (MBankException e) 
		{
			e.printStackTrace();
			Assert.fail("Failed to deposit an amount that changes the client type to a client account");
		}
		 /* deposit non-positive amount */

		try 
		{
			clientAction.depositToAccount(tempClient, -3);
			Assert.fail("Error - managed to deposit negative amount into client account");
		} 
		catch (MBankException e) 
		{
			Assert.assertTrue("Error - managed to deposit negative amount into client account", e.getLocalizedMessage().equalsIgnoreCase("Deposit amount must be non-negative"));
		}		
		
		/* cleanup */
		clientManager.delete(tempClient.getClient_id());
		accountManager.delete(tempAccount);
	}

	@Test
	public void testCreateNewDeposit() throws MBankException 
	{
		/* Create a client this test */
		Client tempClient = createAndInsertTempClient("testCreateNewDepositClient", ClientType.REGULAR);
		ClientAction clientAction = new ClientAction(tempClient.getClient_id());
		ArrayList<Deposit> deposits = new ArrayList<>();
		/* create short term deposit */
		try 
		{
			deposits.add(clientAction.createNewDeposit(tempClient, DepositType.SHORT, 100000, new Date(System.currentTimeMillis() + 1000*3600*24*2)));
		} 
		catch (MBankException e) 
		{
			e.printStackTrace();
			Assert.fail("Failed to create new short-term deposit");
		}
		Assert.assertTrue("Failed to create new short-term deposit", deposits.get(deposits.size() - 1) !=  null);
		
		/* create long term deposit */ 
		try 
		{
			deposits.add(clientAction.createNewDeposit(tempClient, DepositType.LONG, 100000, new Date(System.currentTimeMillis() + 1000*3600*24*366)));
		} 
		catch (MBankException e) 
		{
			e.printStackTrace();
			Assert.fail("Failed to create new long-term deposit");
		}
		Assert.assertTrue("Failed to create new long-term deposit",  deposits.get(deposits.size() - 1) !=  null);
		
		/* try to create a short term deposit with an invalid closing date*/
		try 
		{
			deposits.add(clientAction.createNewDeposit(tempClient, DepositType.SHORT, 100000, new Date(System.currentTimeMillis() - 10000)));
			Assert.fail("Failed to create new short-term deposit");
		} 
		catch (MBankException e) 
		{
			Assert.assertTrue("Failed to create new short-term deposit", e.getLocalizedMessage().equalsIgnoreCase("Deposit duration must be non-negative"));
		}
		
		/* try to create a long term deposit with an invalid closing date*/
		try 
		{
			deposits.add(clientAction.createNewDeposit(tempClient, DepositType.LONG, 100000, new Date(System.currentTimeMillis() - 10000)));
			Assert.fail("Failed to create new short-term deposit");
		} 
		catch (MBankException e) 
		{
			Assert.assertTrue("Failed to create new short-term deposit", e.getLocalizedMessage().equalsIgnoreCase("Deposit duration must be non-negative"));
		}
		
		/* cleanup */
		while (deposits.size() > 0)
		{
			depositManager.delete(deposits.get(deposits.size() -1));
			deposits.remove(deposits.size() -1);
		}
	}

	@Test
	public void testPreOpenDeposit() throws MBankException {
		/* Create a client and an account for this test and associate the account with the client */
		Client tempClient = createAndInsertTempClient("testCreateNewDepositClient", ClientType.REGULAR);
		@SuppressWarnings("unused") // need to add account for the deposit money to be transfered to
		Account tempAccount = createAndInsertTempAccount("testDepositToAccount", tempClient, 10000);
		ClientAction clientAction = new ClientAction(tempClient.getClient_id());
		
		Deposit tempDeposit = new Deposit(tempClient.getClient_id(), 10000, DepositType.LONG, 11000, new Date(), new Date(System.currentTimeMillis() + 1000*3600*24*380));
		tempDeposit.setDeposit_id(depositManager.insert(tempDeposit));
		
		try 
		{
			clientAction.preOpenDeposit(tempDeposit.getDeposit_id());
		} catch (MBankException e) 
		{
			Assert.fail("Failed to pre-open long-term deposit");
		}
		/* cleanup */
		clientManager.delete(tempClient.getClient_id());
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
			tempClient.setClient_id(clientManager.insert(tempClient));	
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
			tempAccount.setAccount_id(accountManager.insert(tempAccount));	
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to insert account into the Accounts table");
		}
		
		return tempAccount;
	}
	

	private List<Activity> CreateTempActivities(long client_id, String description) {
		ArrayList<Activity> activities = new ArrayList<Activity>();
		Activity activity = null;
		for (int i = 0; i < 4; i++) {
			activity = new Activity(client_id, i*100, new Date(), i*0.2, ActivityType.UPDATE_CLIENT_DETAILS, description + i);
			try
			{
				activity.setActivityId(activityManager.insert(activity));
			} catch (MBankException e)
			{
				e.printStackTrace();
			}
			activities.add(activity);
		}
		return activities;
	}
	
	private List<Deposit> CreateTempDeposits(long client_id, String string, DepositType type) {
		ArrayList<Deposit> deposits = new ArrayList<Deposit>();
		Deposit deposit = null;
		for (int i = 0; i < 4; i++) {
			deposit = new Deposit(client_id, 100*i, type, i*1000, new Date(), new Date(System.currentTimeMillis() + 1000*3600*24*2));
			try
			{
				deposit.setDeposit_id(depositManager.insert(deposit));
			} catch (MBankException e)
			{
				e.printStackTrace();
			}
			deposits.add(deposit);
		}
		return deposits;
	}
}
