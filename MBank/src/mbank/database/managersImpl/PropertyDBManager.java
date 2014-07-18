/**
 * 
 */
package mbank.database.managersImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mbank.database.beans.Property;
import mbank.database.managersInterface.PropertyManager;

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
	public boolean insert(Property property, Connection con)
	{
		try
		{
			String sql = "INSERT INTO " + tableName + " VALUES (?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, property.getProp_key());
			ps.setString(2, property.getProp_value());
			ps.execute();
			if (ps.getUpdateCount() > 0)
			{
				return true;
			}
		} catch (SQLException e)
		{
			System.err.println("Failed to insert into " + tableName + " table");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Property property, Connection con)
	{
		try{
			String sql = "UPDATE " + tableName + " SET ";
			sql += "prop_value = ? ";
			sql += "WHERE prop_key = ?";
					
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(2, property.getProp_key());
			ps.setString(1, property.getProp_value());
			ps.execute();
			if (ps.getUpdateCount() > 0)
			{
				return true;
			}
		} 
		catch (SQLException e)
		{
			System.err.println("Failed to update " + tableName + " table");
			e.printStackTrace();
		}
	 
	return false;
	
	}

	@Override
	public boolean delete(Property property, Connection con)
	{
		String sql = "DELETE FROM " + tableName + " WHERE prop_key = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, property.getProp_key());
			ps.execute();
			if(ps.getUpdateCount() > 0)
			{
				return true;
			}
		} 
		catch (SQLException e)
		{
			System.err.println("Failed to delete property: "+ property.getProp_key() + " from the " + tableName + "table");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Property query(String propertyName, Connection con)
	{
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
				{	//the first column is the property ID - not needed
					Property p = new Property(rs.getString(2), rs.getString(3));
					return p;
				}
			}
		} catch (SQLException e)
		{
			System.err.println("Failed to query the "  + tableName + "table");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Property> queryAllProperties(Connection con) {
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
				return propertiesList;
			}			
		} catch (SQLException e)
		{
			System.err.println("Failed to query the " + tableName + " table");
			e.printStackTrace();
		}
		return null;
	}
}