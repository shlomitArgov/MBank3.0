package tableOperations;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import mbank.database.beans.enums.ClientType;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import tableOperations.dbStructure.DBVarTypes;
import tableOperations.dbStructure.TableRow;

public class InsertAdminClientTest {
	private static final String tableName = "clients";
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
	
	@Test
	public void test() {
		System.out.println("Populating table " + tableName + "...");
		
		String[] columnNames = new String[]{"CLIENT_NAME", "PASSWORD", "CLIENT_TYPE", "ADDRESS", "EMAIL", "PHONE", "COMMENT"};
		DBVarTypes[] columnTypes = new DBVarTypes[]{DBVarTypes.VARCHAR, DBVarTypes.VARCHAR, DBVarTypes.VARCHAR, DBVarTypes.VARCHAR, DBVarTypes.VARCHAR, DBVarTypes.VARCHAR, DBVarTypes.VARCHAR};
		Object[] columnData = new Object[]{"system", "admin", ClientType.PLATINUM.getTypeStringValue(), "Ehad Ha'am 1, Tel-Aviv", "system@MBank.com", "555-5555", "Obey the admin"};
		TableRow adminRow = new TableRow(columnNames, columnTypes, columnData);
		TableRow[] rows = new TableRow[]{adminRow};
		PopulateTable adminUserTableEntry = new PopulateTable(con, tableName, rows);
		try{
			adminUserTableEntry.populate();	
		System.out.println("***Table " + tableName + " population completed***");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Failed to populate '" + tableName + "' table");
		}
	}

}
