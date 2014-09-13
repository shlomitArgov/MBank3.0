package mbank;
import static org.junit.Assert.*;

import mbank.MBank;
import mbank.actions.Action;
import mbank.actions.AdminAction;
import mbank.database.beans.Client;
import mbank.database.beans.enums.ClientType;
import mbank.database.managersImpl.ClientDBManager;
import mbankExceptions.MBankException;

import org.junit.Test;


public class MBankTest {

	@Test
	public void test() {
		MBank myBank = null;
		myBank = MBank.getInstance();	
		String adminUserName = "system";
		String adminPassword = "admin";
		Action adminAction = null;
		ClientDBManager clientDBManager = new ClientDBManager();
		Client adminClient = null;
		try {
			adminClient = new Client("system", "admin", ClientType.PLATINUM, "Ahad Ha'am 51, TLV", "mail@gmail.com", "555-5555555", "admin user comment");
		} catch (MBankException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			adminClient.setClient_id(clientDBManager.insert(adminClient));
		} catch (MBankException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			adminAction = myBank.login(adminUserName, adminPassword);
		} catch (MBankException e) {
			fail("Failed to login to bank with admin credentials");
			e.printStackTrace();
		}
		assertTrue("Failed to retrieve AdminAction by logging in to MBank with admin credentials", adminAction instanceof AdminAction);
		//cleanup
		try {
			clientDBManager.delete(adminClient.getClient_id());
		} catch (MBankException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}