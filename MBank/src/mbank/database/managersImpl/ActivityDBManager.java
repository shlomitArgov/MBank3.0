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

import mbank.database.beans.Activity;
import mbank.database.beans.enums.ActivityType;
import mbank.database.managersInterface.ActivityManager;

/**
 * @author Shlomit Argov
 *
 */
public class ActivityDBManager implements ActivityManager
{
	private static final String tableName = "Activity";
	
	public ActivityDBManager()
	{
	}
	
	@Override
	public boolean insert(Activity activity, Connection con)
	{
		try
		{
			String sql = "INSERT INTO " + tableName
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, activity.getId());
			ps.setLong(2, activity.getClient_id());
			ps.setDouble(3, activity.getAmount());
			ps.setDate(4, new java.sql.Date(activity.getActivity_date().getTime()));
			ps.setDouble(5, activity.getCommission());
			ps.setInt(6, activity.getActivityType().getVal());
			ps.setString(7, activity.getDescription());
			ps.execute();
			if (ps.getUpdateCount() > 0)
			{
				return true;
			}
		} catch (SQLException e)
		{
			System.err.println("Failed to insert into Activity table");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Activity activity, Connection con)
	{
		try{
			String sql = "UPDATE " + tableName + " SET ";
			sql += "id = ?, ";
			sql += "client_id = ?, ";
			sql += "amount = ?, ";
			sql += "activity_date = ?, ";
			sql += "commission = ?, ";
			sql += "activity_type = ?, ";
			sql += "description = ? ";
			sql += "WHERE id = ?";
			
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setLong(1, activity.getId());
			ps.setLong(2, activity.getClient_id());
			ps.setDouble(3, activity.getAmount());
			ps.setDate(4, new java.sql.Date(activity.getActivity_date().getTime()));
			ps.setDouble(5, activity.getCommission());
			ps.setInt(6, activity.getActivityType().getVal());
			ps.setString(7, activity.getDescription());
			ps.setLong(8, activity.getId());
			ps.execute();
			if (ps.getUpdateCount() > 0)
			{
				return true;
			}
		} 
		catch (SQLException e)
		{
			System.err.println("Failed to update Activity table");
			e.printStackTrace();
		}
	return false;
	}

	@Override
	public boolean delete(Activity activity, Connection con)
	{
		String sql = "DELETE FROM " + tableName + " WHERE id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, activity.getId());
			ps.execute();
			if(ps.getUpdateCount() > 0)
			{
				return true;
			}
		} 
		catch (SQLException e)
		{
			System.err.println("Failed to delete activity with id: "+ activity.getId() + " from the Activity table");
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Activity query(ActivityType activityType, long clientId, Connection con)
	{
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE client_id = ? AND  activity_type = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, clientId);
			ps.setInt(2, activityType.getVal());
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs != null)
			{	
				if(rs.next()) //next() returns false if there are no more rows in the RS
				{	
					Activity a = new Activity(rs.getLong(1), rs.getLong(2), rs.getDouble(3), new java.util.Date(rs.getDate(4).getTime()), rs.getDouble(5), ActivityType.intToType(rs.getInt(6)), rs.getString(7));
					return a;
				}
			}
		} catch (SQLException e)
		{
			System.err.println("Failed to query the Activity table");
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see mbank.database.managersInterface.ActivityManager#queryAllActivities(java.sql.Connection)
	 */
	@Override
	public ArrayList<Activity> queryAllActivities(Connection con)
	{
		String sql = "SELECT * FROM " + tableName;
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			ArrayList<Activity> activitiesList = new ArrayList<>();
			while(rs.next())
			{
				long  id = rs.getLong(1);
				long client_id = rs.getLong(2);
				double amount = rs.getDouble(3);
				java.util.Date activity_date = new java.util.Date((rs.getDate(4)).getTime());
				double commission = rs.getDouble(5);
				ActivityType activityType = ActivityType.intToType(rs.getInt(6));
				String description = rs.getString(7);
				Activity activity = new Activity(id, client_id, amount, activity_date, commission, activityType, description);
				activitiesList.add(activity);
			}
			
			return activitiesList;
			
		} catch (SQLException e)
		{
			System.err.println("Failed to query the Activity table");
			e.printStackTrace();
		} 		
		return null;
	}

	@Override
	public List<Activity> queryByClientId(long clientId, Connection con) {
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE client_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, clientId);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			List<Activity> clientActivities = new ArrayList<>();
			if (rs != null)
			{	
				while(rs.next()) //next() returns false if there are no more rows in the RS
				{	
					Activity a = new Activity(rs.getLong(1), rs.getLong(2), rs.getDouble(3), new java.util.Date(rs.getDate(4).getTime()), rs.getDouble(5), ActivityType.intToType(rs.getInt(6)), rs.getString(7));
					clientActivities.add(a);
				}
				return clientActivities;
			}
		} catch (SQLException e)
		{
			System.err.println("Failed to query the Activity table");
			e.printStackTrace();
		}
		return null;
	}

}
