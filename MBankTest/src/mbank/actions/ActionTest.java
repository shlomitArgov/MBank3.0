package mbank.actions;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import mbank.Util;
import mbank.actions.AdminAction;
import mbank.actions.ClientAction;
import mbank.actions.TableValue;
import mbank.database.beans.Client;
import mbank.database.beans.enums.ClientAttributes;
import mbank.database.beans.enums.ClientType;
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
public class ActionTest {
	private static Connection con;
	private static ClientManager clientManager;
	private static Client client1;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String url = Util.DB_URL;
		con = DriverManager.getConnection(url);
		
		client1 = new Client("moshe1", "pass1", ClientType.REGULAR, "address1", "email1", "phone1", "comment1");
		clientManager = new ClientDBManager();			
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		clientManager.delete(client1, con);
	}

	@Test
	public void testUpdateClientDetails() throws MBankException {
		try
		{
			client1.setClient_id(clientManager.insert(client1, con));	
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to insert client into the Clients table");
		}
		AdminAction adminAction =  new AdminAction(con, client1.getClient_id());
		
		TableValue[] details = new TableValue[]{new TableValue(ClientAttributes.ADDRESS.getAttribute(), "address2"),new TableValue(ClientAttributes.PHONE.getAttribute(),"phone2"),new TableValue(ClientAttributes.EMAIL.getAttribute(), "email2"), new TableValue(ClientAttributes.CLIENT_TYPE.getAttribute(), ClientType.PLATINUM.getTypeStringValue()), new TableValue(ClientAttributes.COMMENT.getAttribute(), "Comment text")};
		adminAction.updateClientDetails(Long.toString(client1.getClient_id()), details);
		
		Client c = null;
		try
		{
			c = clientManager.query(client1, con);	
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("AdminAction: updateClientDetails method failed");
		}
		Assert.assertTrue("AdminAction: updateClientDetails method failed", c.getAddress().equals("address2") && c.getEmail().equals("email2") && c.getPhone().equals("phone2"));
		
		ClientAction clientAction = new ClientAction(con, client1.getClient_id());
		
		try
		{
			clientAction.updateClientDetails(Long.toString(client1.getClient_id()), details);
			fail("Updated client type with clientAction - permission violation");
		}
		catch(MBankException e)
		{
			Assert.assertTrue(true);
		}		
	}
}