package createTables;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import createTables.columns.AccountsTableColumns;
import createTables.columns.ActivitiesTableColumns;
import createTables.columns.DepositsTableColumns;
import createTables.columns.clientsTableColumns;
import createTables.columns.propertiesTableColumns;
import createTables.columns.tableColumns;

public class CreateMBankTablesTest{

	private static Connection con;
	private static final String dbName = "MBankDB";

	public static String getDbname() {
		return dbName;
	}

	@BeforeClass
	public static void oneTimeSetUp() {
		/* Initialization code - create DB connection */ 
		System.out.println("@BeforeClass - oneTimeSetUp\n: Creating connection and connecting to Derby DB");
		try {
			con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbName + ";create=true");
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail("Failed to create connection to Derby DB\n"
					+ e.getLocalizedMessage() + "\n" + e.getStackTrace());
		}
	}

	@Test
	public void testCreateTables() {
		/* Create the clients table */
		tableColumns clientsTableColumns = new clientsTableColumns();
		System.out.println("Creating table " + clientsTableColumns.getTableName() + "...");
		try {
			CreateTables.createTable(con, clientsTableColumns.getTableName(), clientsTableColumns.getPrimaryKeyName(), clientsTableColumns.getColumnNames(), clientsTableColumns.getColumnDataTypes());
			System.out.println("***Table " + clientsTableColumns.getTableName() + " created successfuly***");
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail("Failed to create " + clientsTableColumns.getTableName() + " table \n"
					+ e.getLocalizedMessage());
		}

		// Create the Accounts table
		tableColumns accountsTableColumns = new AccountsTableColumns();
		System.out.println("Creating table " + accountsTableColumns.getTableName() + "...");
		try {
			CreateTables.createTable(con, accountsTableColumns.getTableName(), accountsTableColumns.getPrimaryKeyName(), accountsTableColumns.getColumnNames(), accountsTableColumns.getColumnDataTypes());
			System.out.println("***Table " + accountsTableColumns.getTableName() + " created successfuly***");
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail("Failed to create " + accountsTableColumns.getTableName() + " table \n"
					+ e.getLocalizedMessage());
		}

		// Create the Deposits table
		tableColumns depositsTableColumns = new DepositsTableColumns();
		System.out.println("Creating table " + depositsTableColumns.getTableName() + "...");
		try {
			CreateTables.createTable(con, depositsTableColumns.getTableName(), depositsTableColumns.getPrimaryKeyName(), depositsTableColumns.getColumnNames(), depositsTableColumns.getColumnDataTypes());
			System.out.println("***Table " + depositsTableColumns.getTableName() + " created successfuly***");
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail("Failed to create " + depositsTableColumns.getTableName() + " table \n"
					+ e.getLocalizedMessage());
		}

		// Create the Activity table
		tableColumns activitiesTableColumns = new ActivitiesTableColumns();
		System.out.println("Creating table " + activitiesTableColumns.getTableName() + "...");
		try {
			CreateTables.createTable(con, activitiesTableColumns.getTableName(),  activitiesTableColumns.getPrimaryKeyName(), activitiesTableColumns.getColumnNames(), activitiesTableColumns.getColumnDataTypes());
			System.out.println("***Table " + activitiesTableColumns.getTableName() + " created successfuly***");
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail("Failed to create " + activitiesTableColumns.getTableName() + " table \n" + e.getLocalizedMessage());
		}

		// Create the Properties table
		tableColumns propertiesTableColumns = new propertiesTableColumns();
		System.out.println("Creating table " + propertiesTableColumns.getTableName() + "...");
		try {
			CreateTables.createTable(con, propertiesTableColumns.getTableName(), propertiesTableColumns.getPrimaryKeyName(), propertiesTableColumns.getColumnNames(), propertiesTableColumns.getColumnDataTypes());
			System.out.println("***Table " + propertiesTableColumns.getTableName() + " created successfuly***");
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail("Failed to create " + propertiesTableColumns.getTableName() + " table \n"
					+ e.getLocalizedMessage());
		}
	}
}
