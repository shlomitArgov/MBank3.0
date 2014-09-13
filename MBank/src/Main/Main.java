package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mbank.actions.AdminAction;
import mbank.actions.ClientAction;
import mbank.actions.TableValue;
import mbank.database.beans.Account;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.Property;
import mbank.database.beans.enums.ClientAttributes;
import mbank.database.beans.enums.DepositType;
import mbank.database.beans.enums.SystemProperties;
import mbank.database.managersImpl.AccountDBManager;
import mbank.database.managersImpl.ClientDBManager;
import mbank.database.managersImpl.PropertyDBManager;
import mbank.exceptions.MBankException;

public class Main {
	private static final String AN_ERROR_OCCURED = "An error occured: ";
	private static final String UNRECOGNIZED_COMMAND = "\n---Unrecognized command---\n";
	private static final String MAIN_MENU_DIALOG = "\nEnter an option number:\n1. Test AdminAction methods\n2. Test ClientAction methods\n3. exit";
	private static final String ADMIN_MENU_INSTRUCTION = "\n---AdminAction methods menu---\nEnter an option number: ";
	private static final String CLIENT_MENU_INSTRUCTION = "\n---ClientAction methods menu---\nEnter an option number: ";

	private Client client;
	
	private AdminAction adminAction;
	private ClientAction clientAction;
	
	//Project entry point
	private ClientDBManager clientManager;

	public static void main(String[] args) throws MBankException {
//		MBank bank = MBank.getInstance();
		Main main = new Main();
		
		main.setAdminAction(new AdminAction(1));
		long clientId = main.getAdminAction().addNewClient("CLI Client" + System.currentTimeMillis(), new char[]{'p','w','d'}, "home", "mail@home.com", "555-555555", 1000000);
		main.setClientAction(new ClientAction(clientId));
		main.setClientManager(new ClientDBManager());
		try 
		{
			main.setClient(main.getClientManager().query(main.getClientAction().getClientId()));
		} catch (MBankException e) {
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			System.exit(1);
		}		
		System.out.println("***Welcome to MBank***\n");

		main.mainMenu();
	}

	private void mainMenu() 
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

	private int getMainMenuActionChoice() 
	{
		System.out.println(MAIN_MENU_DIALOG);		
		int input = getIntegerInput();
		while((input < 1) || (input > 3))
		{
			System.out.println(UNRECOGNIZED_COMMAND);
			System.out.println(MAIN_MENU_DIALOG);
			input = getIntegerInput();
		}
	return input;		
	}
	
