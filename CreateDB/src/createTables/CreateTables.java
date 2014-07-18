package createTables;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 */

/**
 * @author Shlomit Argov
 *
 */
public class CreateTables {
	public static boolean createTable(Connection con, String tableName, String primaryKeyName, String[]  columnNames, DBVarTypes[] columnTypes) throws SQLException
	{
		String sql = "CREATE TABLE " + tableName + "( ";
		sql += primaryKeyName + " INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)";
		
		for (int i = 0; i < columnNames.length; i++) {
			sql += ", " + columnNames[i] + " " + columnTypes[i].getName() ;
		}
//		sql += "CONSTRAINT primary_key PRIMARY KEY (" + primaryKeyName + ")";
		sql+=")";
		PreparedStatement pstatement = con.prepareStatement(sql);
		pstatement.execute();
		return true;
	}
}

/*
CREATE TABLE students
(
id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
name VARCHAR(24) NOT NULL,
address VARCHAR(1024),
CONSTRAINT primary_key PRIMARY KEY (id)
) ;*/