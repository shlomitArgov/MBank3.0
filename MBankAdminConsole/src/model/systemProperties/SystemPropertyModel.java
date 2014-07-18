package model.systemProperties;

import java.util.List;

import mbank.actions.AdminAction;
import mbank.database.beans.Property;
import mbankExceptions.MBankException;
import model.login.LoginAction;

/**
 * @author Shlomit Argov
 * This Singletone class is used to communicate between the server's model 
 * and the UI''s  model for system properties 
 */

public class SystemPropertyModel{
	private static AdminAction adminAction = LoginAction.getAdminAction();
	private static SystemPropertyModel instance = null; 
	
	/* Init model with data from the server */
	private SystemPropertyModel(){
		getProperties();
	}
	/* Get properties from the DB */
	public List<Property> getProperties(){
		return adminAction.viewSystemProperties();
	}
	

	public void setProperty(Property property) throws MBankException{
		adminAction.updateSystemProperty(property);
	}
	
	public static SystemPropertyModel getInstance(AdminAction action){
		if(instance == null)
		{
			instance = new SystemPropertyModel();
			return instance;
		}
		return instance;
	}
}
