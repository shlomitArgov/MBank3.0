/**
 * 
 */
package mbank.database.managersImplTest;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import mbank.database.beans.Account;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.Property;
import mbank.database.beans.enums.ActivityType;
import mbank.database.beans.enums.ClientType;
import mbank.database.beans.enums.DepositType;
import mbank.database.managersImpl.AccountDBManager;
import mbank.database.managersImpl.ActivityDBManager;
import mbank.database.managersImpl.ClientDBManager;
import mbank.database.managersImpl.DepositDBManager;
import mbank.database.managersImpl.PropertyDBManager;
import mbank.sequences.AccountIdSequence;
import mbank.sequences.ClientIdSequence;
import mbankExceptions.MBankException;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Shlomit Argov
 * 
 */
public class DBManagersTest {
	private static ClientDBManager clientDBManager;
	private static AccountDBManager accountDBManager;
	private static ActivityDBManager activityDBManager;
	private static DepositDBManager depositDBManager;
	private static PropertyDBManager propertyDBManager;
	static long clientID1;
	static long clientID2;
	static long clientID3;
	static long accountID1;
	static long accountID2;
	static long accountID3;
	private static Connection con;
	private static Client client1;
	private static Client client2;
	private static Account account1;
	private static Account account2;
//	private static Account account3;
	private static Deposit deposit1;
	private static Deposit deposit2;
	private static Deposit deposit3;
	private static Activity activity1;
	private static Activity activity2;
	private static Property property1;
	private static Property property2;
	
