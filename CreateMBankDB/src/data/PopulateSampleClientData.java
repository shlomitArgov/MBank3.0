package data;

import mbank.actions.AdminAction;
import mbank.actions.ClientAction;
import mbank.exceptions.MBankException;

import org.junit.Test;

public class PopulateSampleClientData
{

	@Test
	public void test() throws MBankException
	{
		AdminAction adminAction = new AdminAction(1);
		long clientId = adminAction.addNewClient("John Doe", "pwd".toCharArray(), "Diagon Alley", "john@doe.com", "555-555555", 50000);
		ClientAction clientAction = new ClientAction(clientId);
		clientAction.createNewDeposit(17000, new java.util.Date(System.currentTimeMillis() + 24*3600*320));
		clientAction.createNewDeposit(30000, new java.util.Date(System.currentTimeMillis() + 24*3600*120));
	}
}
