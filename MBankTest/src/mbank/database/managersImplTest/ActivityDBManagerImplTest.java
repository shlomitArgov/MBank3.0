package mbank.database.managersImplTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import mbank.Util;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbank.database.beans.enums.ActivityType;
import mbank.database.beans.enums.ClientType;
import mbank.database.managersImpl.ActivityDBManager;
import mbank.database.managersImpl.ClientDBManager;
import mbank.exceptions.MBankException;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class ActivityDBManagerImplTest {

	static final String url = Util.DB_URL;
	static Connection con;

	private static ClientDBManager clientDBManager;
	private static ActivityDBManager activityDBManager;
	
	
	private static Client client1;
	private static Client client2;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws SQLException, MBankException {
		con = DriverManager.getConnection(url);
		clientDBManager = new ClientDBManager();
		activityDBManager = new ActivityDBManager();
		
		/* Add clients that the test activities will associated with */
		client1 = new Client("user_name3", "password1", ClientType.REGULAR, "address1", "email1", "phone1", "comment1");
		client2 = new Client("user_name4", "password2", ClientType.REGULAR, "address2", "email2", "phone2", "comment2");
		
		/* Insert the clients into the DB */
		client1.setClient_id(clientDBManager.insert(client1));
		client2.setClient_id(clientDBManager.insert(client2));
	}
	
	@After
	//cleanup
	public void tearDownAfterClass() throws Exception {
		/* Clean clients from DB */
		clientDBManager.delete(client1.getClient_id());
		clientDBManager.delete(client2.getClient_id());
	}
	@Test
	public void testActivityDBManager() {
		// test insert
		Activity activity1 = new Activity(client1.getClient_id(), 513.3, new java.util.Date(System.currentTimeMillis()) ,0.2, ActivityType.ADD_NEW_CLIENT, "activity 1 description");
		Activity activity2 = new Activity(client2.getClient_id(), 2000, new java.util.Date(System.currentTimeMillis()) , 0.3, ActivityType.REMOVE_CLIENT, "activity 2 description");
		try
		{
			activity1.setActivityId(activityDBManager.insert(activity1));
			activity2.setActivityId(activityDBManager.insert(activity2));
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Insert into Activity table failed");
		}

		// test query
		try
		{
			activityDBManager.query(activity1.getActivityType(), activity1.getClient_id());
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to query Accounts table");
		}
		

		//test queryAllAccount
		ArrayList<Activity> activityList = null;
		try
		{
			activityList = activityDBManager.queryAllActivities();
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to query all rows from Activity table");
		}
		Assert.assertTrue("Failed to query all rows from Activity table", activityList != null);
		
		// test update
		String s = "test update Activity method";
		activity1.setDescription(s);
		try
		{
			activityDBManager.update(activity1);	
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to update the Activities table");
		}
		
		// test delete
		try
		{
			activityDBManager.delete(activity1);
			activityDBManager.delete(activity2);
			
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Delete client from Clients table failed");
		}
	}
}
