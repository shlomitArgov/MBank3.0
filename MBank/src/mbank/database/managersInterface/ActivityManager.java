/**
 * 
 */
package mbank.database.managersInterface;

import java.util.ArrayList;
import java.util.List;

import mbank.database.beans.Activity;
import mbank.database.beans.enums.ActivityType;
import mbank.exceptions.MBankException;

/**
 * @author Shlomit
 *
 */
public interface ActivityManager
{
//	long insert(Activity activity) throws MBankException;
//
//	void update(Activity activity) throws MBankException;
//
//	void delete(Activity activity) throws MBankException;
//
//	Activity query(ActivityType activityType,long clientId) throws MBankException;
//	
//	ArrayList<Activity> queryAllActivities(Connection con) throws MBankException;
//
//	List<Activity> queryByClientId(long clientId) throws MBankException;
	long insert(Activity activity) throws MBankException;

	void update(Activity activity) throws MBankException;

	void delete(Activity activity) throws MBankException;

	Activity query(ActivityType activityType,long clientId) throws MBankException;
	
	ArrayList<Activity> queryAllActivities() throws MBankException;

	List<Activity> queryByClientId(long clientId) throws MBankException;
}
