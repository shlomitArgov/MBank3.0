package tableOperations;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import createTables.data.PropertiesTableData;

public class PopulatePropertiesTable {
	
	private static final String tableName = "PROPERTIES";
	private static Connection con;
	private static final String dbName = CreateMBankTablesTest.dbName;
		
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// one-time initialization code - CreateBankDB
		System.out
				.println("@BeforeClass - oneTimeSetUp: Creating a connection and connecting to Derby DB");
		try {
			con = DriverManager.getConnection("jdbc:derby://localhost:1527/"
					+ dbName + ";create=true");
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail("Failed to connect to Derby DB\n"
					+ e.getLocalizedMessage() + "\n" + e.getStackTrace());
		}
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testPopulatePropertiesTable() {
	
		System.out.println("Populating table " + tableName + "...");
		
		PropertiesTableData propertiesRows = new PropertiesTableData();
		
		PopulateTable populatePropertiesTable = new PopulateTable(con, tableName, propertiesRows.getTableRows());
		try{
		populatePropertiesTable.populate();	
		System.out.println("***Table " + tableName + " population completed***");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Failed to populate '" + tableName + "' table");
		}
	}
}
