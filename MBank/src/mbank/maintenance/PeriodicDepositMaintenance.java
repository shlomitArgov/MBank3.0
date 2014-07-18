/**
 * 
 */
package mbank.maintenance;

import java.sql.Connection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;

import mbank.MBank;
import mbank.database.beans.Account;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.managersImpl.AccountDBManager;
import mbank.database.managersImpl.ClientDBManager;
import mbank.database.managersImpl.DepositDBManager;
import mbank.database.managersInterface.AccountManager;
import mbank.database.managersInterface.ClientManager;
import mbank.database.managersInterface.DepositManager;
import mbankExceptions.MBankException;

/**
 * @author Shlomit Argov
 * 
 */
public class PeriodicDepositMaintenance {

	private static Connection con;
	private static Date date;
	private Timer timer;
	private static MBank bankInstance;
	public PeriodicDepositMaintenance(MBank instance) {
		bankInstance = instance;
	}

	public void launch()
	{
		//configure object to hold the daily time on which the maintenance thread will execute 
		Calendar calendar =	Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = new Date(calendar.getTimeInMillis());
		
		//schedule the maintenance task to run daily at the time configured in "date" with "period" milliseconds in between successive executions
		long period = 24*60*60*1000; //number of milliseconds in a day

		
		//instantiate timer object as daemon 
		timer = new Timer("PeriodicDepositMaintenanceThread", true);
		timer.schedule(new Task(), date, period);
	}

	private class Task extends TimerTask
	{
		@Override
		public void run() {
			execute();
		}	
	}
	
	private static void execute() {
		System.out.println("Performing timed maintenance task, curr date: " + new Date(System.currentTimeMillis()) + ", configured date: " + date);
		try {
			// get connection
			con = bankInstance.getConnection();
			//Get all deposits and attempt to close them using the maintenance action (the closeDeposit method checks if the deposit has expired)
			DepositManager depositManager = new DepositDBManager();
			List<Deposit> depositsList = depositManager.queryAllDeposits(con);
			if (depositsList != null) {
				Iterator<Deposit> it = depositsList.iterator();
				while (it.hasNext()) {
					Deposit deposit = it.next();
					closeDeposit(deposit);
				}
			}
			// return connection to the connection pool
			bankInstance.returnConnection(con);
		} catch (MBankException e) {
			try {
				throw new MBankException(
						"Failed to get connection to database\n"
								+ e.getStackTrace());
			} catch (MBankException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println("completed timed maintenance task");
	}

	/**
	 * @author Shlomit Argov 
	 * 	Removes expired deposits and transfers their
	 *  balance into the appropriate client accounts
	 */

	private static boolean closeDeposit(Deposit deposit) throws MBankException {
		DepositManager depositManager = new DepositDBManager();
		java.util.Date currDate = new java.util.Date(System.currentTimeMillis());
		// close deposit if close date has passed
		if (currDate.after(deposit.getClosing_date())) {
			ClientManager clientManager = new ClientDBManager();
			Client client = clientManager.query(deposit.getClient_id(), con);
			// update client account with deposit balance
			AccountManager accountManager = new AccountDBManager();
			Account account = accountManager.queryAccountByClient(
					client.getClient_id(), con);
			account.setBalance(account.getBalance()
					+ deposit.getEstimated_balance());
			accountManager.update(account, con);
			// remove deposit
			depositManager.delete(deposit, con);
		}
		return false;
	}
}
