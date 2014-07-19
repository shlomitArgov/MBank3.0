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

	boolean update(Account account, Connection con);

	boolean delete(Account account, Connection con);
	
	void delete(long accountID, Connection con) throws MBankException;

	Account query(Account account, Connection con);

	Account queryAccountByClient(long clientId, Connection con) throws MBankException;

	List<Account> queryAllAccounts(Connection con);
}
