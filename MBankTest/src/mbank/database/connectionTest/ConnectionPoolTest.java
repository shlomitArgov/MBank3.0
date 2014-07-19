/**
 * 
 */
package mbank.database.connectionTest;

import java.sql.Connection;
import mbank.database.connection.ConnectionPool;
import mbankExceptions.MBankException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Shlomit Argov
 * 
 */
public class ConnectionPoolTest {

	private static final String url = "jdbc:derby://localhost:1527/MBankDB;";
	private static ConnectionPool connectionPool;
	private static Connection[] connections =new Connection[20];
			
	@BeforeClass
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
				connections[i] = connectionPool.checkout();
			}
		} catch (MBankException e) {
			Assert.fail("Failed to checkout connections from connection pool \n" + e.getMessage());
		}
	}

	@Test
	public void testCheckin() {
		for (int i = 0; i < connections.length; i++) {
//			if(connections[i] == null)
//			{
//				System.out.println("connection is null");
//			}
			connectionPool.checkin(connections[i]);
		}	
	}
}
