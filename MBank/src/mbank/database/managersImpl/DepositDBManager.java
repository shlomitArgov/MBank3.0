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
import mbank.database.beans.Deposit;
import mbank.database.beans.enums.DepositType;
import mbank.database.managersInterface.DepositManager;
import mbank.exceptions.MBankException;

/**
 * @author Shlomit Argov
 * 
 */
public class DepositDBManager implements DepositManager
{
	private static final String tableName = "Deposits";

	public DepositDBManager()
	{
	}

	@Override
	public long insert(Deposit deposit) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		long depositId = 0;
		try
		{
			String sql = "INSERT INTO " + tableName
					+ " (client_id, balance, deposit_type, estimated_balance, opening_date, closing_date) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setLong(1, deposit.getClient_id());
			ps.setDouble(2, deposit.getBalance());
			ps.setString(3, deposit.getType().getTypeStringValue());
			ps.setDouble(4, deposit.getEstimated_balance());
			ps.setDate(5,
					new java.sql.Date(deposit.getOpening_date().getTime()));
			ps.setDate(6,
					new java.sql.Date(deposit.getClosing_date().getTime()));
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next())
			{
				depositId = rs.getLong(1);
			}
		} catch (SQLException e)
		{
			throw new MBankException("Failed to insert into Deposits table");
		}
	MBank.getInstance().returnConnection(con);
	return depositId;
	}

	@Override
	public void update(Deposit deposit) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		try
		{
			String sql = "UPDATE " + tableName + " SET ";
			sql += "client_id = ?, ";
			sql += "balance = ?, ";
			sql += "deposit_type = ?, ";
			sql += "estimated_balance = ?, ";
			sql += "opening_date = ?, ";
			sql += "closing_date = ? ";
			sql += "WHERE deposit_id = ?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setLong(1, deposit.getClient_id());
			ps.setDouble(2, deposit.getBalance());
			ps.setString(3, deposit.getType().getTypeStringValue());
			ps.setDouble(4, deposit.getEstimated_balance());
			ps.setDate(5, new java.sql.Date(deposit.getOpening_date().getTime()));
			ps.setDate(6, new java.sql.Date(deposit.getClosing_date().getTime()));
			ps.setLong(7, deposit.getDeposit_id());
			ps.execute();

			if (!(ps.getUpdateCount() > 0))
			{
				throw new MBankException();
			}
		} catch (MBankException | SQLException e)
		{
			throw new MBankException("Failed to update Deposit table");
		}
		MBank.getInstance().returnConnection(con);
	}

	@Override
	public void delete(Deposit deposit) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		String sql = "DELETE FROM " + tableName + " WHERE deposit_id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, deposit.getDeposit_id());
			ps.execute();
			if (!(ps.getUpdateCount() > 0))
			{
				throw new MBankException();
			}
		} catch (MBankException | SQLException e)
		{
			throw new MBankException("Failed to delete deposit with id: "
					+ deposit.getDeposit_id() + " from the Activity table");
		}
		MBank.getInstance().returnConnection(con);
	}

	@Override
	public Deposit query(Deposit deposit) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE deposit_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, deposit.getDeposit_id());
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs != null)
			{
				Deposit d = null;
				if (rs.next()) // next() returns false if there are no more rows
								// in the RS
				{
					d = new Deposit(rs.getLong(1), rs.getLong(2),
							rs.getDouble(3), DepositType.getEnumFromString(rs
									.getString(4)), rs.getDouble(5),
							new java.util.Date(rs.getDate(6).getTime()),
							new java.util.Date(rs.getDate(7).getTime()));
				}
				MBank.getInstance().returnConnection(con);
				return d;
			}
			else
			{
				throw new MBankException();
			}
		} catch (SQLException | MBankException e)
		{
			throw new MBankException("Failed to query the " + tableName + " table");
		}
	}

	@Override
	public ArrayList<Deposit> queryDepositsByClient(long clientId) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		String sql = "SELECT * FROM " + tableName + " Where client_id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, clientId);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			ArrayList<Deposit> depositsList = new ArrayList<>();
			while(rs.next())
			{
				long  deposit_id = rs.getLong(1);
				long client_id = rs.getLong(2);
				double balance = rs.getDouble(3);
				DepositType type = DepositType.getEnumFromString(rs.getString(4));
				Double estimated_balance = rs.getDouble(5);
				java.util.Date opening_date = new java.util.Date(rs.getDate(6).getTime());
				java.util.Date closing_date = new java.util.Date(rs.getDate(7).getTime());
				Deposit d = new Deposit(deposit_id, client_id, balance, type, estimated_balance, opening_date, closing_date);
				depositsList.add(d);
			}
			if(!(depositsList.isEmpty()))
			{
				MBank.getInstance().returnConnection(con);
				return depositsList;	
			}
		} catch (SQLException | MBankException e)
		{
			throw new MBankException("Failed to query the " + tableName + " table");
		} 		
		MBank.getInstance().returnConnection(con);
		return null;
	}

	@Override
	public Deposit query(long depositId) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE deposit_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, depositId);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs != null)
			{
				if (rs.next()) // next() returns false if there are no more rows
								// in the RS
				{
					Deposit d = new Deposit(rs.getLong(1), rs.getLong(2),
							rs.getDouble(3), DepositType.getEnumFromString(rs
									.getString(4)), rs.getDouble(5),
							new java.util.Date(rs.getDate(6).getTime()),
							new java.util.Date(rs.getDate(7).getTime()));
					MBank.getInstance().returnConnection(con);
					return d;
				}
			}
		} catch (SQLException | MBankException e)
		{
			throw new MBankException("Failed to query the " + tableName + " table");
		}
		MBank.getInstance().returnConnection(con);
		return null;
	
	}

	@Override
	public List<Deposit> queryAllDeposits() throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		try
		{
			String sql = "SELECT * FROM " + tableName;
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			List<Deposit> depositsList = new ArrayList<>();
			if (rs != null)
			{
				while (rs.next()) // next() returns false if there are no more rows
								// in the RS
				{
					Deposit d = new Deposit(rs.getLong(1), rs.getLong(2),
							rs.getDouble(3), DepositType.getEnumFromString(rs
									.getString(4)), rs.getDouble(5),
							new java.util.Date(rs.getDate(6).getTime()),
							new java.util.Date(rs.getDate(7).getTime()));
					depositsList.add(d);
					
				}
			}
			MBank.getInstance().returnConnection(con);
			return depositsList;
		} catch (SQLException | MBankException e)
		{
			throw new MBankException("Failed to query the " + tableName + " table");
		}
	}
}
