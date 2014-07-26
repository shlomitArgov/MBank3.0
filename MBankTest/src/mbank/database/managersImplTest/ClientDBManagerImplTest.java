/**
 * 
 */
package mbank.database.managersImplTest;

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

	private static Connection con;
	private static Client client1;
	private static Client client2;
	/**
	 * @throws MBankException 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws SQLException, MBankException {
		
		String url=Util.DB_URL;
		con = DriverManager.getConnection(url);
		
		clientDBManager = new ClientDBManager();
		
		client1 = new Client("user_name1", "password1", ClientType.REGULAR, "address1", "email1", "phone1", "comment1");
		client2 = new Client("user_name2", "password2", ClientType.REGULAR, "address2", "email2", "phone2", "comment2");
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
			e.printStackTrace();
			Assert.fail("Insert into Clients table failed\n" + e.getLocalizedMessage());
		}
		
		// test queryAllClients
		ArrayList<Client> clientsList = null ;
		try
		{
		clientsList = clientDBManager.queryAllClients(con);
		} catch(MBankException e){
			e.printStackTrace();
			Assert.fail("Failed to query all rows from Clients table\n" + e.getLocalizedMessage());
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
			e.printStackTrace();
			Assert.fail("Failed to update client\n" + e.getLocalizedMessage());
		}

		// test delete
		try
		{
			clientDBManager.delete(client1, con);
			clientDBManager.delete(client2, con);
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Delete client from Clients table failed");
		}
	}
}
