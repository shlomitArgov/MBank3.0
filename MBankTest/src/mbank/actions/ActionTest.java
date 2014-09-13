package mbank.actions;

import static org.junit.Assert.fail;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mbank.Util;
import mbank.actions.ClientAction;
import mbank.actions.TableValue;
import mbank.database.beans.Account;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.enums.ActivityType;
import mbank.database.beans.enums.ClientAttributes;
import mbank.database.beans.enums.ClientType;
import mbank.database.beans.enums.DepositType;
import mbank.database.beans.enums.SystemProperties;
import mbank.database.managersImpl.AccountDBManager;
import mbank.database.managersImpl.ActivityDBManager;
import mbank.database.managersImpl.ClientDBManager;
import mbank.database.managersImpl.DepositDBManager;
import mbank.database.managersInterface.AccountManager;
import mbank.database.managersInterface.ClientManager;
import mbank.exceptions.MBankException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Shlomit Argov
 *
 */
public class ActionTest 
{
	private static ClientManager clientManager;
	private static AccountManager accountManager;
	private static Client client;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		String url = Util.DB_URL;
		DriverManager.getConnection(url);
		
		client = new Client("moshe1", "pass1", ClientType.REGULAR, "address1", "email1", "phone1", "comment1");
		clientManager = new ClientDBManager();		
		accountManager = new AccountDBManager();
		
