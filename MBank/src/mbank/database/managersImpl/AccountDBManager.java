/**
 * 
 */
package mbank.database.managersImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mbank.MBank;
import mbank.database.beans.Account;
import mbank.database.managersInterface.AccountManager;
import mbankExceptions.MBankException;

/**
 * @author Shlomit Argov
 * 
 */
public class AccountDBManager implements AccountManager
{
	private static final String tableName = "Accounts";

	public AccountDBManager()
	{
	}

	@Override
	public long insert(Account account) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		try
		{
			String sql = "INSERT INTO " + tableName
					+ " (client_id, balance, credit_limit, comment) VALUES ( ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, account.getClient_id());
			ps.setDouble(2, account.getBalance());
			ps.setDouble(3, account.getCredit_limit());
			ps.setString(4, account.getComment());
			ps.execute();
		} catch (SQLException e)
		{
			throw new MBankException("Database error:\n" + e.getLocalizedMessage());
		}
		String sql2 = "SELECT IDENTITY_VAL_LOCAL() FROM " + tableName;
		PreparedStatement ps2;
		long accountId = 0;
		try {
			ps2 = con.prepareStatement(sql2);
			ps2.execute();
			ResultSet rs = ps2.getResultSet();
			rs.next();
			accountId = rs.getLong(1);
		} catch (SQLException e) {
			throw new MBankException("Failed to retrieve new account ID");
		}
	MBank.getInstance().returnConnection(con);
	return accountId;
	}

	@Override
	public void update(Account account) throws MBankException
	{		
		Connection con = MBank.getInstance().getConnection();
		try{
			String sql = "UPDATE " + tableName + " SET ";
//			sql += "account_id = ?, ";
			sql += "client_id = ?, ";
			sql += "balance = ?, ";
			sql += "credit_limit = ?, ";
			sql += "comment = ? ";
			sql += "WHERE account_id = ?";
					
			PreparedStatement ps = con.prepareStatement(sql);
			
//			ps.setLong(1, account.getAccount_id());
			ps.setLong(1, account.getClient_id());
			ps.setDouble(2, account.getBalance());
			ps.setDouble(3, account.getCredit_limit());
			ps.setString(4, account.getComment());
			ps.setLong(5, account.getAccount_id());
			
			ps.execute();
			MBank.getInstance().returnConnection(con);
			if (!(ps.getUpdateCount() > 0))
			{
				throw new MBankException();
			}
		} 
		catch (MBankException | SQLException e)
		{
			throw new MBankException("Failed to update Accounts table");
		}
	}

	@Override
	public void delete(Account account) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		String sql = "DELETE FROM " + tableName + " WHERE account_id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, account.getAccount_id());
			ps.execute();
			MBank.getInstance().returnConnection(con);
			if(!(ps.getUpdateCount() > 0))
			{
				throw new MBankException();
			}
		} 
		catch (MBankException | SQLException e)
		{
			throw new MBankException("Failed to delete account with id: "+ account.getAccount_id() + " from the Accounts table");
		}
	}

	@Override
	public void delete(long accountId) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		String sql = "DELETE FROM " + tableName + " WHERE account_id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, accountId);
			ps.execute();
			MBank.getInstance().returnConnection(con);
		} 
		catch (SQLException e)
		{
			throw new MBankException("Failed to delete account with id: "+ accountId + e.getLocalizedMessage());
		}
	}

	@Override
	public Account query(Account account) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE account_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, account.getAccount_id());
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs != null)
			{	
				if(rs.next()) //next() returns false if there are no more rows in the RS
				{	
					Account a = new Account(rs.getLong(1), rs.getLong(2), rs.getDouble(3), rs.getDouble(4), rs.getString(5));
					MBank.getInstance().returnConnection(con);
					return a;
				}
			}
			else
			{
				throw new MBankException();
			}
		} catch (MBankException | SQLException e)
		{
			throw new MBankException("Failed to query the Accounts table");
		}
		MBank.getInstance().returnConnection(con);
		return null;
	}

	@Override
	public Account queryAccountByClient(long clientId) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		String sql = "SELECT * FROM " + tableName + " WHERE client_id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, clientId);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			ArrayList<Account> accountlist = new ArrayList<>();
			Account account = null;
			while(rs.next())
			{
				long account_id = rs.getLong(1);
				long client_id = rs.getLong(2);
				double balance = rs.getDouble(3);
				double credit_limit = rs.getDouble(4);
				String comment = rs.getString(5);
				account = new Account(account_id, client_id, balance, credit_limit, comment);
				accountlist.add(account);
			}
			if(!(accountlist.isEmpty()))
			{
				if(accountlist.size() > 1)
				{
					throw new MBankException("More than one account for client[" + clientId + "]");
				}
				MBank.getInstance().returnConnection(con);
				return account;	
			}
		} catch (SQLException e)
		{
			throw new MBankException("Failed to query the Accounts table");
		} 		
		MBank.getInstance().returnConnection(con);
		return null;
	}

	@Override
	public List<Account> queryAllAccounts() throws MBankException {
		Connection con = MBank.getInstance().getConnection();
		String sql = "SELECT * FROM " + tableName;
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			ArrayList<Account> accountlist = new ArrayList<>();
			Account account = null;
			while(rs.next())
			{
				long account_id = rs.getLong(1);
				long client_id = rs.getLong(2);
				double balance = rs.getDouble(3);
				double credit_limit = rs.getDouble(4);
				String comment = rs.getString(5);
				account = new Account(account_id, client_id, balance, credit_limit, comment);
				accountlist.add(account);
			}
			if(!(accountlist.isEmpty()))
			{
				MBank.getInstance().returnConnection(con);
				return accountlist;	
			}
		} catch (SQLException e)
		{
			throw new MBankException("Failed to query the Accounts table");
		} 		
		MBank.getInstance().returnConnection(con);
		return null;
	}
}
