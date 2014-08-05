package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mbank.MBank;
import mbank.actions.AdminAction;
import mbank.actions.ClientAction;
import mbankExceptions.MBankException;

public class Main {
	private static final String MAIN_MENU_DIALOG = "Enter the number of the action type you would like to test:\n1. Client action\n2. Admin action";
	private static final String ADMIN_MENU_INSTRUCTION = "Enter the number of the method you would like to test: ";
	private static final String CLIENT_MENU_INSTRUCTION = "Enter the number of the method you would like to test: ";

	private static AdminAction adminAction;
	private static ClientAction clientAction;
	
	private static AdminActionMethods[] adminMethods;
	private static ClientActionMethods[] clientMethods;
	//Project entry point
	public static void main(String[] args) throws MBankException {
		MBank bank = MBank.getInstance();
		
		/* Create CLI action menu for testing */
		
		adminAction = new AdminAction(bank.getConnection(), 1);
		long clientId = adminAction.addNewClient("CLI Client" + System.currentTimeMillis(), new char[]{'p','w','d'}, "home", "mail@home.com", "555-555555", 1000000);
		clientAction = new ClientAction(bank.getConnection(), clientId);
		
		System.out.println("***Welcome to MBank***\n");

		mainMenu();
	}

	private static void mainMenu() 
	{
	int mainMenuChoice = getMainMenuActionChoice();
		
		switch (mainMenuChoice) {
		case 1:
			clientActionMenu();
			break;
		case 2:
			adminActionMenu();
			break;
		default:
			break;
		}	
	}

	private static int getMainMenuActionChoice() 
	{
		System.out.println(MAIN_MENU_DIALOG);		
		int input = getNumericInput();
		while((input != 1) && (input != 2))
		{
			System.out.println("Unrecognized command.\n");
			System.out.println(MAIN_MENU_DIALOG);
			input = getNumericInput();
		}
	return input;		
	}
	
	private static void adminActionMenu() {
		int adminMenuChoice = getAdminMenuChoice();
		AdminActionMethods method = adminMethods[adminMenuChoice + 1];
		switch (method) {
		case CREATE_NEW_ACCOUNT:
			break;
		case REMOVE_ACCCOUNT:
			break;
		case CREATE_NEW_CLIENT:
			break;
		case REMOVE_CLIENT:
			break;
		case RETURN_TO_MAIN_MENU:
			break;
		case UPDATE_CLIENT_DETAILS:
			break;
		case VIEW_ALL_ACCOUNTS_DETAILS:
			break;
		case VIEW_ALL_ACTIVITIES_DETAILS:
			break;
		case VIEW_ALL_CLIENTS_DETAILS:
			break;
		case VIEW_ALL_DEPOSITS_DETAILS:
			break;
		default:
			break;
		}
	}

	private static int getAdminMenuChoice() {
		System.out.println(ADMIN_MENU_INSTRUCTION);	
		printAdminMethods();
		int input = getNumericInput();
		while((input < 1) || (input > adminMethods.length))
		{
			System.out.println("Unrecognized command.\n");
			System.out.println(ADMIN_MENU_INSTRUCTION);
			printAdminMethods();
			input = getNumericInput();
		}
	return input;	
	}

	private static void printAdminMethods() {
		adminMethods = AdminActionMethods.values();
		for (int i = 0; i < adminMethods.length; i++) {
			System.out.println((i+1) + ". " + adminMethods[i].getName());
		}
	}

	private static void clientActionMenu() {
		int clientMenuChoice = getClientMenuChoice();		
	}

	private static int getClientMenuChoice() {
		System.out.println(CLIENT_MENU_INSTRUCTION);
		printAClientMethods();
		int input = getNumericInput();
		while((input < 1) || (input > clientMethods.length))
		{
			System.out.println("Unrecognized command.\n");
			System.out.println(CLIENT_MENU_INSTRUCTION);
			printAClientMethods();
			input = getNumericInput();
		}
	return input;
	}

	private static void printAClientMethods() {
		clientMethods = ClientActionMethods.values();
		for (int i = 0 ; i < clientMethods.length; i++) {
			System.out.println((i+1) + ". " + clientMethods[i].getName());
		}		
	}

	/**
	 * 
	 * @return numeric input in case of valid numeric input, -1 otherwise
	 */
	private static int getNumericInput() 
	{
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 
	      String input = null;
	 
	      /* Read the numeric input entered by the user in the command-line;
	       * need to use try/catch with the readLine() method */
	      try {
	         input = br.readLine();
	      } catch (IOException ioe) {
	         System.out.println("IO error occured. Program will exit.");
	         System.exit(1);
	      }
	      
	      int num = 0;
	      try 
	      {
			num = Integer.parseInt(input.trim());
	      } 
	      catch (NumberFormatException e) 
	      {
	    	  return -1;
	      }
		return num; 
		}
	
	private enum ClientActionMethods {

		VIEW_CLIENT_DETAILS("VIEW_CLIENT_DETAILS"),
		VIEW_ACCOUNT_DETAILS("VIEW_ACCOUNT_DETAILS"),
		VIEW_CLIENT_DEPOSITS("VIEW_CLIENT_DEPOSITS"),
		VIEW_CLIENT_ACTIVITIES("VIEW_CLIENT_ACTIVITIES"),
		VIEW_SYSTEM_PROPERTY("VIEW_SYSTEM_PROPERTY"),
		UPDATE_CLIENT_DETAILS("UPDATE_CLIENT_DETAILS"),
		WITHDRAW_FROM_ACCOUNT("WITHDRAW_FROM_ACCOUNT"),
		DEPOSIT_TO_ACCOUNT("DEPOSIT_TO_ACCOUNT"),
		CREATE_NEW_DEPOSIT("CREATE_NEW_DEPOSIT"),
		PRE_OPEN_DEPOSIT("PRE_OPEN_DEPOSIT"),
		RETURN_TO_MAIN_MENU("RETURN_TO_MAIN_MENU");
		
		private String name;
		ClientActionMethods(String name)
		{
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}
	
	
	private enum AdminActionMethods {
		CREATE_NEW_CLIENT("CREATE_NEW_CLIENT"), 
		REMOVE_CLIENT("REMOVE_CLIENT"),
		UPDATE_CLIENT_DETAILS("UPDATE_CLIENT_DETAILS"),
		CREATE_NEW_ACCOUNT("CREATE_NEW_ACCOUNT"),
		REMOVE_ACCCOUNT("REMOVE_ACCCOUNT"),
		VIEW_ALL_ACCOUNTS_DETAILS("VIEW_ALL_ACCOUNTS_DETAILS"),
		VIEW_ALL_DEPOSITS_DETAILS("VIEW_ALL_DEPOSITS_DETAILS"),
		VIEW_ALL_ACTIVITIES_DETAILS("VIEW_ALL_ACTIVITIES_DETAILS"),
		VIEW_ALL_CLIENTS_DETAILS("VIEW_ALL_CLIENTS_DETAILS"),
		RETURN_TO_MAIN_MENU("RETURN_TO_MAIN_MENU");
		//UPDATE_SYSTEM_PROPERTY can be tested via the SWING UI
		private String name;
		
		AdminActionMethods(String name)
		{
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
