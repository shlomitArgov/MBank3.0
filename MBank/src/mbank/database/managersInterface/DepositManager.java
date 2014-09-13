/**
 * 
 */
package mbank.database.managersInterface;

import java.util.ArrayList;
import java.util.List;

import mbank.database.beans.Deposit;
import mbank.exceptions.MBankException;

/**
 * @author Shlomit
 *
 */
public interface DepositManager
{
	long insert(Deposit deposit) throws MBankException;

	void update(Deposit deposit) throws MBankException;

	void delete(Deposit deposit) throws MBankException;

	Deposit query(Deposit deposit) throws MBankException;

	ArrayList<Deposit> queryDepositsByClient(long clientId) throws MBankException;

	Deposit query(long depositId) throws MBankException;

	List<Deposit> queryAllDeposits() throws MBankException;
}
