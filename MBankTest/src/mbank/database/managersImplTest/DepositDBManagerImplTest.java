package mbank.database.managersImplTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import mbank.Util;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.enums.ClientType;
import mbank.database.beans.enums.DepositType;
import mbank.database.managersImpl.ClientDBManager;
import mbank.database.managersImpl.DepositDBManager;
import mbank.exceptions.MBankException;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class DepositDBManagerImplTest {

	static final String url = Util.DB_URL;
	static Connection con;

	private static ClientDBManager clientDBManager;
	private static DepositDBManager depositDBManager;
	
	
	private static Client client1;
	private static Client client2;
	private static Deposit deposit1;
	private static Deposit deposit2;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws SQLException, MBankException {
		con = DriverManager.getConnection(url);
		clientDBManager = new ClientDBManager();
		depositDBManager = new DepositDBManager();
		
		/* Add clients that the test activities will associated with */
		client1 = new Client("user_name3", "password1", ClientType.REGULAR, "address1", "email1", "phone1", "comment1");
		client2 = new Client("user_name4", "password2", ClientType.REGULAR, "address2", "email2", "phone2", "comment2");
		
		/* Insert the clients into the DB */
		client1.setClient_id(clientDBManager.insert(client1));
		client2.setClient_id(clientDBManager.insert(client2));
		
		/* create deposits for testing */
		deposit1 = new Deposit(client1.getClient_id(), 2000.20, DepositType.SHORT, 350, new java.util.Date(System.currentTimeMillis()), new java.util.Date(System.currentTimeMillis()));
		deposit2 = new Deposit(client2.getClient_id(), 1050.40, DepositType.LONG, 580, new java.util.Date(System.currentTimeMillis()), new java.util.Date(System.currentTimeMillis()));
	}
	
	@After
	//cleanup
	public void tearDownAfterClass() throws Exception {
		/* Clean clients from DB */
		clientDBManager.delete(client1.getClient_id());
		clientDBManager.delete(client2.getClient_id());
	}
	@Test
	public void testDepositsDBManager() {
		// test insert
		try
		{
			deposit1.setDeposit_id(depositDBManager.insert(deposit1));
			deposit2.setDeposit_id(depositDBManager.insert(deposit2));
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Insert into Deposits table failed");
		}

		// test query
		try
		{
			Deposit temp = depositDBManager.query(deposit1);
			if(temp == null)
			{
				throw new MBankException();
			}
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Query Deposits table failed");
		}

		//test queryAllAccount
		try
		{
			ArrayList<Deposit> depositsList = depositDBManager.queryDepositsByClient(client1.getClient_id());
			if (depositsList == null)
			{
				throw new MBankException();
			}
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to query all rows from Deposits table");
		}
		
		// test update
		try
		{
			deposit1.setBalance(50000);
			depositDBManager.update(deposit1);
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Update Deposits table failed");
			
		}

		
		// test delete
		try
		{
			depositDBManager.delete(deposit1);
			depositDBManager.delete(deposit2);
		}
		catch (MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Delete client from Deposits table failed");
		}
	}	
}