		/* Insert the client into the DB */
		try
		{
			client.setClient_id(clientManager.insert(client));	
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
	public static void tearDownAfterClass() throws Exception 
	{
		clientManager.delete(client.getClient_id());
	}

	/**
	 *  test the updateClientDetails implementation found in Action class (currently used by the clientAdmin class and called from
	 *  the AdminAction method that overrides this method
	 * @throws MBankException
	 */
	@Test
	public void testUpdateClientDetails() throws MBankException 
	{
		
		/* test that update succeeds */
		TableValue[] details = new TableValue[]{new TableValue(ClientAttributes.ADDRESS.getAttribute(), "updated address"),new TableValue(ClientAttributes.PHONE.getAttribute(),"updated phone"),new TableValue(ClientAttributes.EMAIL.getAttribute(), "updated email")};
		ClientAction clientAction = new ClientAction(client.getClient_id());
		
		try
		{
			clientAction.updateClientDetails(details);
		}
		catch(MBankException e)
		{
			fail("Failed to update client details with ClientAction\n" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		/* test that update of client type fails (unauthorized action for client) */
		TableValue[] details2 = new TableValue[]{new TableValue(ClientAttributes.ADDRESS.getAttribute(), "updated address"),new TableValue(ClientAttributes.PHONE.getAttribute(),"updated phone"),new TableValue(ClientAttributes.EMAIL.getAttribute(), "updated email"), new TableValue(ClientAttributes.CLIENT_TYPE.getAttribute(), ClientType.PLATINUM.getTypeStringValue())};//, new TableValue(ClientAttributes.COMMENT.getAttribute(), "Comment text")};
		try
		{
			clientAction.updateClientDetails(details2);
			fail("Updated client type with clientAction - permission violation");
		}
		catch(MBankException e)
		{
			Assert.assertTrue(true);
		}
	}
	
	/* Test view client details action */
	@Test
	public void testViewClientDetails() throws MBankException 
	{
		/* Create a temp client for this test and insert it into the database */
		Client tempClient = createAndInsertTempClient("testViewClientDetailsWithClientAction", ClientType.REGULAR);
		
		/* Create a clientAction for testing the viewClientDetails method */
		ClientAction clientAction = new ClientAction(tempClient.getClient_id());
		Client clientDetails = null;
		try
		{
			clientDetails = clientAction.viewClientDetails();	
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail();
		}
		/* Make sure that the correct client is returned */
		Assert.assertTrue("View client details action returned details that differ from the test client's details", tempClient.equals(clientDetails));
		
		/* cleanup */
		clientManager.delete(tempClient.getClient_id());
	}
	
	/* Test view account details action */
	@Test
	public void testViewAccountDetails() throws MBankException 
	{
		/* Create a temp client for this test and insert it into the database */
		Client tempClient = createAndInsertTempClient("tempClientForTestingViewAccountDetails", ClientType.REGULAR);
				
		/* Create a temp account for this test and insert it into the database */
		Account tempAccount = createAndInsertTempAccount("testing viewAccountDetails", tempClient, 5000);
				
		/* Create a clientAction for testing the viewAccountDetails method */
		ClientAction clientAction = new ClientAction(tempClient.getClient_id());
		Account accountDetails = null;
		
		try
		{
			accountDetails = clientAction.viewAccountDetails();	
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail();
		}
		/* Make sure that the correct account is returned */
		Assert.assertTrue("View account details action returned details that differ from the test account's details", tempAccount.equals(accountDetails));
		
		/* cleanup */
		clientManager.delete(tempClient.getClient_id());
		accountManager.delete(tempAccount);
	}
	
	/* Test view client deposits action */
	@Test
	public void testViewClientDeposits() throws MBankException 
	{
		/* Create a temp client for this test and insert it into the database */
		Client tempClient = createAndInsertTempClient("tempClientForTestingViewClientDeposits", ClientType.REGULAR);
		
		DepositDBManager depositManager = new DepositDBManager();
		Date startDate =  new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.MONTH, 3);
		Date endDate = new Date(cal.getTimeInMillis());
		Deposit tempDeposit1 = new Deposit(tempClient.getClient_id(), 100000, DepositType.LONG, 2000000, startDate, endDate);
		Deposit tempDeposit2 = new Deposit(tempClient.getClient_id(), 100000, DepositType.LONG, 2000000, startDate, endDate);
		try
		{
			tempDeposit1.setDeposit_id(depositManager.insert(tempDeposit1));
			tempDeposit2.setDeposit_id(depositManager.insert(tempDeposit2));
		} catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail();
		}
		
		/* Create a clientAction for testing the viewAccountDetails method */
		ClientAction clientAction = new ClientAction(tempClient.getClient_id());
		
		ArrayList<Deposit> deposits = (ArrayList<Deposit>) clientAction.viewClientDeposits();
		
		if((deposits.size() == 2))
		{
			Assert.assertTrue("View client deposit details action returned deposits that differ from the test deposits' details ", 
					deposits.get(0).equals(tempDeposit1) && deposits.get(1).equals(tempDeposit2)
					|| deposits.get(1).equals(tempDeposit1) && deposits.get(0).equals(tempDeposit2));
		}
		
		/* cleanup */
		clientManager.delete(tempClient.getClient_id());
		depositManager.delete(tempDeposit1);
		depositManager.delete(tempDeposit2);
	}
	
	/* Test view client activities action */
	@Test
	public void testViewClientActivites() throws MBankException 
	{
		/* Create a temp client for this test and insert it into the database */
		Client tempClient = createAndInsertTempClient("tempClientForTestingViewClientActivities", ClientType.REGULAR);
		
		/* Create a clientAction for testing the viewAccountDetails method */
		ClientAction clientAction = new ClientAction(tempClient.getClient_id());
		
		Activity tempActivity1 = new Activity(tempClient.getClient_id(), 500, new Date(), 200, ActivityType.UPDATE_CLIENT_DETAILS, "TestingViewClientActivities");
		Activity tempActivity2 = new Activity(tempClient.getClient_id(), 500, new Date(), 200, ActivityType.UPDATE_CLIENT_DETAILS, "TestingViewClientActivities");
		
		
		ActivityDBManager activityManager =  new ActivityDBManager();
		try
		{
			tempActivity1.setActivityId(activityManager.insert(tempActivity1));
			tempActivity2.setActivityId(activityManager.insert(tempActivity2));	
		} catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail();
		}
		
		ArrayList<Activity> activities = (ArrayList<Activity>) clientAction.viewClientActivities();
		
		if((activities.size() == 2))
		{
			Assert.assertTrue("View client activity details action returned activities that differ from the test activities' details ", 
					activities.get(0).equals(tempActivity1) && activities.get(1).equals(tempActivity2)
					|| activities.get(1).equals(tempActivity1) && activities.get(0).equals(tempActivity2));
		}
		
		/* cleanup */
		clientManager.delete(tempClient.getClient_id());
		activityManager.delete(tempActivity1);
		activityManager.delete(tempActivity2);
	}

	/* Test view system property activities action */
	@Test
	public void testViewSystemProperty() throws MBankException 
	{
		/* Create a clientAction for testing the viewSystemProperty method */
		ClientAction clientAction = new ClientAction(client.getClient_id());
		String systemProperty = null;
		try
		{
			systemProperty = clientAction.viewSystemProperty(SystemProperties.PRE_OPEN_FEE.getPropertyName());	
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail();
		}
		/* Assumes that if no exception was thrown and the property is not empty/null - that it was retrieve successfully */
		Assert.assertTrue("System property is empty", (systemProperty != null) && !(systemProperty.isEmpty()));
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
}