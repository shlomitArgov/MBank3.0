/**
 * 
 */
package mbank.actionsTest;

import java.sql.Connection;
import java.sql.DriverManager;

import mbank.actions.AdminAction;
import mbank.database.beans.Account;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.enums.ActivityType;
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
import mbank.sequences.AccountIdSequence;
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
	private static long clientID1;
	private static long clientID2;
	private static ClientManager clientManager;
	private static ActivityManager activityManager;
	private static Client client1;
	private static Client client2;
	private static Account account1;
	private static Deposit deposit1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String url = "jdbc:derby://localhost:1527/MBankDB;";
		con = DriverManager.getConnection(url);
		
		client1 = new Client(0, "moshe1", "pass1", ClientType.REGULAR, "address1", "email1", "phone1", "comment1");
		client2 = new Client(0, "Moshe2", "Pass2", ClientType.GOLD, "address2", "email2","phone2", "comment2");
		account1 = new Account(AccountIdSequence.getNext(), clientID1, 500.0, 500.0, "comment1");
		deposit1 = new Deposit(0L, clientID1, 500.0, DepositType.LONG, 5000L, new java.util.Date(System.currentTimeMillis()), new java.util.Date(System.currentTimeMillis() + 99999999));
		clientManager = new ClientDBManager();	
		activityManager = new ActivityDBManager();
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		clientManager.delete(client1, con);
		clientManager.delete(client2, con);
	}
	
	@Test
	public void testAddNewClient() {
		AdminAction adminAction = new AdminAction(con, clientID1);
		try 
		{
			clientID1 = adminAction.addNewClient(client1.getClient_name(), client1.getPassword().toCharArray(), client1.getAddress(), client1.getEmail(),	client1.getPhone(), 512.12);
			client1.setClient_id(clientID1);
		} catch (MBankException e) 
		{
			Assert.fail("Failed to add new client via AdminAction");
			e.printStackTrace();
		}
		
		try {
			clientID2 = adminAction.addNewClient(client2.getClient_name(), client2.getPassword().toCharArray(), client2.getAddress(), client2.getEmail(), client2.getPhone(), 1024.12);
			client2.setClient_id(clientID2);
		} catch (MBankException e) {
			Assert.fail("Failed to add new client with existing name and different password than in the DB");
			e.printStackTrace();
		}
		try {//try to add a client with an existing username/password combination (not permitted)
		adminAction.addNewClient(client2.getClient_name(), client2.getPassword().toCharArray(), client2.getAddress(), client2.getEmail(), client2.getPhone(), 1024.12);
		} catch (MBankException e) {
			Assert.assertTrue("Succeeded in adding a client with an existing client-name/pasword combination", e.getMessage().equalsIgnoreCase("Client name/password combination already exists"));
		}
		
		clientManager.delete(client1, con);
		clientManager.delete(client2, con);
	}
	
	@Test
	public void testRemoveClient() throws MBankException {
		AdminAction adminAction = new AdminAction(con, clientID1);
		ClientManager clientManager = new ClientDBManager();
		clientManager.insert(client1, con);
		AccountManager accountManager = new AccountDBManager();
		accountManager.insert(account1, con);
		DepositManager depositManager = new DepositDBManager();
		depositManager.insert(deposit1, con);
		
		adminAction.removeClient(client1);
		
		//Refactor - add test of properties table update
		activityManager.query(ActivityType.REMOVE_CLIENT, client1.getClient_id(), con);
//		Assert.assertTrue("Bank balance was not updated before client deposits were removed", MBank.getBankBalance().equals(2000.0 + 2000.0*0.015));
		
		Assert.assertTrue("Client accounts were not removed", accountManager.queryAccountByClient(client1.getClient_id(), con) == null);
		Assert.assertTrue("Client deposits were not removed", depositManager.queryDepositsByClient(client1.getClient_id(), con) == null);
		Assert.assertTrue("Client was not removed from Clients table", clientManager.query(client1, con) == null);
	}	
}
