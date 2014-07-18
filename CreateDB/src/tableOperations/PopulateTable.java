package tableOperations;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import tableOperations.dbStructure.TableRow;

public class PopulateTable {
	private Connection con;
	private String tableName;
	private TableRow[] rowValues;
	
	public PopulateTable(Connection con, String tableName, TableRow[] rowValues) {
		this.con = con;
		this.tableName = tableName;
		this.rowValues = rowValues;
	}
	
	public void populate() throws SQLException
	{
		/*sql insert syntax: 
		INSERT INTO table_name (column1,column2,column3,...)
		VALUES (value1,value2,value3,...);*/
		
		for (TableRow row: this.rowValues)
		{
			String sql;
			PreparedStatement pstatement;
			
			sql = "INSERT INTO " + tableName + " (";
			
			String[] columnNames = row.getColumnNames();
			
			for(int i = 0; i<columnNames.length; i++)
			{
				if (i < columnNames.length - 1)
				{
					sql += row.getColumnNames()[i] + ", ";
				}
				else
				{
					sql += row.getColumnNames()[i];		
				}
			}
			sql += ") VALUES (";
			// Add a '?' for each column
			for(int i = 0; i < row.getColumnTypes().length - 1; i++)
			{
				sql +="?, ";
			}
			sql+="?)";
			pstatement = con.prepareStatement(sql, PreparedStatement.NO_GENERATED_KEYS);
			for(int j = 0; j < row.getColumnTypes().length; j++)
			{
				switch(row.getColumnTypes()[j])
				{
				case BIGINT: 
					pstatement.setBigDecimal(j+1, (BigDecimal) row.getColumnData()[j]);
					break;
				case DATE:
					pstatement.setDate(j+1, (Date) row.getColumnData()[j]);
					break;
				case DOUBLE:
					pstatement.setDouble(j+1, (double) row.getColumnData()[j]);
					break;
				case LONGVARCHAR:
					pstatement.setString(j+1, row.getColumnData()[j].toString());
					break;
				case VARCHAR:
					pstatement.setString(j+1, row.getColumnData()[j].toString());
					break;
				default:
					pstatement.setString(j+1, row.getColumnData()[j].toString());
					break;
				}
			}
			pstatement.execute();
		}
	}
}
