import static org.junit.Assert.*;

import java.sql.Connection;

import mbank.MBank;
import mbank.actions.Action;
import mbank.actions.AdminAction;
import mbank.database.beans.Client;
import mbank.database.beans.enums.ClientType;
import mbank.database.managersImpl.ClientDBManager;
import mbank.sequences.ClientIdSequence;
import mbankExceptions.MBankException;

import org.junit.Test;


public class MBankTest {

	@Test
	public void test() {
		MBank myBank = null;
		try {
			myBank = MBank.getInstance();	
			
		} catch (MBankException e) {
			fail("Failed to get instance of MBank class");
			e.printStackTrace();
		}
		String adminUserName = "system";
		String adminPassword = "admin";
		Action adminAction = null;
		ClientDBManager clientDBManager = new ClientDBManager();
		Connection con = null;
		try {
			con = myBank.getConnection();
		} catch (MBankException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Client adminClient = new Client(ClientIdSequence.getNext(), "system", "admin", ClientType.PLATINUM, "Ahad Ha'am 51, TLV", "mail@gmail.com", "555-5555555", "admin user comment");
		clientDBManager.insert(adminClient, con);
		try {
			adminAction = myBank.login(adminUserName, adminPassword);
		} catch (MBankException e) {
			fail("Failed to login to bank with admin credentials");
			e.printStackTrace();
		}
		assertTrue("Failed to retrieve AdminAction by logging in to MBank with admin credentials", adminAction instanceof AdminAction);
		//cleanup
		clientDBManager.delete(adminClient, con);
	}
}