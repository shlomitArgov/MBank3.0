/**
 * 
 */
package mbank;

import java.sql.Connection;
import java.sql.SQLException;

import mbank.actions.Action;
import mbank.actions.AdminAction;
import mbank.actions.ClientAction;
import mbank.database.beans.Client;
import mbank.database.beans.enums.SystemProperties;
import mbank.database.connection.ConnectionPool;
import mbank.database.managersImpl.ClientDBManager;
import mbank.database.managersImpl.PropertyDBManager;
import mbank.database.managersInterface.PropertyManager;
import mbank.exceptions.MBankException;

/**
 * @author Shlomit Argov
 *
 */
public class MBank
{	//MBank Singleton class
	private static MBank instance = null;
	private static final String url="jdbc:derby://localhost:1527/MBankDB;"; //jdbc URL
	private static final int initialConnectionNum = 10;	//initial size of connection pool
	private ConnectionPool connectionPool;
	
	private MBank() throws MBankException
	{	
		//Generate connection pool
		//load driver
		try 
		{
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		connectionPool = new ConnectionPool(url, initialConnectionNum);
		/* -- Commented out due to Tomcat limitations --
		//launch daily maintenance daemon thread responsible for closing expired deposits
		PeriodicDepositMaintenance depositMaintenance = new PeriodicDepositMaintenance(this);
		depositMaintenance.launch();
		*/
	}

	public static MBank getInstance()
	{
		if (instance == null)
		{
			try 
			{
				instance = new MBank();
			} catch (MBankException e) 
			{
				System.exit(1);
			} 
			return instance;
		}
		return instance;
	}
	
	public Connection getConnection() throws MBankException
	{
		return connectionPool.checkout();
//		return con;
	}
	
	public void returnConnection(Connection con) throws MBankException
	{
		connectionPool.checkin(con);
	}
	
	public void exit() throws SQLException
	{
		connectionPool.exit();
//		con.close();
	}
	
	/**
	 * 
	 * @param user_id  
	 * @param password 
	 * @return Action (either AdminAction or ClientAction)
	 * @throws MBankException - in case of unknown username-password combination
	 */
	public Action login(String username, String password) throws MBankException
	{
		ClientDBManager clientManager = new ClientDBManager();
		Client client = clientManager.query(username);
		if (client == null)
		{
			throw new MBankException("Unknown username-password combination");
		}
		//confirm user-password combination
		if(client.getPassword().equals(password))
		{
			//check if the user is an admin
			PropertyManager propertyManager = new PropertyDBManager();
			String adminPwd = propertyManager.query(SystemProperties.ADMIN_PASSWORD.getPropertyName()).getProp_value();
			String adminUsername = propertyManager.query(SystemProperties.ADMIN_USERNAME.getPropertyName()).getProp_value();
			if (client.getPassword().equals(adminPwd) && client.getClient_name().equals(adminUsername))
			{
				return new AdminAction(client.getClient_id());
			}
			else 
			{
				return new ClientAction(client.getClient_id());
			}

		}
		else
		{
			throw new MBankException("Unknown username-password combination");
		}
	}
}