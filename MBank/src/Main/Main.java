package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import mbank.MBank;
import mbank.actions.AdminAction;
import mbank.actions.ClientAction;
import mbank.database.beans.Account;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbankExceptions.MBankException;

public class Main {
	private static final String UNRECOGNIZED_COMMAND = "\n---Unrecognized command---\n";
	private static final String MAIN_MENU_DIALOG = "Enter an option number:\n1. Test AdminAction methods\n2. Test ClientAction methods\n3. exit";
	private static final String ADMIN_MENU_INSTRUCTION = "---AdminAction methods menu---\nEnter an option number: ";
	private static final String CLIENT_MENU_INSTRUCTION = "---ClientAction methods menu---\nEnter an option number: ";

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
			adminActionMenu();
			break;
		case 2:
			clientActionMenu();
			break;
		case 3:
			System.out.println("\nGoodbye!");
			System.exit(0);
			break;
		default:
			break;
		}	
	}

	private static int getMainMenuActionChoice() 
	{
		System.out.println(MAIN_MENU_DIALOG);		
		int input = getNumericInput();
		while((input < 1) || (input > 3))
		{
			System.out.println(UNRECOGNIZED_COMMAND);
			System.out.println(MAIN_MENU_DIALOG);
			input = getNumericInput();
		}
	return input;		
	}
	
	private static void adminActionMenu() {
		int adminMenuChoice = getAdminMenuChoice();
		AdminActionMethods method = adminMethods[adminMenuChoice - 1];
		switch (method) {
		case CREATE_NEW_ACCOUNT:
			handleCreateNewAccount();
			break;
		case REMOVE_ACCCOUNT:
			handleRemoveAccount();
			break;
		case CREATE_NEW_CLIENT:
			handleCreateNewClient();
			break;
		case REMOVE_CLIENT:
			handleRemoveClient();
			break;
		case VIEW_ALL_DEPOSITS_DETAILS:
			handleViewAllDepositsDetails();
			break;
		case UPDATE_CLIENT_DETAILS:
			handleUpdateclientDetails();
			break;
		case VIEW_ALL_ACCOUNTS_DETAILS:
			handleViewAllAccountsDetails();
			break;
		case VIEW_ALL_ACTIVITIES_DETAILS:
			handleViewAllActivitiesDetails();
			break;
		case VIEW_ALL_CLIENTS_DETAILS:
			handleViewAllClientsDetails();
			break;
		case RETURN_TO_MAIN_MENU:
			mainMenu();
			break;
		default:
			break;
		}
		adminActionMenu();
	}

	private static void handleViewAllClientsDetails() {
		System.out.println("---Displaying all client details---");
		try 
		{
			List<Client> clients = adminAction.ViewAllClientDetails();
			for (Client client : clients) {
				System.out.println(client.toString());
			}
		} catch (MBankException e) 
		{
			System.out.println("An error occured: " + e.getLocalizedMessage());
			System.exit(1);
		}
		System.out.println();
	}

	private static void handleViewAllActivitiesDetails() {
		System.out.println("---Displaying all activities---");
		try 
		{
			List<Activity> activities = adminAction.viewAllActivitiesDetails();
			for (Activity activity : activities) {
				System.out.println(activity.toString());
			}
		} catch (MBankException e) 
		{
			System.out.println("An error occured: " + e.getLocalizedMessage());
			System.exit(1);
		}	
		System.out.println();
	}

	private static void handleViewAllAccountsDetails() {
		System.out.println("---Displaying all accounts---");
		try 
		{
			List<Account> accounts = adminAction.viewAllAccountsDetails();
			for (Account account : accounts) {
				System.out.println(account.toString());
			}
		} catch (MBankException e) 
		{
			System.out.println("An error occured: " + e.getLocalizedMessage());
			System.exit(1);
		}	
		System.out.println();		
	}

	private static void handleUpdateclientDetails() {
		// TODO Auto-generated method stub
		
	}

	private static void handleViewAllDepositsDetails() {
		// TODO Auto-generated method stub
		
	}

	private static void handleRemoveClient() {
		// TODO Auto-generated method stub
		
	}

	private static void handleCreateNewClient() {
		// TODO Auto-generated method stub
		
	}

	private static void handleRemoveAccount() {
		// TODO Auto-generated method stub
		
	}

	private static void handleCreateNewAccount() {
		// TODO Auto-generated method stub
		
	}

	private static int getAdminMenuChoice() {
		System.out.println(ADMIN_MENU_INSTRUCTION);	
		printAdminMethods();
		int input = getNumericInput();
		while((input < 1) || (input > adminMethods.length))
		{
			System.out.println(UNRECOGNIZED_COMMAND);
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
		ClientActionMethods method = clientMethods[clientMenuChoice - 1];
		switch (method) {
		case CREATE_NEW_DEPOSIT:
			break;
		case DEPOSIT_TO_ACCOUNT:
			break;
		case PRE_OPEN_DEPOSIT:
			break;
		case UPDATE_CLIENT_DETAILS:
			break;
		case VIEW_ACCOUNT_DETAILS:
			break;
		case VIEW_CLIENT_ACTIVITIES:
			break;
		case VIEW_CLIENT_DEPOSITS:
			break;
		case VIEW_CLIENT_DETAILS:
			break;
		case VIEW_SYSTEM_PROPERTY:
			break;
		case WITHDRAW_FROM_ACCOUNT:
			break;
		case RETURN_TO_MAIN_MENU:
			mainMenu();
			break;
		default:
			break;
		}
		clientActionMenu();
	}
	private static int getClientMenuChoice() {
		System.out.println(CLIENT_MENU_INSTRUCTION);
		printAClientMethods();
		int input = getNumericInput();
		while((input < 1) || (input > clientMethods.length))
		{
			System.out.println(UNRECOGNIZED_COMMAND);
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
