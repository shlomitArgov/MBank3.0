package tableOperations;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DropMBankTablesTest {

	static Connection con;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//Get connection
		con = DriverManager
				.getConnection("jdbc:derby://localhost:1527/" + CreateMBankTablesTest.dbName);
		
	}

	@Test
	public void testDropTables() {
		
		String[] tableNames = new String[]{"Clients", "Accounts", "Deposits", "Activity", "Properties"}; 
		
		for(String tableName : tableNames)
		{
			System.out.println("Dropping table '" + tableName + "'...");
			try {
				DropTables.dropTable(con, tableName);
				System.out.println("***Table " + tableName + " has been dropped successfuly***");
			} catch (SQLException e) {
				Assert.fail("Failed to drop table " + tableName + "\n" + e.getLocalizedMessage());
				e.printStackTrace();
			}
		}		
//		String tableName = "Clients";
//		try {
//			System.out.println("Dropping table " + tableName + "...");
//			DropTables.dropTable(con, tableName);
//		} catch (SQLException e) {
//			Assert.fail("Failed to drop table " + tableName + e.getLocalizedMessage());
//			e.printStackTrace();
//		}
//		
//		tableName = "Accounts";
//		try {
//			System.out.println("Dropping table " + tableName + "...");
//			DropTables.dropTable(con, tableName);
//		} catch (SQLException e) {
//			Assert.fail("Failed to drop table " + tableName + e.getLocalizedMessage());
//			e.printStackTrace();
//		}
//		
//		tableName = "Deposits";
//		try {
//			System.out.println("Dropping table " + tableName + "...");
//			DropTables.dropTable(con, tableName);
//		} catch (SQLException e) {
//			Assert.fail("Failed to drop table " + tableName + e.getLocalizedMessage());
//			e.printStackTrace();
//		}
//		
//		tableName = "Activity";
//		try {
//			System.out.println("Dropping table " + tableName + "...");
//			DropTables.dropTable(con, tableName);
//		} catch (SQLException e) {
//			Assert.fail("Failed to drop table " + tableName + e.getLocalizedMessage());
//			e.printStackTrace();
//		}
//		
//		tableName = "Properties";
//		try {
//			System.out.println("Dropping table " + tableName + "...");
//			DropTables.dropTable(con, tableName);
//		} catch (SQLException e) {
//			Assert.fail("Failed to drop table " + tableName + e.getLocalizedMessage());
//			e.printStackTrace();
//		}
	}

}
