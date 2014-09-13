/**
 * 
 */
package mbank.database.managersInterface;

import java.util.ArrayList;

import mbank.database.beans.Client;
import mbank.exceptions.MBankException;

/**
 * @author Shlomit Argov
 * 
 */
public interface ClientManager
{
	long insert(Client client) throws MBankException;

	void update(Client client) throws MBankException;

	void delete(long clientId) throws MBankException;

	Client query(Client client) throws MBankException;
	
	ArrayList<Client> queryAllClients() throws MBankException;

	Client query(long client_id) throws MBankException;
	
	Client query(String username) throws MBankException;
}
