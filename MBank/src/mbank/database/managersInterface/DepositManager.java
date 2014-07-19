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

	boolean update(Deposit deposit, Connection con);

	boolean delete(Deposit deposit, Connection con);

	Deposit query(Deposit deposit, Connection con);

	ArrayList<Deposit> queryDepositsByClient(long clientId, Connection con);

	Deposit query(long depositId, Connection con);

	List<Deposit> queryAllDeposits(Connection con);

}
