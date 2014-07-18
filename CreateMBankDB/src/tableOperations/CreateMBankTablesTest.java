package tableOperations;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import tableInfo.AccountsTableInfo;
import tableInfo.ActivitiesTableInfo;
import tableInfo.ClientsTableInfo;
import tableInfo.DepositsTableInfo;
import tableInfo.PropertiesTableInfo;
import tableOperations.CreateTables;
import tableOperations.dbStructure.TableInfo;

public class CreateMBankTablesTest{

	private static Connection con;
	public static final String dbName = "MBankDB";

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
		
		/* Create the MBank DB tables */
		
		TableInfo[] tablesInfo = new TableInfo[]{new ClientsTableInfo(), new AccountsTableInfo(), new DepositsTableInfo(), new ActivitiesTableInfo(),new PropertiesTableInfo() };
		for(TableInfo tableInfo: tablesInfo)
		{
			System.out.println("Creating table " + tableInfo.getTableName() + "...");
			try {
				CreateTables.createTable(con, tableInfo.getTableName(), tableInfo.getPrimaryKeyName(), tableInfo.getColumnNames(), tableInfo.getColumnDataTypes());
				System.out.println("***Table '" + tableInfo.getTableName() + "' created successfuly***");
			} catch (SQLException e) {
				e.printStackTrace();
				Assert.fail("Failed to create " + tableInfo.getTableName() + " table \n"
						+ e.getLocalizedMessage());
			}
		}
	}
}
