package mbank.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mbankExceptions.MBankException;

public class ConnectionPool implements Runnable {
	private static String url;
	private List<Connection> availableConnections = new ArrayList<Connection>();
	private List<Connection> usedConnections = new ArrayList<Connection>();

	public ConnectionPool(String url, int initialConnectionNum)
			throws MBankException {
		ConnectionPool.url = url;
		for (int i = 0; i < initialConnectionNum; i++) {
			availableConnections.add(getConnection(url));
		}

	}

	private Connection getConnection(String url) throws MBankException {
		try {
			Connection con = DriverManager.getConnection(url);
			return con;
		} catch (SQLException e) {
			throw new MBankException("Failed to create connection to DB: "
					+ url, e);
		}
	}

	public synchronized Connection checkout() throws MBankException {
		Connection con;
		if (availableConnections.size() > 0) {// There are available connections
			con = availableConnections.get(availableConnections.size() - 1);
			try {// Make sure available connection is not closed
				if (con.isClosed()) {
					do {
						availableConnections.remove(con);
					} while (availableConnections.size() > 0
							&& (con = availableConnections
									.get(availableConnections.size() - 1))
									.isClosed());
				}
			} catch (SQLException e) {
				throw new MBankException("Failed to check connection status", e);
			}
			if (availableConnections.size() > 0) {// If after removing closed
													// connections there are
													// still available
													// connections - checkout an
													// available connection
				availableConnections.remove(con);
				usedConnections.add(con);
			} else {// All open connections were closed - get a new connection
				con = getConnection(ConnectionPool.url);
				usedConnections.add(con);
			}
		} else {// No available connections exist
			con = getConnection(ConnectionPool.url);
			usedConnections.add(con);
		}
		return con;
	}

	public synchronized void checkin(Connection con) {
		if (con == null) {
			return;
		}
		usedConnections.remove(con);
		availableConnections.add(con);
	}
	
	public synchronized void exit() throws SQLException
	{
		Iterator<Connection> availableIt = availableConnections.iterator();
		Iterator<Connection> usedIt = usedConnections.iterator();
		Connection con = null;
		while(availableIt.hasNext())
		{
			con = availableIt.next();
			con.close();
		}
		while(usedIt.hasNext())
		{
			con = availableIt.next();
			con.close();
		}
	}

	@Override
	public void run() {
		while (true) {
			synchronized (this) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					try {
						throw new MBankException("Error occured in thread"
								+ Thread.currentThread().getName(), e);
					} catch (MBankException e1) {
						// * TODO Auto-generated catch block
						// System.err.println(e1.getMessage()); } }
					}
				}
			}
		}
	}
}
