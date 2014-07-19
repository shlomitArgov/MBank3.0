package tableOperations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import tableOperations.dbStructure.DBVarTypes;

/**
 * 
 */

/**
 * @author Shlomit Argov
 */
public class CreateTables {
	/**
	 * This class provides a service method (createTable) that creates tables in a given 
	 * DB. It currently supports primary keys that are auto incremental integer
	 * values or no primary key for a table.
	 * 
	 * @param con - Connection to DB
	 * @param tableName - Table name in DB
	 * @param primaryKeyName - name of primary key auto incremental numeric column. 
	 * If null - no primamry key column is created. 
	 * @param columnNames - Names of columns apart for the primary key column
	 * @param columnTypes - Types of all columns apart for the primary key column
	 * @return
	 * @throws SQLException
	 */
	public static boolean createTable(Connection con, String tableName, String primaryKeyName, String[]  columnNames, DBVarTypes[] columnTypes) throws SQLException
	{
		String sql = "CREATE TABLE " + tableName + " (";
		if (primaryKeyName != null)
		{
			sql += primaryKeyName + " INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)";
		
		
			for (int i = 0; i < columnNames.length; i++) {
				sql += ", " + columnNames[i] + " " + columnTypes[i].getName() ;
			}
		}
		else
		{
			for (int i = 0; i < columnNames.length -1; i++) {
				sql += columnNames[i] + " " + columnTypes[i].getName() + ", ";
			}
			sql += columnNames[columnNames.length -1] + " " + columnTypes[columnTypes.length -1].getName();
		}
		sql+=")";
		System.out.println(sql);
		PreparedStatement pstatement = con.prepareStatement(sql);
		pstatement.execute();
		return true;
	}
}