/**
 * 
 */
package mbank.database.managersInterface;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import mbank.database.beans.Deposit;
import mbankExceptions.MBankException;

/**
 * @author Shlomit
 *
 */
public interface DepositManager
{
	long insert(Deposit deposit, Connection con) throws MBankException;

	void update(Deposit deposit, Connection con) throws MBankException;

	void delete(Deposit deposit, Connection con) throws MBankException;

	Deposit query(Deposit deposit, Connection con) throws MBankException;

	ArrayList<Deposit> queryDepositsByClient(long clientId, Connection con) throws MBankException;

	Deposit query(long depositId, Connection con) throws MBankException;

	List<Deposit> queryAllDeposits(Connection con) throws MBankException;

}
