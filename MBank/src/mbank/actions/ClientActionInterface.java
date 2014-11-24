/**
 * 
 */
package mbank.actions;

import java.util.ArrayList;
import java.util.List;

import mbank.database.beans.Account;
import mbank.database.beans.Activity;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.Property;
import mbank.exceptions.MBankException;

/**
 * @author Shlomit Argov
 *
 */
public interface ClientActionInterface 
{
	public void withdraw(double amount) throws MBankException;
	public void deposit(double amount) throws MBankException;
	public long getClientID();
	public void updateClientDetails(TableValue... details) throws MBankException;
	public Account viewAccountDetails() throws MBankException;
	public Client viewClientDetails() throws MBankException;
	public void preOpenDeposit(long depositId) throws MBankException;
	public Deposit createNewDeposit(double depositAmount, java.util.Date closeDate) throws MBankException;
	public boolean logout();
	public List<Activity> viewClientActivities() throws MBankException;
	public List<Deposit> viewClientDeposits() throws MBankException;
	public abstract ArrayList<Property> viewSystemProperties() throws MBankException;
	public String viewSystemProperty(String propertyName) throws MBankException;
}
