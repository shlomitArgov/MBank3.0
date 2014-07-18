/**
 * 
 */
package tableOperations.dbStructure;



/**
 * @author Shlomit
 *
 */
public abstract class TableInfo {
	public TableInfo(String tableName,int numColumns, String primaryKeyName, String[] columnNames, DBVarTypes[] columndatatypes) {
		this.tableName = tableName;
		this.numColumns = numColumns;
		this.primaryKeyName = primaryKeyName;
		this.columnNames = columnNames;
		this.columnDataTypes = columndatatypes;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public String[] getColumnNames() {
		return columnNames;
	}
	public DBVarTypes[] getColumnDataTypes() {
		return columnDataTypes;
	}

	public String getPrimaryKeyName() {
		return primaryKeyName;
	}

	public String getTableName() {
		return tableName;
	}

	private String tableName;
	private int numColumns;
	private String[] columnNames;
	private DBVarTypes[] columnDataTypes;
	private String primaryKeyName;
}
