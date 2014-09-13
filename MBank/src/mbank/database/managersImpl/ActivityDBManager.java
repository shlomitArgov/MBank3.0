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
import mbank.database.beans.Activity;
import mbank.database.beans.enums.ActivityType;
import mbank.database.managersInterface.ActivityManager;
import mbankExceptions.MBankException;

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
	public long insert(Activity activity) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		try
		{
			String sql = "INSERT INTO " + tableName
					+ " (client_id, amount, activity_date, commission, ACTIVITY_TYPE, description) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setLong(1, activity.getId());
			ps.setLong(1, activity.getClient_id());
			ps.setDouble(2, activity.getAmount());
			ps.setDate(3, new java.sql.Date(activity.getActivity_date().getTime()));
			ps.setDouble(4, activity.getCommission());
			ps.setInt(5, activity.getActivityType().getVal());
			ps.setString(6, activity.getDescription());
			ps.execute();
		} catch (SQLException e)
		{
			throw new MBankException("Failed to into '" + tableName + "' table");
		}
		String sql2 = "SELECT IDENTITY_VAL_LOCAL() FROM " + tableName;
		PreparedStatement ps2;
		long activityId = 0;
		try {
			ps2 = con.prepareStatement(sql2);
			ps2.execute();
			ResultSet rs = ps2.getResultSet();
			rs.next();
			activityId = rs.getLong(1);
		} catch (SQLException e) {
			throw new MBankException("Failed to retrieve new activity ID");
		}
	MBank.getInstance().returnConnection(con);
	return activityId;
	}

	@Override
	public void update(Activity activity) throws MBankException
	{		
		Connection con = MBank.getInstance().getConnection();
		try{
			String sql = "UPDATE " + tableName + " SET ";
			sql += "client_id = ?, ";
			sql += "amount = ?, ";
			sql += "activity_date = ?, ";
			sql += "commission = ?, ";
			sql += "activity_type = ?, ";
			sql += "description = ? ";
			sql += "WHERE activity_id = ?";
			
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setLong(1, activity.getClient_id());
			ps.setDouble(2, activity.getAmount());
			ps.setDate(3, new java.sql.Date(activity.getActivity_date().getTime()));
			ps.setDouble(4, activity.getCommission());
			ps.setInt(5, activity.getActivityType().getVal());
			ps.setString(6, activity.getDescription());
			ps.setLong(7, activity.getId());
			ps.execute();
			MBank.getInstance().returnConnection(con);
			if (!(ps.getUpdateCount() > 0))
			{
				throw new MBankException();
			}
		} 
		catch (MBankException | SQLException e)
		{
			throw new MBankException("Failed to update Activity table");
		}
	}

	@Override
	public void delete(Activity activity) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
		String sql = "DELETE FROM " + tableName + " WHERE activity_id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, activity.getId());
			ps.execute();
			MBank.getInstance().returnConnection(con);
			if(!(ps.getUpdateCount() > 0))
			{
				throw new MBankException();
			}
		} 
		catch (MBankException | SQLException e)
		{
			throw new MBankException("Failed to delete activity with id: "+ activity.getId() + " from the Activity table");
		}
	}
	
	@Override
	public Activity query(ActivityType activityType, long clientId) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
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
				Activity a = null;
				if(rs.next()) //next() returns false if there are no more rows in the RS
				{	
					a = new Activity(rs.getLong(1), rs.getInt(2), rs.getDouble(3), new java.util.Date(rs.getDate(4).getTime()), rs.getDouble(5), ActivityType.intToType(rs.getInt(6)), rs.getString(7));
					
				}
				MBank.getInstance().returnConnection(con);
				return a;
			}
			else
			{
				throw new MBankException();
			}
		} catch (MBankException | SQLException e)
		{
			throw new MBankException("Failed to query the Activity table");
		}
	}

	/* (non-Javadoc)
	 * @see mbank.database.managersInterface.ActivityManager#queryAllActivities(java.sql.Connection)
	 */
	@Override
	public ArrayList<Activity> queryAllActivities() throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
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
			MBank.getInstance().returnConnection(con);
			return activitiesList;
			
		} catch (SQLException e)
		{
			throw new MBankException("Failed to query the Activity table");
		} 		
	}

	@Override
	public List<Activity> queryByClientId(long clientId) throws MBankException
	{
		Connection con = MBank.getInstance().getConnection();
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
				MBank.getInstance().returnConnection(con);
				return clientActivities;
			}
			else
			{
				throw new MBankException();
			}
		} catch (MBankException | SQLException e)
		{
			throw new MBankException("Failed to query the Activity table");
		}
	}

}
