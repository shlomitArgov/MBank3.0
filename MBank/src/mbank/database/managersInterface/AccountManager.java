/**
 * 
 */
package mbank.database.managersInterface;

import java.util.List;

import mbank.database.beans.Account;
import mbankExceptions.MBankException;

/**
 * @author Shlomit
 * 
 */
public interface AccountManager
{
//	long insert(Account account) throws MBankException;
//
//	void update(Account account) throws MBankException;
// 
//	void delete(Account account) throws MBankException;
//	
//	void delete(long accountID) throws MBankException;
//
//	Account query(Account account) throws MBankException;
//
//	Account queryAccountByClient(long clientId) throws MBankException;
//
//	List<Account> queryAllAccounts(Connection con) throws MBankException
	long insert(Account account) throws MBankException;

	void update(Account account) throws MBankException;
 
	void delete(Account account) throws MBankException;
	
	void delete(long accountID) throws MBankException;

	Account query(Account account) throws MBankException;

	Account queryAccountByClient(long clientId) throws MBankException;

	List<Account> queryAllAccounts() throws MBankException;
}
