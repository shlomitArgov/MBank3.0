/**
 * 
 */
package mbank.database.beans.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Shlomit Argov
 *
 */
public enum SystemProperties
{
	REGULAR_DEPOSIT_RATE("regular_deposit_rate"),		
	GOLD_DEPOSIT_RATE("gold_deposit_rate"),		
	PLATINUM_DEPOSIT_RATE("platinum_deposit_rat"),		
	REGULAR_DEPOSIT_COMMISSION("regular_deposit_commission"),		
	GOLD_DEPOSIT_COMMISSION("gold_deposit_commission"),		
	PLATINUM_DEPOSIT_COMMISSION("platinum_deposit_commission"),		
	REGULAR_CREDIT_LIMIT("regular_credit_limit"),		
	GOLD_CREDIT_LIMIT("gold_credit_limit"),
	PLATINUM_CREDIT_LIMIT("platinum_credit_limit"),		
	COMMISSION_RATE("commission_rate"),
	REGULAR_DAILY_INTEREST("regular_daily_interest"),		
	GOLD_DAILY_INTEREST("gold_daily_interest"),		
	PLATINUM_DAILY_INTEREST("platinum_daily_interest"),		
	PRE_OPEN_FEE("pre_open_fee"), 		
	ADMIN_USERNAME("admin_username"), 		
	ADMIN_PASSWORD("admin_password");	
	
	private SystemProperties(String propertyName)
	{
		this.propertyName = propertyName;
	}

	public String getPropertyName()
	{
		return propertyName;
	}

	private String propertyName;

	public static boolean validateString(String propertyName2) {
		List<SystemProperties> properties = Arrays.asList(SystemProperties.values());
		List<String> stringProperties = new ArrayList<>();
		for (Iterator<SystemProperties> iterator = properties.iterator(); iterator.hasNext();) {
			stringProperties.add((iterator.next()).toString());	
		}
		if(stringProperties.contains(propertyName2.toUpperCase()))
		{
			return true;
		}
		return false;
	}
}
