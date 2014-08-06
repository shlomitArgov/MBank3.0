/**
 * 
 */
package mbank.actions;

import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

import mbank.database.beans.Account;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.enums.ActivityType;
import mbank.database.beans.enums.ClientAttributes;
import mbank.database.beans.enums.ClientType;
import mbank.database.beans.enums.DepositType;
import mbank.database.beans.enums.SystemProperties;
import mbank.database.managersImpl.AccountDBManager;
import mbank.database.managersImpl.ActivityDBManager;
import mbank.database.managersImpl.ClientDBManager;
import mbank.database.managersImpl.DepositDBManager;
import mbank.database.managersImpl.PropertyDBManager;
import mbank.database.managersInterface.AccountManager;
import mbank.database.managersInterface.ActivityManager;
import mbank.database.managersInterface.ClientManager;
import mbank.database.managersInterface.DepositManager;
import mbank.database.managersInterface.PropertyManager;
import mbankExceptions.MBankException;

/**
 * @author Shlomit Argov
 *
 */
public class ClientAction extends Action
{

	public ClientAction(Connection con, long clientId)
	{
		super(con, clientId);
	}

	@Override
	public List<Deposit> viewClientDeposits(long clientId) throws MBankException
	{
		if(this.getClientId() != clientId)
		{
			return null;
		}
		else
		{
			return queryClientDeposits(clientId);
		}
	}

	@Override
	public List<Activity> viewClientActivities(long clientId) throws MBankException
	{
		if(clientId != this.getClientId())
		{
			return null;
		}
		else
		{
			return queryClientActivities(clientId);
		}
	}

	@Override
	public boolean logout()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 
	 * @param client
	 * @param withdrawAmount
	 * @return true: if withdrawal executed successfully, false if withdrawal failed
	 * @throws MBankException
	 */
	public void withdrawFromAccount(Client client, double withdrawAmount) throws MBankException
	{
		//make sure withdrawal amount is positive
		if(withdrawAmount < 0)
		{
			throw new MBankException("Cannot withdraw negative amount");
		}
		PropertyManager propertyManager = new PropertyDBManager();
		double commissionRate = Double.parseDouble(propertyManager.query(SystemProperties.COMMISSION_RATE.getPropertyName(), this.getCon()).getProp_value());
		Double credit_limit;
		ClientType clientType = client.getType();
		switch(clientType)
		{
		case REGULAR:
			credit_limit = Double.parseDouble(propertyManager.query(SystemProperties.REGULAR_CREDIT_LIMIT.getPropertyName(), this.getCon()).getProp_value());
			break;
		case GOLD:
			credit_limit = Double.parseDouble(propertyManager.query(SystemProperties.GOLD_CREDIT_LIMIT.getPropertyName(), this.getCon()).getProp_value());
			break;
		case PLATINUM:
			credit_limit = Double.parseDouble(propertyManager.query(SystemProperties.PLATINUM_CREDIT_LIMIT.getPropertyName(), this.getCon()).getProp_value());
			break;
		default: credit_limit = 0.0;
		}
		AccountManager accountManager = new AccountDBManager();
		Account account = accountManager.queryAccountByClient(client.getClient_id(), this.getCon());
		double accountBalance = account.getBalance();
		//Make sure account balance after withdrawal will not exceed the account's credit limit
		if ((accountBalance - commissionRate - withdrawAmount) >= -credit_limit)
		{
			account.setBalance(accountBalance - commissionRate - withdrawAmount);
			accountManager.update(account, this.getCon());
			ActivityManager activityManager = new ActivityDBManager();
			Activity activity = new Activity(client.getClient_id(), accountBalance - commissionRate - withdrawAmount, new java.util.Date(System.currentTimeMillis()), commissionRate, ActivityType.WITHDRAW_FROM_ACCOUNT, "Withdrew " + withdrawAmount + "from accout[" + account.getAccount_id() + "]");
			//update activity table
			activityManager.insert(activity, this.getCon());
		}
		else
		{
			//account balance was not greater than credit limit - withdrawal could not be executed
			throw new MBankException("Ilegal Action - withdrawal exceeds client limit");	
		}
	}

	public void depositToAccount(Client client, double depositAmount) throws MBankException
	{
		//make sure withdrawal amount is positive
		if (depositAmount < 0)
		{
			throw new MBankException("Deposit amount must be non-negative");
		}
		//get the commission rate for this action
		PropertyManager propertyManager = new PropertyDBManager();
		double commissionRate = Double.parseDouble(propertyManager.query(SystemProperties.COMMISSION_RATE.getPropertyName(), this.getCon()).getProp_value());
		//get the account 
		AccountManager accountManager = new AccountDBManager();
		Account account = accountManager.queryAccountByClient(client.getClient_id(), this.getCon());
		//update the account with the new deposit including action commission charge
		account.setBalance(account.getBalance() + depositAmount - commissionRate);
		accountManager.update(account, this.getCon());
		
		/* Check if the client type has changed due to the deposit */
		ClientType prevType = client.getType();
		ClientType newType = getClientType(account.getBalance());
		if (!(newType.equals(prevType)))
		{
				AdminAction adminAction = new AdminAction(this.getCon(), 1);
				TableValue clientTypeUpdate = new TableValue(ClientAttributes.CLIENT_TYPE.getAttribute(), newType.getTypeStringValue());
				adminAction.updateClientDetails(String.valueOf(client.getClient_id()), clientTypeUpdate);
		}				
	}
	
