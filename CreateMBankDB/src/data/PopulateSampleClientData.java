package data;

import java.util.Calendar;
import java.util.Date;

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
		Date tmpDate = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(tmpDate); 
		c.add(Calendar.DATE, 120);
		tmpDate = c.getTime();
		// Create short deposit
		clientAction.createNewDeposit(17000, tmpDate);
		// Create long deposit
		c.add(Calendar.DATE,380);
		tmpDate = c.getTime();
		clientAction.createNewDeposit(30000, tmpDate);
	}
}