	private void adminActionMenu() {
		AdminActionMethods method = getAdminMenuChoice();  
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
		case UPDATE_SYSTEM_PROPERTY:
			handleUpdateSystemProperty();
			break;
		case RETURN_TO_MAIN_MENU:
			mainMenu();
			break;
		default:
			break;
		}
		adminActionMenu();
	}

	private void handleUpdateSystemProperty() {
		System.out.println("\n---System Properties---\n");
		printEnumValuesAsMenuOptions(SystemProperties.class);
		System.out.println("\nEnter the number of the system property you wish to update");
		int userChoice = getIntegerInput();
		SystemProperties selectedProperty = getEnumChoice(userChoice, SystemProperties.class);
		PropertyDBManager propertyManager = new PropertyDBManager();
		String selectedPropertyValue = null;
		try 
		{
			selectedPropertyValue = propertyManager.query(selectedProperty.getPropertyName()).getProp_value();
		} catch (MBankException e2) 
		{
			System.out.println(AN_ERROR_OCCURED + e2.getLocalizedMessage());
			adminActionMenu();
		}
		if (selectedPropertyValue != null)
			
		{
			System.out.println("Current property value for " + selectedProperty.getPropertyName() + ": " + selectedPropertyValue);	
		}
		else
		{
			System.out.println(AN_ERROR_OCCURED + "Failed to retrieve current property value");
		}
		String newValue = getValidStringInput("Enter new property value");
		Property property = null;
		try 
		{
			property = new Property(selectedProperty.getPropertyName(), newValue);
		} catch (MBankException e1) 
		{
			System.out.println(AN_ERROR_OCCURED + e1.getLocalizedMessage());
			adminActionMenu();
		}
		
		try 
		{
			if(property != null)
			{
				this.adminAction.updateSystemProperty(property);	
			}
			else
			{
				throw new MBankException("Failed to retrieve property");
			}
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			adminActionMenu();
		}
		System.out.println("\n---Property updated successfully---\n");
		
	}

	private void handleViewAllClientsDetails() {
		System.out.println("---Displaying all client details---");
		try 
		{
			List<Client> clients = this.adminAction.ViewAllClientDetails();
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
			adminActionMenu();
		}
		System.out.println();
	}

	private void handleViewAllActivitiesDetails() {
		System.out.println("---Displaying all activities---");
		try 
		{
			List<Activity> activities = this.adminAction.viewAllActivitiesDetails();
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
			adminActionMenu();
		}	
		System.out.println();
	}

	private void handleViewAllAccountsDetails() {
		System.out.println("---Displaying all accounts---");
		try 
		{
			List<Account> accounts = this.adminAction.viewAllAccountsDetails();
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
			adminActionMenu();
		}	
		System.out.println();		
	}

	private void handleUpdateclientDetails() {
		String clientId = getValidStringInput("Enter the ID of the client whose details you wish to edit: ");
		String columnName = selectClientAttributeToEdit();
		String columnValue = getValidStringInput("Enter new value for the chosen client detail: ");
		TableValue details = new TableValue(columnName, columnValue);
		try {
			this.adminAction.updateClientDetails(Integer.parseInt(clientId), details);
			System.out.println("\n---Updated client [id = " + clientId + "] details successfully---\n");
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			adminActionMenu();
		}	
		System.out.println();		
	}

	private String selectClientAttributeToEdit() {
		int attributeNum = 0;
		ClientAttributes[] clientAttributes;
		do
		{
			System.out.println("Enter the number of the attribute you wish to edit: ");
			clientAttributes = ClientAttributes.values();
			for (int i = 0; i < clientAttributes.length; i++) {
				System.out.println((i+1) + ". " + clientAttributes[i].getAttribute());
			}
			attributeNum = getIntegerInput();
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

	private void handleViewAllDepositsDetails() {
		System.out.println("---Displaying all deposits---");
		try 
		{
			List<Deposit> deposits = this.adminAction.viewAllDepositsDetails();
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
			adminActionMenu();
		}	
		System.out.println();		
	}

	private void handleRemoveClient() {
		long clientId = getValidLongInput("Enter the ID of the client you wish to remove");
		/* Get the client bean from the DB */
		ClientDBManager clientManager = new ClientDBManager();
		Client tempClient = null;
		try {
			tempClient = clientManager.query(clientId);
		} catch (MBankException e) 
		{
			System.out.println("Failed to retrieve client \n" + e.getLocalizedMessage());
			adminActionMenu();
		}
		try {
			this.adminAction.removeClient(tempClient);
			System.out.println("\n---Removed client with ID[" + clientId + "] successfuly---\n");
		} catch (MBankException e) {
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			adminActionMenu();
		}
		System.out.println();
	}

	private void handleAddNewClient() {
		String clientName = getValidStringInput("Enter client name: ");
		String clientPassword = getValidStringInput("Enter client password: ");
		String clientAddress = getValidStringInput("Enter client address: ");
		String clientEmail = getValidStringInput("Enter client email: ");
		String clientPhone = getValidStringInput("Enter client phone#: ");
		double deposit = getValidDoubleInput("Enter initial deposit amount: ");
		try 
		{
			long clientId = this.adminAction.addNewClient(clientName, clientPassword.toCharArray(), clientAddress, clientEmail, clientPhone, deposit);
			System.out.println("****\nAdded new client with ID: " + clientId + "\n****");
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			adminActionMenu();
		}
		System.out.println();
	}

	private void handleRemoveAccount() {
		System.out.println("Removal of accounts is performed as part of removal of client.\nYou cannot remove an account without removing a client completely\n");
	}

	private void handleCreateNewAccount() {
		System.out.println("Creation of a new account is performed as part of adding a new client.\nYou cannot create an account without creating a new client\n");
		
	}

	private AdminActionMethods getAdminMenuChoice() {
		System.out.println(ADMIN_MENU_INSTRUCTION);	
		printEnumValuesAsMenuOptions(AdminActionMethods.class);
		int userChoice = getIntegerInput();
		AdminActionMethods adminMethod = getEnumChoice(userChoice, AdminActionMethods.class);
		while (adminMethod == null)
		{
			System.out.println(UNRECOGNIZED_COMMAND);
			System.out.println(ADMIN_MENU_INSTRUCTION);
			printEnumValuesAsMenuOptions(AdminActionMethods.class);
			userChoice = getIntegerInput();
			adminMethod = getEnumChoice(userChoice, AdminActionMethods.class);
		}
	return adminMethod;	
	}

	private void clientActionMenu() {
		
		ClientActionMethods method = getClientMenuChoice();  

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
	private void handleWithdrawFromAccount() {
		AccountDBManager accountManager = new AccountDBManager();
		Account account = null;
		try 
		{
			account = accountManager.queryAccountByClient(this.clientAction.getClientId());
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			clientActionMenu();
		}
		double withdrawAmount = 0;
		if(account != null)
		{
			System.out.println("Current account balance: " + account.getBalance()+ "\nAccount limit is: " + account.getCredit_limit());
			withdrawAmount = getValidDoubleInput("\nEnter the amount to withdraw: \n");
			try 
			{
				this.clientAction.withdrawFromAccount(withdrawAmount);
			} catch (MBankException e) 
			{
				System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
				clientActionMenu();
			}
			System.out.println("\n---Withdrawal completed successfuly---\n");
		}
	}

	private void handleViewSystemProperty() 
	{
		System.out.println("\nSelect a property to display\n");
		printEnumValuesAsMenuOptions(SystemProperties.class);
		
		int userChoice = getIntegerInput();
			
		String propertyName = getEnumChoice(userChoice, SystemProperties.class).getPropertyName();
		String propertyValue = null;
		try 
		{
			if (propertyName == null)
			{
				throw new MBankException();
			}
			propertyValue = this.clientAction.viewSystemProperty(propertyName);
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			clientActionMenu();
		}
		if (propertyValue != null)
		{
			System.out.println(propertyName + " = " + propertyValue);
			System.out.println();
		}
	}

	private <T extends Enum<T>> T getEnumChoice(int userChoice, Class<T> enumType) {
		T[] values = enumType.getEnumConstants();
		if (userChoice > values.length || userChoice  < 1)
		{
			return null;
		}
		return values[userChoice - 1];
	}

	private void handleViewClientDetails() {
		System.out.println("---Displaying client details for the client associated with this ClientAction object---\n");
		Client clientDetails = null;
		try 
		{
			clientDetails = this.clientAction.viewClientDetails();
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
		}
		System.out.println(clientDetails.toString());
		System.out.println();
	}

	private void handleViewClientDeposits() {
		System.out.println("---Displaying deposit details for the client associated with this ClientAction object---\n");
		List<Deposit> clientDeposits = null;
		try 
		{
			clientDeposits = this.clientAction.viewClientDeposits();
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
		}
		if(clientDeposits != null)
		{
			System.out.println(Arrays.toString(clientDeposits.toArray()));	
		}
		else
		{
			System.out.println("No deposits found");
		}
		System.out.println();
	}

	private void handleViewClientActivities() {
		System.out.println("---Displaying activities for the client associated with this ClientAction object---\n");
		List<Activity> clientActivities = null;
		try 
		{
			clientActivities = this.clientAction.viewClientActivities();
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
		}
		if(clientActivities != null)
		{
			System.out.println(Arrays.toString(clientActivities.toArray()));
		}
		else
		{
			System.out.println("No activities found");
		}
		System.out.println();
	}

	private void handleViewAccountDetails() {
		System.out.println("---Displaying account details for the client associated with this ClientAction object---\n");
		Account clientAccount = null;
		try 
		{
			clientAccount = this.clientAction.viewAccountDetails();
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
		}
		if(clientAccount != null)
		{
			System.out.println(clientAccount.toString());
		}
		else
		{
			System.out.println("No account found");
		}
		System.out.println();
	}

	private void handleUpdateClientDetails() {
		/* Display only the attribute a ClientAction object can update */
		ClientAttributes[] availableAttributes = new ClientAttributes[]{ClientAttributes.ADDRESS, ClientAttributes.EMAIL, ClientAttributes.PHONE};
		ClientAttributes attribute = null;
		int userChoice = 0;
		do
		{
			System.out.println("Select the client detail you wish to update: ");
			for (int i = 0; i < availableAttributes.length; i++) {
				System.out.println((i + 1 + ". " + availableAttributes[i].getAttribute()));
			}
			userChoice = getIntegerInput();
			if (userChoice > availableAttributes.length || userChoice  < 1)
			{
				System.out.println(UNRECOGNIZED_COMMAND);
			}
		} while (userChoice > availableAttributes.length || userChoice  < 1);
		
		attribute =  availableAttributes[userChoice - 1];
		
		String updatedDetail = getValidStringInput("Enter the new value: ");
		TableValue tableValue = new TableValue(attribute.getAttribute(), updatedDetail);
		try 
		{
			this.clientAction.updateClientDetails(tableValue);
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
			clientActionMenu();
		}
	}

	private void handlePreOpenDeposit() {
		System.out.println("Enter the ID of the deposit you would like to preopen from the list below (only long-term deposits shown): \n");
		
		List<Deposit> clientDeposits = null;
		try 
		{
			clientDeposits = this.clientAction.viewClientDeposits();
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
		}
		if(clientDeposits != null)
		{
			boolean noLongTermDeposits = true;
			ArrayList<Long> longDepositIds = new ArrayList<>();
			for (int i = 0; i < clientDeposits.size(); i++) {
				if (clientDeposits.get(i).getType().equals(DepositType.LONG))
				{
					noLongTermDeposits = false;
					longDepositIds.add(clientDeposits.get(i).getDeposit_id());
					System.out.println(clientDeposits.get(i).toString());		
				}
			}
			if (noLongTermDeposits)
			{
				System.out.println("No long-term deposits found");
			}
			else
			{
				Long userChoice = getLongInput();
				if(longDepositIds.contains(userChoice))
				{
					try 
					{
						this.clientAction.preOpenDeposit(userChoice);
						System.out.println("\n---Deposit [" +  userChoice + "] pre-opened successfully---\n");
					} catch (MBankException e) 
					{
						System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
						clientActionMenu();
					}	
				}
				else
				{
					System.out.println("Invalid deposit ID");
					clientActionMenu();
				}
			}
		}
		else
		{
			System.out.println("No deposits found");
		}
		System.out.println();
	}


	private void handleDepositToAccount() {
		double depositAmount = getValidDoubleInput("Enter amount to deposit: ");
		try 
		{
			this.clientAction.depositToAccount(depositAmount);
			System.out.println("\n---Deposit executed successfuly---\n");
		} catch (MBankException e) 
		{
			System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
		}
	}

	private void handleCreateNewDeposit() {
		System.out.println("Choose deposit type: ");
		printEnumValuesAsMenuOptions(DepositType.class);
		int userChoice = getIntegerInput();
		DepositType depositType = getEnumChoice(userChoice, DepositType.class); 
//				getDepositTypeFromUser("Choose deposit type: ");
		if(depositType != null)
		{
			double depositAmount = getValidDoubleInput("Enter deposit amount");
			System.out.println("Enter length of deposit in days");
			int depositLengthInDays = getIntegerInput();
			Deposit newDeposit = null;
			try
			{
				newDeposit = this.clientAction.createNewDeposit(depositType, depositAmount, new Date(System.currentTimeMillis() + depositLengthInDays));	
			}
			catch (MBankException e)
			{
				System.out.println(AN_ERROR_OCCURED + e.getLocalizedMessage());
				clientActionMenu();
			}
			if(newDeposit != null)
			{
				System.out.println("\n---New deposit created successfuly with ID [" + newDeposit.getDeposit_id() + "]---\n");
			}
		}
	}

	private ClientActionMethods getClientMenuChoice() {
		System.out.println(CLIENT_MENU_INSTRUCTION);
		printEnumValuesAsMenuOptions(ClientActionMethods.class);
		int userChoice = getIntegerInput();
		ClientActionMethods clientMethod = getEnumChoice(userChoice, ClientActionMethods.class);
		while(clientMethod == null)
		{
			System.out.println(UNRECOGNIZED_COMMAND);
			System.out.println(CLIENT_MENU_INSTRUCTION);
			printEnumValuesAsMenuOptions(ClientActionMethods.class);
			userChoice = getIntegerInput();
			clientMethod = getEnumChoice(userChoice, ClientActionMethods.class);
		}
	return clientMethod;
	}

	private <T extends Enum<T>> void printEnumValuesAsMenuOptions(Class<T> enumType)
	{
		T[] values = enumType.getEnumConstants();
		for (int i = 0 ; i < values.length; i++)
		{
			System.out.println((i+1) + ". " + values[i].name());
		}
	}

	/**
	 * 
	 * @return numeric input in case of valid numeric input, -1 otherwise
	 */
	private int getIntegerInput() 
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
	

	private Long getLongInput() {
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
	      
	      long num = 0;
	      try 
	      {
			num = Long.parseLong(input.trim());
	      } 
	      catch (NumberFormatException e) 
	      {
	    	  return new Long (-1);
	      }
		return new Long(num); 
	}	

	private String getValidStringInput(String message) 
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
	private double getValidDoubleInput(String message) 
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
	
	private long getValidLongInput(String message) 
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
		
		//used in utility methods that handle enums
		@SuppressWarnings("unused")
		private String name;
		ClientActionMethods(String name)
		{
			this.name = name;
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
		UPDATE_SYSTEM_PROPERTY("UPDATE_SYSTEM_PROPERTY"),
		RETURN_TO_MAIN_MENU("RETURN_TO_MAIN_MENU")
		;// can be tested via the SWING UI
		//used in utility methods that handle enums
		@SuppressWarnings("unused")
		private String name;
		
		AdminActionMethods(String name)
		{
			this.name = name;
		}	
	}
	
	public ClientDBManager getClientManager() {
		return this.clientManager;
	}

	public void setClientManager(ClientDBManager clientManager) {
		this.clientManager = clientManager;
	}
	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public AdminAction getAdminAction() {
		return this.adminAction;
	}

	public void setAdminAction(AdminAction adminAction) {
		this.adminAction = adminAction;
	}

	public ClientAction getClientAction() {
		return this.clientAction;
	}

	public void setClientAction(ClientAction clientAction) {
		this.clientAction = clientAction;
	}
}