	/**
	 * @throws MBankException 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws SQLException, MBankException {
//		clientID1 = ClientIdSequence.getNext();
//		clientID2 = ClientIdSequence.getNext();
//		clientID3 = ClientIdSequence.getNext();
//		accountID1 = AccountIdSequence.getNext();
//		accountID2 = AccountIdSequence.getNext();
//		accountID3 = AccountIdSequence.getNext();
//		
		clientID1 = ClientIdSequence.getNext();
//		clientID2 = ClientIdSequence.getNext();
//		clientID3 = ClientIdSequence.getNext();
//		accountID1 = AccountIdSequence.getNext();
//		accountID2 = AccountIdSequence.getNext();
//		accountID3 = AccountIdSequence.getNext();
		
		clientDBManager = new ClientDBManager();
		accountDBManager = new AccountDBManager();
		activityDBManager = new ActivityDBManager();
		depositDBManager = new DepositDBManager();
		propertyDBManager = new PropertyDBManager();
		String url="jdbc:derby://localhost:1527/MBankDB;";
		con = DriverManager.getConnection(url);
		
		client1 = new Client("user_name", "password",
				ClientType.REGULAR, "address", "email", "phone", "comment");
		
		client2 = new Client("user_name2", "password2",
				ClientType.REGULAR, "address2", "email2", "phone2", "comment2");
		
		account1 = new Account(clientID1, 5520.14, 10000, "account 1 comment");
		account2 = new Account(clientID2, 751.7, 15000, "account 2 comment");
//		account3 = new Account(accountID3, clientID1, 751.7, 15000, "account 3 comment");
		
		deposit1 = new Deposit(clientID1, 2000.20, DepositType.SHORT, 350, new java.util.Date(System.currentTimeMillis()), new java.util.Date(System.currentTimeMillis()));
		deposit2 = new Deposit(clientID2, 1050.40, DepositType.LONG, 580, new java.util.Date(System.currentTimeMillis()), new java.util.Date(System.currentTimeMillis()));
		deposit3 = new Deposit(clientID1, 1050.40, DepositType.LONG, 580, new java.util.Date(System.currentTimeMillis()), new java.util.Date(System.currentTimeMillis()));
		
		activity1 = new Activity(clientID1, 513.3, new java.util.Date(System.currentTimeMillis()) , 0.2, ActivityType.ADD_NEW_CLIENT ,"activity 1 description");
		activity2 = new Activity(clientID2, 2000, new java.util.Date(System.currentTimeMillis()) , 0.3, ActivityType.REMOVE_CLIENT, "activity 2 description");
		
		property1 = new Property("prop_key_1", "prop_value_1");
		property2 = new Property("prop_key_2", "prop_value_2");
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	//cleanup
	public void tearDownAfterClass() throws Exception {
//		clientDBManager.delete(client1, con);
//		clientDBManager.delete(client2, con);
//		accountDBManager.delete(account1, con);
//		accountDBManager.delete(account2, con);
//		accountDBManager.delete(account3, con);
//		depositDBManager.delete(deposit1, con);
//		depositDBManager.delete(deposit2, con);
//		depositDBManager.delete(deposit3, con);
//		activityDBManager.delete(activity1, con);
//		activityDBManager.delete(activity2, con);
//		propertyDBManager.delete(property2, con);
	}

	@Test
	public void testClientDBManagerActions() throws MBankException {
		// test insert
		try
		{
			 clientDBManager.insert(client1, con);
			 
		}
		catch (MBankException e)
		{
			Assert.fail("Insert into Clients table failed\n" + e.getLocalizedMessage());
		}
		

		//test queryAllClients
		try {
			clientDBManager.insert(client2, con);
		} catch (MBankException e1) {
			throw e1;
		}
		ArrayList<Client> clientsList = clientDBManager.queryAllClients(con);
		ArrayList<Client> originalClients = new ArrayList<>();
		originalClients.add(client1);
		originalClients.add(client2);
		Assert.assertTrue("Failed to query all rows from Clients table", clientsList.containsAll(originalClients) && originalClients.containsAll(clientsList));
		
		// test update
		client1.setClient_name("updateClientName");
		try {
			clientDBManager.update(client1, con);
		} catch (MBankException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		Assert.assertTrue("Update Clients table failed", clientDBManager.query(client1, con).getClient_name().equals("updateClientName"));
//		
//		// test delete
//		Assert.assertTrue("Delete client from Clients table failed", clientDBManager.delete(client1, con));
	}
	
	@Test
	public void testAccountDBManagerActions() throws MBankException{
		// test insert
		try{
			accountDBManager.insert(account1, con);
		}
		catch(MBankException e){
			Assert.fail("Insert into Accounts table failed\n" + e.getLocalizedMessage());
		}
		

		// test query
		Assert.assertTrue("Query Accounts table failed", accountDBManager.query(account1, con).equals(account1));

		//test queryAccountByClient
		Account account = null;
		try
		{
		accountDBManager.insert(account2, con);
		account = accountDBManager.queryAccountByClient(clientID2, con);
		}
		catch(MBankException m)
		{
			throw m;
		}
		Assert.assertTrue("Failed to query Accounts table", account.equals(account2));
		
		// test update
		String s = "test update Accounts method";
		account1.setComment(s);
		accountDBManager.update(account1, con);
		Assert.assertTrue("Update Clients table failed", accountDBManager.query(account1, con).getComment().equals(s));
//		
//		// test delete
//		Assert.assertTrue("Delete client from Clients table failed", accountDBManager.delete(account1, con));
	}
	
	@Test
	public void testActivityDBManager() {
		// test insert
		Activity activity1 = new Activity(1, clientID1, 513.3, new java.util.Date(System.currentTimeMillis()) ,0.2, ActivityType.ADD_NEW_CLIENT, "activity 1 description");
		Activity activity2 = new Activity(2, clientID2, 2000, new java.util.Date(System.currentTimeMillis()) , 0.3, ActivityType.REMOVE_CLIENT, "activity 2 description");
		Assert.assertTrue("Insert into Activity table failed",
				activityDBManager.insert(activity1, con));

		// test query
		Assert.assertTrue("Query Accounts table failed", activityDBManager.query(activity1.getActivityType(), activity1.getClient_id(), con).equals(activity1));

		//test queryAllAccount
		activityDBManager.insert(activity2, con);
		ArrayList<Activity> activityList = activityDBManager.queryAllActivities(con);
		ArrayList<Activity> originalActivities = new ArrayList<>();
		originalActivities.add(activity1);
		originalActivities.add(activity2);
		Assert.assertTrue("Failed to query all rows from Clients table", activityList.containsAll(originalActivities) && originalActivities.containsAll(activityList));
		
		// test update
		String s = "test update Activity method";
		activity1.setDescription(s);
		activityDBManager.update(activity1, con);
		Assert.assertTrue("Update Activities table failed", activityDBManager.query(activity1.getActivityType(),activity1.getClient_id(), con).getDescription().equals(s));
//		
//		// test delete
//		Assert.assertTrue("Delete client from Clients table failed", activityDBManager.delete(activity1, con));
	}
	@Test
	public void testDepositsDBManager() {
		// test insert
		Assert.assertTrue("Insert into Deposits table failed",
				depositDBManager.insert(deposit1, con));

		// test query
		Assert.assertTrue("Query Deposits table failed", depositDBManager.query(deposit1, con).equals(deposit1));

		//test queryAllAccount
		depositDBManager.insert(deposit2, con);
		depositDBManager.insert(deposit3, con);
		ArrayList<Deposit> depositsList = depositDBManager.queryDepositsByClient(clientID1, con);
		ArrayList<Deposit> originalDeposits = new ArrayList<>();
		originalDeposits.add(deposit1);
		originalDeposits.add(deposit3);
		Assert.assertTrue("Failed to query all rows from Deposits table", depositsList.containsAll(originalDeposits) && originalDeposits.containsAll(depositsList));
		
		// test update
		deposit1.setClient_id(deposit2.getClient_id());
		depositDBManager.update(deposit1, con);
		Assert.assertTrue("Update Deposits table failed", (depositDBManager.query(deposit1, con).getClient_id()) == (deposit2.getClient_id()));
//		
//		// test delete
//		Assert.assertTrue("Delete client from Deposits table failed", depositDBManager.delete(deposit1, con));
	}
	
	@Test
	public void testPropertyDBManager() {
		// test insert
		Assert.assertTrue("Insert into Properties table failed",
				propertyDBManager.insert(property1, con));

		// test query
		Property queryResult = propertyDBManager.query(property1.getProp_key(), con);
		Assert.assertTrue("Query Properties table failed", queryResult.equals(property1));

		// test update
		property1.setProp_value(property2.getProp_value());
		propertyDBManager.update(property1, con);
		queryResult = propertyDBManager.query(property1.getProp_key(), con);
		Assert.assertTrue("Update Properties table failed",queryResult.getProp_value().equals(property2.getProp_value()));
//		
//		// test delete
//		Assert.assertTrue("Delete property from Properties table failed", propertyDBManager.delete(property1, con));
	}
}
