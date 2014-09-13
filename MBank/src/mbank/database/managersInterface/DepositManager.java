/**
 * 
 */
package mbank.database.managersInterface;

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
//	long insert(Deposit deposit) throws MBankException;
//
//	void update(Deposit deposit) throws MBankException;
//
//	void delete(Deposit deposit) throws MBankException;
//
//	Deposit query(Deposit deposit) throws MBankException;
//
//	ArrayList<Deposit> queryDepositsByClient(long clientId) throws MBankException;
//
//	Deposit query(long depositId) throws MBankException;
//
//	List<Deposit> queryAllDeposits(Connection con) throws MBankException;
	long insert(Deposit deposit) throws MBankException;

	void update(Deposit deposit) throws MBankException;

	void delete(Deposit deposit) throws MBankException;

	Deposit query(Deposit deposit) throws MBankException;

	ArrayList<Deposit> queryDepositsByClient(long clientId) throws MBankException;

	Deposit query(long depositId) throws MBankException;

	List<Deposit> queryAllDeposits() throws MBankException;
}
