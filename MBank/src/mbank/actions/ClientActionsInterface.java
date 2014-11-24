/**
 * 
 */
package mbank.actions;

import mbank.exceptions.MBankException;

/**
 * @author Shlomit Argov
 *
 */
public interface ClientActionsInterface 
{
	public void withdraw(double amount) throws MBankException;
	public void deposit(double amount) throws MBankException;
	public long getClientID();
}
