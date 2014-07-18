package model.login;

import mbank.MBank;
import mbank.actions.AdminAction;
import mbankExceptions.MBankException;
import view.mainFrame.MainFrame;

public class LoginAction {
	private static AdminAction adminAction;
	
	public static void login(String username, char[] password) throws MBankException {

		/* perform login - will throw exception if login fails */
		MBank myBank = MBank.getInstance();
		if((username.length() == 0) || (password.length == 0))
		{
			throw new MBankException("Username and password fields must not be empty");
		}
		adminAction = (AdminAction) myBank.login(username, String.valueOf(password));

		/*
		 * If we made it here (no exception occurred during login: update the UI main frame by switching to the console page
		 */
		MainFrame.mainCardLayout.show(MainFrame.getInstance().getContentPane(), MainFrame.consoleCard);
	}
	public static AdminAction getAdminAction() {
		return adminAction;
	}
}
