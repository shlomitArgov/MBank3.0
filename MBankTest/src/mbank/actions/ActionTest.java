package mbank.actions;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import mbank.Util;
import mbank.actions.ClientAction;
import mbank.actions.TableValue;
import mbank.database.beans.Account;
import mbank.database.beans.Client;
import mbank.database.beans.enums.ClientAttributes;
import mbank.database.beans.enums.ClientType;
import mbank.database.managersImpl.AccountDBManager;
import mbank.database.managersImpl.ClientDBManager;
import mbank.database.managersInterface.ClientManager;
import mbankExceptions.MBankException;

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
	private static Connection con;
	private static ClientManager clientManager;
	private static Client client;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		String url = Util.DB_URL;
		con = DriverManager.getConnection(url);
		
		client = new Client("moshe1", "pass1", ClientType.REGULAR, "address1", "email1", "phone1", "comment1");
		clientManager = new ClientDBManager();		
		
		/* Insert the client into the DB */
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
	public static void tearDownAfterClass() throws Exception 
	{
		clientManager.delete(client, con);
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
		ClientAction clientAction = new ClientAction(con, client.getClient_id());
		
		try
		{
			clientAction.updateClientDetails(Long.toString(client.getClient_id()), details);
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
			clientAction.updateClientDetails(Long.toString(client.getClient_id()), details2);
			fail("Updated client type with clientAction - permission violation");
		}
		catch(MBankException e)
		{
			Assert.assertTrue(true);
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	/* Test view client details action */
	@Test
	public void testViewClientDetails() throws MBankException 
	{
		/* Create a temp client for this test */
		
		Client tempClient = new Client("testViewClientDetailsWithClientAction", "pass1", ClientType.REGULAR, "address1", "email1", "phone1", "comment1");
		
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
		
		/* Create a clientAction for testing the viewClientDetails method */
		ClientAction clientAction = new ClientAction(con, tempClient.getClient_id());
		Client clientDetails = null;
		try
		{
			clientDetails = clientAction.viewClientDetails(tempClient.getClient_id());	
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail();
		}
		/* Make sure that the correct client is returned */
		Assert.assertTrue("View client details action returned details that differ from the test client's details", tempClient.equals(clientDetails));
	}
	
	/* Test view account details action */
	@Test
	public void testViewAccountDetails() throws MBankException 
	{
		/* Create a temp client for this test */
		Client tempClient = new Client("tempClientForTestingViewAccountDetails", "pass1", ClientType.REGULAR, "address1", "email1", "phone1", "comment1");

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
		Account tempAccount = new Account(tempClient.getClient_id(), 5000, 20000, "testing viewAccountDetails");
		

		/* Insert the temp account into the DB */
		AccountDBManager accountManager = new AccountDBManager();
		try
		{
			tempAccount.setAccount_id((accountManager.insert(tempAccount, con)));	
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to insert client into the Clients table");
		}
		
		/* Create a clientAction for testing the viewAccountDetails method */
		ClientAction clientAction = new ClientAction(con, tempClient.getClient_id());
		Account accountDetails = null;
		
		try
		{
			accountDetails = clientAction.viewAccountDetails(tempClient);	
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail();
		}
		/* Make sure that the correct account is returned */
		Assert.assertTrue("View account details action returned details that differ from the test account's details", tempAccount.equals(accountDetails));
	}
}
