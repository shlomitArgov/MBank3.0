package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import mbank.MBank;
import mbank.actions.AdminAction;
import mbank.actions.ClientAction;
import mbank.actions.TableValue;
import mbank.database.beans.Account;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.enums.ClientAttributes;
import mbank.database.managersImpl.ClientDBManager;
import mbankExceptions.MBankException;

public class Main {
	private static final String AN_ERROR_OCCURED = "An error occured: ";
	private static final String UNRECOGNIZED_COMMAND = "\n---Unrecognized command---";
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
		case ADD_NEW_CLIENT:
			handleAddNewClient();
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
			if(clients.isEmpty())
			{
				System.out.println("No clients available");
			}
			for (Client client : clients) {
				System.out.println(client.toString());
			}
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			System.exit(1);
		}
		System.out.println();
	}

	private static void handleViewAllActivitiesDetails() {
		System.out.println("---Displaying all activities---");
		try 
		{
			List<Activity> activities = adminAction.viewAllActivitiesDetails();
			if(activities.isEmpty())
			{
				System.out.println("No activities available");
			}
			for (Activity activity : activities) {
				System.out.println(activity.toString());
			}
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			System.exit(1);
		}	
		System.out.println();
	}

	private static void handleViewAllAccountsDetails() {
		System.out.println("---Displaying all accounts---");
		try 
		{
			List<Account> accounts = adminAction.viewAllAccountsDetails();
			if(accounts.isEmpty())
			{
				System.out.println("No accounts available");
			}
			for (Account account : accounts) {
				System.out.println(account.toString());
			}
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			System.exit(1);
		}	
		System.out.println();		
	}

	private static void handleUpdateclientDetails() {
		String clientId = getValidStringInput("Enter the ID of the client whose details you wish to edit: ");
		String columnName = selectClientAttributeToEdit();
		String columnValue = getValidStringInput("Enter new value for the chosen client detail: ");
		TableValue details = new TableValue(columnName, columnValue);
		try {
			adminAction.updateClientDetails(clientId, details);
			System.out.println("\n---Updated client [id = " + clientId + "] details successfully---\n");
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			System.exit(1);
		}	
		System.out.println();		
	}

	private static String selectClientAttributeToEdit() {
		int attributeNum = 0;
		ClientAttributes[] clientAttributes;
		do
		{
			System.out.println("Enter the number of the attribute you wish to edit: ");
			clientAttributes = ClientAttributes.values();
			for (int i = 0; i < clientAttributes.length; i++) {
				System.out.println((i+1) + ". " + clientAttributes[i].getAttribute());
			}
			attributeNum = getNumericInput();
			if (attributeNum < 1 || attributeNum > clientAttributes.length)
			{
				System.out.println(UNRECOGNIZED_COMMAND);
			}
		} while(attributeNum < 1 || attributeNum > clientAttributes.length);
		 
		switch (attributeNum) 
		{
		case 1:
			return clientAttributes[0].getAttribute();
		case 2:
			return clientAttributes[1].getAttribute();
		case 3:
			return clientAttributes[2].getAttribute();
		case 4:
			return clientAttributes[3].getAttribute();
		case 5:
			return clientAttributes[4].getAttribute();	
		default:
			break;
		}
		return clientAttributes[0].getAttribute(); //default
	}

	private static void handleViewAllDepositsDetails() {
		System.out.println("---Displaying all deposits---");
		try 
		{
			List<Deposit> deposits = adminAction.viewAllDepositsDetails();
			if(deposits.isEmpty())
			{
				System.out.println("No deposits available");
			}
			for (Deposit deposit : deposits) {
				System.out.println(deposit.toString());
			}
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			System.exit(1);
		}	
		System.out.println();		
	}

	private static void handleRemoveClient() {
		long clientId = getValidLongInput("Enter the ID of the client you wish to remove");
		/* Get the client bean from the DB */
		ClientDBManager clientManager = new ClientDBManager();
		Client tempClient = null;
		try {
			tempClient = clientManager.query(clientId, MBank.getInstance().getConnection());
		} catch (MBankException e) 
		{
			System.out.println("Failed to retrieve client \n" + e.getLocalizedMessage());
			System.exit(1);
		}
		try {
			adminAction.removeClient(tempClient);
			System.out.println("\n---Removed client with ID[" + clientId + "] successfuly---\n");
		} catch (MBankException e) {
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			System.exit(1);
		}
		System.out.println();
	}

	private static void handleAddNewClient() {
		String clientName = getValidStringInput("Enter client name: ");
		String clientPassword = getValidStringInput("Enter client password: ");
		String clientAddress = getValidStringInput("Enter client address: ");
		String clientEmail = getValidStringInput("Enter client email: ");
		String clientPhone = getValidStringInput("Enter client phone#: ");
		double deposit = getValidDoubleInput("Enter initial deposit amount: ");
		try 
		{
			long clientId = adminAction.addNewClient(clientName, clientPassword.toCharArray(), clientAddress, clientEmail, clientPhone, deposit);
			System.out.println("****\nAdded new client with ID: " + clientId + "\n****");
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			System.exit(1);
		}
		System.out.println();
	}

	private static void handleRemoveAccount() {
		System.out.println("Removal of accounts is performed as part of removal of client.\nYou cannot remove an account without removing a client completely\n");
	}

	private static void handleCreateNewAccount() {
		System.out.println("Creation of a new account is performed as part of adding a new client.\nYou cannot create an account without creating a new client\n");
		
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
			handleCreateNewDeposit();
			break;
		case DEPOSIT_TO_ACCOUNT:
			handleDepositToAccount();
			break;
		case PRE_OPEN_DEPOSIT:
			handlePreOpenDeposit();
			break;
		case UPDATE_CLIENT_DETAILS:
			handleUpdateClientDetails();
			break;
		case VIEW_ACCOUNT_DETAILS:
			handleViewAccountDetails();
			break;
		case VIEW_CLIENT_ACTIVITIES:
			handleViewClientActivities();
			break;
		case VIEW_CLIENT_DEPOSITS:
			handleViewClientDeposits();
			break;
		case VIEW_CLIENT_DETAILS:
			handleViewClientDetails();
			break;
		case VIEW_SYSTEM_PROPERTY:
			handleViewSystemProperty();
			break;
		case WITHDRAW_FROM_ACCOUNT:
			handleWithdrawFromAccount();
			break;
		case RETURN_TO_MAIN_MENU:
			mainMenu();
			break;
		default:
			break;
		}
		clientActionMenu();
	}
	private static void handleWithdrawFromAccount() {
		// TODO Auto-generated method stub
		
	}

	private static void handleViewSystemProperty() {
		// TODO Auto-generated method stub
		
	}

	private static void handleViewClientDetails() {
		System.out.println("---Displaying client details for the client associated with this ClientAction object---\n");
		Client clientDetails = null;
		try 
		{
			clientDetails = clientAction.viewClientDetails(clientAction.getClientId());
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
		}
		System.out.println(clientDetails.toString());
		System.out.println();
	}

	private static void handleViewClientDeposits() {
		System.out.println("---Displaying deposit details for the client associated with this ClientAction object---\n");
		List<Deposit> clientDeposits = null;
		try 
		{
			clientDeposits = clientAction.viewClientDeposits(clientAction.getClientId());
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
		}
		if(clientDeposits != null)
		{
			System.out.println(clientDeposits.toArray().toString());	
		}
		else
		{
			System.out.println("No deposits found");
		}
		System.out.println();
	}

	private static void handleViewClientActivities() {
		// TODO Auto-generated method stub
		
	}

	private static void handleViewAccountDetails() {
		// TODO Auto-generated method stub
		
	}

	private static void handleUpdateClientDetails() {
		// TODO Auto-generated method stub
		
	}

	private static void handlePreOpenDeposit() {
		// TODO Auto-generated method stub
		
	}

	private static void handleDepositToAccount() {
		// TODO Auto-generated method stub
		
	}

	private static void handleCreateNewDeposit() {
		// TODO Auto-generated method stub
		
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
	

	private static String getValidStringInput(String message) 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		
		do
		{
			System.out.println(message);	 
		      /* Read the String input entered by the user in the command-line;
		       * need to use try/catch with the readLine() method */
		      try {
		         input = br.readLine();
		      } catch (IOException ioe) 
		      {
		         System.out.println("IO error occured. Program will exit.");
		         System.exit(1);
		      }
		    if(input != null)
		    {
		    	return input.trim();	
		    }
		    System.out.println(UNRECOGNIZED_COMMAND);
		}
		while(input == null);
		
		return input.trim(); 	
	}
	
	/**
	 * 
	 * @param message 
	 * @return numeric value of string input (double)
	 */
	private static double getValidDoubleInput(String message) 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		double num = 0;
		do{
			System.out.println(message);
			/* Read the numeric input entered by the user in the command-line;
		       * need to use try/catch with the readLine() method */
		      try {
		         input = br.readLine();
		      } catch (IOException ioe) 
		      {
		         System.out.println("IO error occured. Program will exit.");
		         System.exit(1);
		      }
		     
		      try 
		      {
				num = Double.parseDouble(input.trim());
		      } 
		      catch (NumberFormatException e) 
		      {
		    	  System.out.println(UNRECOGNIZED_COMMAND + "Expected a number\n");	  
		      }	 
		} while (num == 0);
		
		return num;
	}
	
	private static long getValidLongInput(String message) 
	{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		long num = 0;
		do{
			System.out.println(message);
			/* Read the numeric input entered by the user in the command-line;
		       * need to use try/catch with the readLine() method */
		      try {
		         input = br.readLine();
		      } catch (IOException ioe) 
		      {
		         System.out.println("IO error occured. Program will exit.");
		         System.exit(1);
		      }
		     
		      try 
		      {
				num = Long.parseLong(input.trim());
		      } 
		      catch (NumberFormatException e) 
		      {
		    	  System.out.println(UNRECOGNIZED_COMMAND + "Expected an integer\n");	  
		      }	 
		} while (num == 0);
		
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
		ADD_NEW_CLIENT("ADD_NEW_CLIENT"), 
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
