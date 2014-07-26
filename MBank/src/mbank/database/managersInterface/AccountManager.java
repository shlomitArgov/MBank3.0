/**
 * 
 */
package mbank.database.managersInterface;

import java.sql.Connection;
import java.util.List;

import mbank.database.beans.Account;
import mbankExceptions.MBankException;

/**
 * @author Shlomit
 * 
 */
public interface AccountManager
{
	long insert(Account account, Connection con) throws MBankException;

	void update(Account account, Connection con) throws MBankException;
 
	void delete(Account account, Connection con) throws MBankException;
	
	void delete(long accountID, Connection con) throws MBankException;

	Account query(Account account, Connection con) throws MBankException;

	Account queryAccountByClient(long clientId, Connection con) throws MBankException;

	List<Account> queryAllAccounts(Connection con) throws MBankException;
}
