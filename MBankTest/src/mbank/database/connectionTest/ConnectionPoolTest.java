/**
 * 
 */
package mbank.database.connectionTest;

import java.sql.Connection;

import mbank.Util;
import mbank.database.connection.ConnectionPool;
import mbank.exceptions.MBankException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Shlomit Argov
 * 
 */
public class ConnectionPoolTest {

	private static final String url = Util.DB_URL;
	private static ConnectionPool connectionPool;
	private static Connection[] connections = new Connection[20];
			
	@BeforeClass
	/* Create a connection pool object initialized with 5 connections */
	public static void oneTimeSetUp() {
		try {
			connectionPool = new ConnectionPool(url, 5);
		} catch (MBankException e) {
			System.err
					.println("Failed to create connection pool to DB: " + url);
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckout() {
		try {
			for (int i = 0; i < connections.length; i++) {
				System.out.println("Number of free connections before checkout: " + connectionPool.getAvailableConnections().size());
				System.out.println("Number of used connections before checkout: " + connectionPool.getUsedConnections().size());
				connections[i] = connectionPool.checkout();
				System.out.println("Checked out connection #:" + i);
				System.out.println("Number of free connections after checkout: " + connectionPool.getAvailableConnections().size());
				System.out.println("Number of used connections after checkout: " + connectionPool.getUsedConnections().size());
				System.out.println();

			}
		} catch (MBankException e) {
			Assert.fail("Failed to checkout connections from connection pool \n" + e.getMessage());
		}
	}

	@Test
	public void testCheckin() {
		for (int i = 0; i < connections.length ; i++) {
			System.out.println("Number of free connections before checkin: " + connectionPool.getAvailableConnections().size());
			System.out.println("Number of used connections before checkin: " + connectionPool.getUsedConnections().size());
			connectionPool.checkin(connections[i]);
			System.out.println("Checked in connection #:" + i);
			System.out.println("Number of free connections after checkin: " + connectionPool.getAvailableConnections().size());
			System.out.println("Number of used connections after checkin: " + connectionPool.getUsedConnections().size());
			System.out.println();
		}	
	}
}