	public Deposit createNewDeposit(Client client, DepositType depositType, double depositAmount, java.util.Date closeDate) throws  MBankException
	{
		
		long depositDurationInDays = (closeDate.getTime() - System.currentTimeMillis())/(60*60*24*1000);
		
		//make sure deposit amount and duration are positive
		if(depositAmount < 0)
		{
			throw new MBankException("Deposit amount must be non-negative");
		}
		else if(depositDurationInDays < 0 || closeDate.getTime() < System.currentTimeMillis())
		{
			throw new MBankException("Deposit duration must be non-negative");
		}
		//get interest rate according to clientType
		ClientType clientType = client.getType();
		double interestRate;
		PropertyManager propertyManager = new PropertyDBManager();
		switch(clientType)
		{
		case REGULAR:
			interestRate = Double.parseDouble(propertyManager.query(SystemProperties.REGULAR_DAILY_INTEREST.getPropertyName(), this.getCon()).getProp_value());
			break;
		case GOLD:
			interestRate = Double.parseDouble(propertyManager.query(SystemProperties.GOLD_DAILY_INTEREST.getPropertyName(), this.getCon()).getProp_value());
			break;
		case PLATINUM:
			interestRate = Double.parseDouble(propertyManager.query(SystemProperties.PLATINUM_DAILY_INTEREST.getPropertyName(), this.getCon()).getProp_value());
			break;
		default: 
			interestRate = 0.0;
		}
		
		Calendar maxCloseCal = Calendar.getInstance();
		maxCloseCal.add(Calendar.YEAR, 40);
		java.util.Date maxCloseDate = new java.util.Date(maxCloseCal.getTimeInMillis());		
		DepositManager depositManager = new DepositDBManager();
		ActivityManager activityManager = new ActivityDBManager();
		Deposit deposit = null;
		//validate deposit length according to deposit type
		if ((depositType.equals(DepositType.SHORT) && depositDurationInDays <= 365) || (depositType.equals(DepositType.LONG) && closeDate.before(maxCloseDate)))
		{
			//Calculate estimated balance on the expiration date of the deposit
			double estimatedBalance = Math.round(depositAmount * (1+ interestRate*depositDurationInDays));
			//get end date
			Calendar calender = Calendar.getInstance();
			//safe explicit cast to int because duration is validated to be smaller than 365/40 years which are both less than MAX_INT
			calender.add(Calendar.DAY_OF_YEAR, (int)depositDurationInDays);
			//create deposit 
			deposit = new Deposit(client.getClient_id(), depositAmount, depositType, estimatedBalance, new java.util.Date(System.currentTimeMillis()), new java.util.Date(calender.getTimeInMillis()));
			deposit.setDeposit_id(depositManager.insert(deposit, this.getCon()));
			//update activity table
			Activity activity = new Activity(client.getClient_id(), depositAmount, new java.util.Date(System.currentTimeMillis()), 0, ActivityType.CREATE_NEW_DEPOSIT, "Create new deposit of type: " + deposit.getType().getTypeStringValue() + " for client[" + client.getClient_id() + "]");
			activityManager.insert(activity, this.getCon());
		}
		else
		{
			throw new MBankException("deposit duration is invalid for deposit type: " + depositType.getTypeStringValue());
		}
		return deposit;
	}
	
	public void preOpenDeposit(long depositId) throws MBankException
	{
		DepositManager depositManager = new DepositDBManager();
		Deposit deposit = depositManager.query(depositId, this.getCon());
		
		if(deposit.getType().equals(DepositType.LONG)) //short deposits cannot be pre-opened
		{
			// get deposit client 
			ClientManager clientManager = new ClientDBManager();
			Client client = clientManager.query(deposit.getClient_id(),this.getCon());

			//calculate deposit balance
			int depositDurationInDays = (int) ((deposit.getOpening_date().getTime() - System.currentTimeMillis())/(1000*60*60*24));
			PropertyManager propertyManager = new PropertyDBManager();
			Double depositInterestRate;
			switch(client.getType())
			{
				case REGULAR:
					depositInterestRate = Double.parseDouble(propertyManager.query(SystemProperties.REGULAR_DAILY_INTEREST.getPropertyName(), this.getCon()).getProp_value()); 
					break;
				case GOLD:
					depositInterestRate = Double.parseDouble(propertyManager.query(SystemProperties.GOLD_DAILY_INTEREST.getPropertyName(), this.getCon()).getProp_value());
					break;
				case PLATINUM:
					depositInterestRate = Double.parseDouble(propertyManager.query(SystemProperties.PLATINUM_DAILY_INTEREST.getPropertyName(), this.getCon()).getProp_value());
					break;
				default:
					depositInterestRate = 0.0;
			}
			double currDepositBalance = deposit.getBalance()*(1 + depositInterestRate*depositDurationInDays);
			
			// update client account with deposit balance and pre-open fee
			Double preOpenFee = Double.parseDouble(propertyManager.query(SystemProperties.PRE_OPEN_FEE.getPropertyName(), this.getCon()).getProp_value());
			AccountManager accountManager = new AccountDBManager();
			Account account = accountManager.queryAccountByClient(client.getClient_id(), this.getCon());
			account.setBalance(account.getBalance()	+ currDepositBalance - preOpenFee);
			accountManager.update(account, this.getCon());
			// remove deposit
			depositManager.delete(deposit, this.getCon());
		}
		else
		{
			throw new MBankException("Only long-term deposits can be pre-opened");
		}
	}
	
	@Override
	public Client viewClientDetails(long clientId) throws MBankException
	{
		if(this.getClientId() != clientId)
		{
			return null;
		}
		else
		{
			return queryClientDetails(clientId);
		}
	}
	
	@Override
	public Account viewAccountDetails(Client client) throws MBankException
	{
		if(client.getClient_id() != this.getClientId())
		{
			return null;
		}
		else
		{
			return queryClientAccount(this.getClientId());
		}
	}
}