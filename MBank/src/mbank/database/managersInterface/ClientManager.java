/**
 * 
 */
package mbank.database.managersInterface;

import java.sql.Connection;
import java.util.ArrayList;

import mbank.database.beans.Client;
import mbankExceptions.MBankException;

/**
 * @author Shlomit Argov
 * 
 */
public interface ClientManager
{
	long insert(Client client, Connection con) throws MBankException;

	void update(Client client, Connection con) throws MBankException;

	void delete(Client client, Connection con) throws MBankException;

	Client query(Client client, Connection con) throws MBankException;
	
	ArrayList<Client> queryAllClients(Connection con) throws MBankException;

	Client query(long client_id, Connection con) throws MBankException;
	
	Client query(String username, Connection con) throws MBankException;
}
