package data;

import java.util.Calendar;
import java.util.Date;

import mbank.actions.AdminAction;
import mbank.actions.ClientAction;
import mbank.actions.ClientActionInterface;
import mbank.exceptions.MBankException;

import org.junit.Test;

public class PopulateSampleClientData
{
	private AdminAction adminAction = new AdminAction(1);
	
	@Test
	public void test() throws MBankException
	{
		insertMockClientData("testClient1", "pwd".toCharArray(), "Diagon Alley", "client1@test.com", "111-555555", 20000.0);
		insertMockClientData("testClient2", "pwd".toCharArray(), "The outer regions", "client2@test.com", "222-555555", 30000.0);
	}

	private void insertMockClientData(String clientName, char[] clientPwd, String clientAddress, String clientEmail, String clientPhoneNumber, Double depositAmount) throws MBankException
	{	
		long clientId = adminAction.addNewClient(clientName, clientPwd, clientAddress, clientEmail, clientPhoneNumber, depositAmount);
		ClientActionInterface clientAction = new ClientAction(clientId);
		Date tmpDate = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(tmpDate); 
		c.add(Calendar.DATE, 120);
		tmpDate = c.getTime();
		// Create short deposit
		clientAction.createNewDeposit(7000, tmpDate);
		// Create long deposit
		c.add(Calendar.DATE,380);
		tmpDate = c.getTime();
		clientAction.createNewDeposit(3000, tmpDate);
	}
}
