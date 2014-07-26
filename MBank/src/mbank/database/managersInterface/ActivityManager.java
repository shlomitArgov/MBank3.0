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

	void update(Activity activity, Connection con) throws MBankException;

	void delete(Activity activity, Connection con) throws MBankException;

	Activity query(ActivityType activityType,long clientId, Connection con) throws MBankException;
	
	ArrayList<Activity> queryAllActivities(Connection con) throws MBankException;

	List<Activity> queryByClientId(long clientId, Connection con) throws MBankException;
}
