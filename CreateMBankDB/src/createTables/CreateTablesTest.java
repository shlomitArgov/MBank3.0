package createTables;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import createTables.columns.AccountsTableColumns;
import createTables.columns.clientsTableColumns;
import createTables.columns.tableColumns;

public class CreateTablesTest{

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
//
//		// Create Deposits table
//		tableName = "Deposits";
//		System.out.println("Creating table " + tableName + "...");
//		try {
//			CreateTables.createTable(con, tableName, new tableColumns("deposit_id",
//					DBVarTypes.BIGINT.getName()), new tableColumns("client_id",
//					DBVarTypes.BIGINT.getName()), new tableColumns("balance",
//					DBVarTypes.DOUBLE.getName()), new tableColumns("type",
//					DBVarTypes.VARCHAR.getName()), new tableColumns(
//					"estimated_balance", DBVarTypes.BIGINT.getName()),
//					new tableColumns("opening_date", DBVarTypes.DATE.getName()),
//					new tableColumns("closing_date", DBVarTypes.DATE.getName()));
//		} catch (SQLException e) {
//			e.printStackTrace();
//			Assert.fail("Failed to create " + tableName + " table \n"
//					+ e.getLocalizedMessage());
//		}
//
//		// Create Activity table
//		tableName = "Activity";
//		System.out.println("Creating table " + tableName + "...");
//		try {
//			CreateTables.createTable(con, "Activity", new tableColumns("id",
//					DBVarTypes.BIGINT.getName()), new tableColumns("client_id",
//					DBVarTypes.BIGINT.getName()), new tableColumns("amount",
//					DBVarTypes.DOUBLE.getName()), new tableColumns("activity_date",
//					DBVarTypes.DATE.getName()), new tableColumns("commission",
//					DBVarTypes.DOUBLE.getName()), new tableColumns("ACTIVITY_TYPE",
//					DBVarTypes.VARCHAR.getName()), new tableColumns("description",
//					DBVarTypes.LONGVARCHAR.getName()));
//		} catch (SQLException e) {
//			e.printStackTrace();
//			Assert.fail("Failed to create " + tableName + " table \n"
//					+ e.getLocalizedMessage());
//		}
//
//		// Create Properties table
//		tableName = "Properties";
//		System.out.println("Creating table " + tableName + "...");
//		try {
//			CreateTables.createTable(con, tableName, new tableColumns("prop_key",
//					DBVarTypes.VARCHAR.getName()), new tableColumns("prop_value",
//					DBVarTypes.VARCHAR.getName()));
//		} catch (SQLException e) {
//			e.printStackTrace();
//			Assert.fail("Failed to create " + tableName + " table \n"
//					+ e.getLocalizedMessage());
//		}
	}
}
