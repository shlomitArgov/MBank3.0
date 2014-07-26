/**
 * 
 */
package mbank.database.managersImplTest;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import mbank.Util;
import mbank.database.beans.Client;
import mbank.database.beans.enums.ClientType;
import mbank.database.managersImpl.ClientDBManager;
import mbankExceptions.MBankException;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Shlomit Argov
 * 
 */
public class ClientDBManagerImplTest {
	private static ClientDBManager clientDBManager;

//	private static DepositDBManager depositDBManager;
//	private static PropertyDBManager propertyDBManager;

	private static Connection con;
	private static Client client1;
	private static Client client2;
	/**
	 * @throws MBankException 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws SQLException, MBankException {
		
		clientDBManager = new ClientDBManager();
//		accountDBManager = new AccountDBManager();
//		activityDBManager = new ActivityDBManager();
//		depositDBManager = new DepositDBManager();
//		propertyDBManager = new PropertyDBManager();
		String url=Util.DB_URL;
		con = DriverManager.getConnection(url);
		
		client1 = new Client("user_name1", "password1", ClientType.REGULAR, "address1", "email1", "phone1", "comment1");
		client2 = new Client("user_name2", "password2", ClientType.REGULAR, "address2", "email2", "phone2", "comment2");
		
//		deposit1 = new Deposit(client1.getClient_id(), 2000.20, DepositType.SHORT, 350, new java.util.Date(System.currentTimeMillis()), new java.util.Date(System.currentTimeMillis()));
//		deposit2 = new Deposit(client2.getClient_id(), 1050.40, DepositType.LONG, 580, new java.util.Date(System.currentTimeMillis()), new java.util.Date(System.currentTimeMillis()));
//		deposit3 = new Deposit(clientID1, 1050.40, DepositType.LONG, 580, new java.util.Date(System.currentTimeMillis()), new java.util.Date(System.currentTimeMillis()));
//		
//		activity1 = new Activity(client1.getClient_id(), 513.3, new java.util.Date(System.currentTimeMillis()) , 0.2, ActivityType.ADD_NEW_CLIENT ,"activity 1 description");
//		activity2 = new Activity(client2.getClient_id(), 2000, new java.util.Date(System.currentTimeMillis()) , 0.3, ActivityType.REMOVE_CLIENT, "activity 2 description");
//		
//		property1 = new Property("prop_key_1", "prop_value_1");
//		property2 = new Property("prop_key_2", "prop_value_2");
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	//cleanup
	public void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testClientDBManagerActions() throws MBankException {
		// test insert 
		try
		{
			client1.setClient_id(clientDBManager.insert(client1, con));
			client2.setClient_id(clientDBManager.insert(client2, con));
			 
		}
		catch (MBankException e)
		{
			Assert.fail("Insert into Clients table failed\n" + e.getLocalizedMessage());
		}
		
		// test queryAllClients
		ArrayList<Client> clientsList = null ;
		try
		{
		clientsList = clientDBManager.queryAllClients(con);
		} catch(MBankException e){
			Assert.fail("Failed to query all rows from Clients table\n" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		ArrayList<Client> originalClients = new ArrayList<>();
		originalClients.add(client1);
		originalClients.add(client2);
		/* Check that all clients that were manually added are returned by the queryAllClients method (did not check that these are the only
		 * clients returned because there may be other clients defined by other tests */
		Assert.assertTrue("Failed to query all rows from Clients table\n", clientsList.containsAll(originalClients));
		
		// test update
		client1.setClient_name("updateClientName");
		client1.setAddress("updatecAddress");
		client1.setType(ClientType.GOLD);
		client1.setEmail("updatedEmail");
		client1.setPhone("updatedPhone");
		client1.setPassword("updatedPassword");
		
		try {
			clientDBManager.update(client1, con);
		} catch (MBankException e) {
			Assert.fail("Failed to update client\n" + e.getLocalizedMessage());
			e.printStackTrace();
			fail();
		}

		// test delete
		try
		{
			clientDBManager.delete(client1, con);
			clientDBManager.delete(client2, con);
		}
		catch(MBankException e)
		{
			Assert.fail("Delete client from Clients table failed");
		}
	}
//	
//	@Test
//	public void testAccountDBManagerActions() throws MBankException{
//		// wait until clients have been inserted into the DB and received IDs 
//		while(client1.getClient_id() == 0 || client2.getClient_id() == 0)
//		{
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		account1 = new Account(client1.getClient_id(), 5520.14, 10000, "account 1 comment");
//		account2 = new Account(client2.getClient_id(), 751.7, 15000, "account 2 comment");
//		// test insert
//		try
//		{
//			accountDBManager.insert(account1, con);
//			accountDBManager.insert(account2, con);
//		}
//		catch (MBankException e)
//		{
//			Assert.fail("Failed to insert into accounts table");
//			e.printStackTrace();
//		}
//		
//		//test queryAccountByClient
//		try
//		{
//			account1 = accountDBManager.queryAccountByClient(client1.getClient_id(), con);
//			account2 = accountDBManager.queryAccountByClient(client2.getClient_id(), con);
//		}
//		
//		catch(MBankException m)
//		{
//			Assert.fail("Failed to query Accounts table\n" + m.getLocalizedMessage());
//			m.printStackTrace();
//		}
//		
//		// test query
//		Account temp = null;
//		try
//		{
//			temp = accountDBManager.query(account1, con);
//		}
//		catch(MBankException e)
//		{
//			Assert.fail("Query Accounts table failed");
//		}
//		Assert.assertTrue("Query Accounts table failed", account1.equals(temp));
//		
//		// test update
//		account1.setComment("updatedComment");
//		account1.setBalance(3000);
//	
//		try
//		{
//			accountDBManager.update(account1, con);
//		}
//		catch(MBankException e)
//		{
//			Assert.fail("Failed to update the accounts table\n" + e.getLocalizedMessage());
//			e.printStackTrace();
//		}
//		
//		// test delete
//		try
//		{
//			accountDBManager.delete(account1, con);
//			accountDBManager.delete(account2, con);
//		}
//		catch(MBankException e)
//		{
//			Assert.fail("Delete client from Clients table failed\n" + e.getLocalizedMessage());
//			e.printStackTrace();
//		}
//	}
//	
	/*@Test
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
	}*/
}
