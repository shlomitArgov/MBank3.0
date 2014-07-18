package tableOperations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DropTables {

	public static void dropTable(Connection con, String tableName) throws SQLException {
		String sql = "Drop table " + tableName;
		PreparedStatement pStatement = con.prepareStatement(sql);
		pStatement.execute();
	}
}
