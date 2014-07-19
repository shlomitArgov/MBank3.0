/**
 * 
 */
package mbank.database.managersImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mbank.database.beans.Client;
import mbank.database.beans.enums.ClientType;
import mbank.database.managersInterface.ClientManager;
import mbankExceptions.MBankException;

/**
 * @author Shlomit Argov
 * 
 */
public class ClientDBManager implements ClientManager
{
	private static final String tableName = "Clients";

	public ClientDBManager()
	{
	}

	@Override
	public void insert(Client client, Connection con) throws MBankException
	{
		try
		{
			String sql = "INSERT INTO " + tableName + " (client_name, password, client_type, address, email, phone, comment) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setLong(1, client.getClient_id());
			ps.setString(1, client.getClient_name());
			ps.setString(2, client.getPassword());
			ps.setString(3, client.getType().getTypeStringValue());
			ps.setString(4, client.getAddress());
			ps.setString(5, client.getEmail());
			ps.setString(6, client.getPhone());
			ps.setString(7, client.getComment());
			ps.execute();
			
		} catch (SQLException e)
		{
			throw new MBankException("Database error:\n" + e.getLocalizedMessage());
		}
	}

	@Override
	public boolean update(Client client, Connection con) throws MBankException
	 {
		try{
			String sql = "UPDATE " + tableName + " SET ";
			sql += "client_id = ?, ";
			sql += "client_name = ?, ";
			sql += "password = ?, ";
			sql += "type = ?, ";
			sql += "address = ?, ";
			sql += "email = ?, ";
			sql += "phone = ?, ";
			sql += "comment = ? ";
			sql += "WHERE client_id = ?";
					
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setLong(1, client.getClient_id());
			ps.setString(2, client.getClient_name());
			ps.setString(3, client.getPassword());
			ps.setString(4, client.getType().getTypeStringValue());
			ps.setString(5, client.getAddress());
			ps.setString(6, client.getEmail());
			ps.setString(7, client.getPhone());
			ps.setString(8, client.getComment());
			ps.setLong(9, client.getClient_id());
			ps.execute();
			if (ps.getUpdateCount() > 0)
			{
				return true;
			}
		} 
		catch (SQLException e)
		{
			throw new MBankException("Failed to update Clients table", e);
		}
	 
	return false;
	}

	@Override
	public boolean delete(Client client, Connection con)
	{
		String sql = "DELETE FROM " + tableName + " WHERE client_id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, client.getClient_id());
			ps.execute();
			if(ps.getUpdateCount() > 0)
			{
				return true;
			}
		} 
		catch (SQLException e)
		{
			System.err.println("Failed to delete client with id: "+ client.getClient_id() + " from the Clients table");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Client query(Client client, Connection con)
	{
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE client_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, client.getClient_id());
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs != null)
			{	
				if(rs.next()) //next() returns false if there are no more rows in the RS
				{	
					Client c = new Client(rs.getLong(1), rs.getString(2), rs.getString(3), ClientType.getEnumFromString(rs.getString(4)), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
					return c;
				}
			}
		} catch (SQLException | MBankException e)
		{
			System.err.println("Failed to query the Clients table");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Client> queryAllClients(Connection con)
	{
		String sql = "SELECT * FROM " + tableName;
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			ArrayList<Client> clientList = new ArrayList<>();
			while(rs.next())
			{
				long client_id = rs.getLong(1);
				String client_name = rs.getString(2);
				String password = rs.getString(3);
				String type = rs.getString(4); //convert to ClientType in client object
				String address = rs.getString(5);
				String email = rs.getString(6);
				String phone = rs.getString(7);
				String comment = rs.getString(8);
				Client client = new Client(client_id, client_name, password, ClientType.getEnumFromString(type), address, email, phone, comment);
				clientList.add(client);
			}
			
			return clientList;
			
		} catch (SQLException e)
		{
			System.err.println("Failed to query the Clients table");
			e.printStackTrace();
		} catch (MBankException e)
		{
			System.err.println("Unknown client type");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Client query(long client_id, Connection con) throws MBankException {
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE client_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, client_id);
			ps.execute();
			ResultSet rs = ps.getResultSet();	
				if(rs.next()) //next() returns false if there are no more rows in the RS, this query should return a single result or not result
				{	
					Client c = new Client(rs.getLong(1), rs.getString(2), rs.getString(3), ClientType.getEnumFromString(rs.getString(4)), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
					return c;
				}
				else
				{
					throw new MBankException("Could not find client with id[" + client_id + "]");
				}
			
		} catch (SQLException e)
		{
			throw new MBankException("Could not find client with id[" + client_id + "]");
		}
}

	/**
	 * @throws MBankException 
	 * 
	 */
	@Override
	public Client query(String username, Connection con) throws MBankException {
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE client_name = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs != null)
			{	
				if(rs.next()) //next() returns false if there are no more rows in the RS
				{	
					Client c = new Client(rs.getLong(1), rs.getString(2), rs.getString(3), ClientType.getEnumFromString(rs.getString(4)), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
					return c;
				}
			}
		} catch (SQLException | MBankException e)
		{
			System.err.println("Failed to query the Clients table");
			e.printStackTrace();
			throw new MBankException("Failed to retrieve client information from the database");
			
		}
		return null;
	}
}
