/**
 * 
 */
package mbank.database.managersImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mbank.MBank;
import mbank.database.beans.Property;
import mbank.database.managersInterface.PropertyManager;
import mbankExceptions.MBankException;

/**
 * @author Shlomit Argov
 *
 */
public class PropertyDBManager implements PropertyManager
{
	private static final String tableName = "Properties";

	public PropertyDBManager()
	{
	}

	@Override
	public void insert(Property property) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		try
		{
			String sql = "INSERT INTO " + tableName + " (prop_key, prop_value) VALUES (?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, property.getProp_key());
			ps.setString(2, property.getProp_value());
			ps.execute();
			if (!(ps.getUpdateCount() > 0))
			{
				throw new MBankException();
			}
		} catch (MBankException | SQLException e)
		{
			throw new MBankException("Failed to insert into " + tableName + " table");
		}
		MBank.getInstance().returnConnection(con);
	}

	@Override
	public void update(Property property) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		try{
			String sql = "UPDATE " + tableName + " SET ";
			sql += "prop_value = ? ";
			sql += "WHERE prop_key = ?";
					
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(2, property.getProp_key());
			ps.setString(1, property.getProp_value());
			ps.execute();
		} 
		catch (SQLException e)
		{
			throw new MBankException("Failed to update '" + tableName + "' table\n" + e.getLocalizedMessage());
		}
		MBank.getInstance().returnConnection(con);
	}


	@Override
	public void delete(Property property) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		String sql = "DELETE FROM " + tableName + " WHERE prop_key = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, property.getProp_key());
			ps.execute();
			if(!(ps.getUpdateCount() > 0))
			{
				throw new MBankException();
			}
		} 
		catch (MBankException | SQLException e)
		{
			throw new MBankException("Failed to delete property: "+ property.getProp_key() + " from the " + tableName + "table");
		}
		MBank.getInstance().returnConnection(con);
	}

	@Override
	public Property query(String propertyName) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE prop_key = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, propertyName);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs != null)
			{	
				if(rs.next()) //next() returns false if there are no more rows in the RS
				{	
					Property p = new Property(rs.getString(1), rs.getString(2));
					MBank.getInstance().returnConnection(con);
					return p;
				}
			}
		} catch (SQLException e)
		{
			System.err.println("Failed to query the "  + tableName + "table");
			e.printStackTrace();
		}
		MBank.getInstance().returnConnection(con);
		return null;
	}

	@Override
	public ArrayList<Property> queryAllProperties() throws MBankException {
		Connection con = MBank.getInstance().getConnection();
		try
		{
			String sql = "SELECT * FROM " + tableName;
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			ArrayList<Property> propertiesList= new ArrayList<>();
			if (rs != null)
			{	
				while (rs.next()) // next() returns false if there are no more rows
								// in the RS
				{
					Property p = new Property(rs.getString(1), rs.getString(2));
					propertiesList.add(p);
				}
				MBank.getInstance().returnConnection(con);
				return propertiesList;
			}			
		} catch (SQLException e)
		{
			System.err.println("Failed to query the " + tableName + " table");
			e.printStackTrace();
		}
		MBank.getInstance().returnConnection(con);
		return null;
	}
}