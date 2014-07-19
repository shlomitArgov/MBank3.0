/**
 * 
 */
package mbank.database.managersInterface;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import mbank.database.beans.Activity;
import mbank.database.beans.enums.ActivityType;
import mbankExceptions.MBankException;

/**
 * @author Shlomit
 *
 */
public interface ActivityManager
{
	long insert(Activity activity, Connection con) throws MBankException;

	boolean update(Activity activity, Connection con);

	boolean delete(Activity activity, Connection con);

	Activity query(ActivityType activityType,long clientId, Connection con);
	
	ArrayList<Activity> queryAllActivities(Connection con);

	List<Activity> queryByClientId(long clientId, Connection con);
}
