package mbank;

import mbank.actions.AdminAction;
import mbank.actions.ClientAction;
import mbankExceptions.MBankException;

public class Main {
	//Project entry point
	public static void main(String[] args) throws MBankException {
		MBank bank = MBank.getInstance();
		
		/* Create CLI action menu for testing */
		//TODO add CLI actions
		AdminAction adminAction = new AdminAction(bank.getConnection(), 1);
		ClientAction clientAction = new ClientAction(bank.getConnection(), 2);
	}
}
