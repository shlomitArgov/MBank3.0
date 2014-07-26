package mbank.database.managersImplTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import mbank.Util;
import mbank.database.beans.Account;
import mbank.database.beans.Client;
import mbank.database.beans.enums.ClientType;
import mbank.database.managersImpl.AccountDBManager;
import mbank.database.managersImpl.ClientDBManager;
import mbankExceptions.MBankException;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountsDBManagerImplTest {

	static final String url = Util.DB_URL;
	static Connection con;

	private static ClientDBManager clientDBManager;
	private static AccountDBManager accountDBManager;
		
	private static Client client1;
	private static Client client2;
	
	private static Account account1;
	private static Account account2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws SQLException, MBankException {
		con = DriverManager.getConnection(url);
		clientDBManager = new ClientDBManager();
		accountDBManager = new AccountDBManager();
		
		/* Add clients that the test accounts will associated with */
		client1 = new Client("user_name3", "password1", ClientType.REGULAR, "address1", "email1", "phone1", "comment1");
		client2 = new Client("user_name4", "password2", ClientType.REGULAR, "address2", "email2", "phone2", "comment2");
		
		/* Insert the clients into the DB */
		client1.setClient_id(clientDBManager.insert(client1, con));
		client2.setClient_id(clientDBManager.insert(client2, con));
		
		/* Create accounts that are associated with the test clients */
		account1 = new Account(client1.getClient_id(), 5520.14, 10000, "account 1 comment");
		account2 = new Account(client2.getClient_id(), 751.7, 15000, "account 2 comment");
	}
	
	@After
	//cleanup
	public void tearDownAfterClass() throws Exception {
		/* Clean accounts from DB */
		clientDBManager.delete(client1, con);
		clientDBManager.delete(client2, con);
	}

	@Test
	public void testAccountDBManagerActions() throws MBankException{

		// test insert
		try
		{
			accountDBManager.insert(account1, con);
			accountDBManager.insert(account2, con);
		}
		catch (MBankException e)
		{
			Assert.fail("Failed to insert into accounts table");
			e.printStackTrace();
		}
		
		//test queryAccountByClient
		try
		{
			account1 = accountDBManager.queryAccountByClient(client1.getClient_id(), con);
			account2 = accountDBManager.queryAccountByClient(client2.getClient_id(), con);
		}
		
		catch(MBankException m)
		{
			Assert.fail("Failed to query Accounts table\n" + m.getLocalizedMessage());
			m.printStackTrace();
		}
		
		// test query
		Account temp = null;
		try
		{
			temp = accountDBManager.query(account1, con);
		}
		catch(MBankException e)
		{
			Assert.fail("Query Accounts table failed");
		}
		Assert.assertTrue("Query Accounts table failed", account1.equals(temp));
		
		// test update
		account1.setComment("updatedComment");
		account1.setBalance(3000);
	
		try
		{
			accountDBManager.update(account1, con);
		}
		catch(MBankException e)
		{
			Assert.fail("Failed to update the accounts table\n" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		// test delete
		try
		{
			accountDBManager.delete(account1, con);
			accountDBManager.delete(account2, con);
		}
		catch(MBankException e)
		{
			Assert.fail("Delete client from Clients table failed\n" + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

}
