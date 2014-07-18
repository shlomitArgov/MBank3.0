/**
 * 
 */
package createTables.columns;

import createTables.DBVarTypes;

/**
 * @author Shlomit
 *
 */
public abstract class tableColumns {
	public tableColumns(int numColumns, String primaryKeyName, String[] columnNames, DBVarTypes[] columndatatypes) {
		this.primaryKeyName = primaryKeyName;
		this.numColumns = numColumns;
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

	private int numColumns;
	private String[] columnNames;
	private DBVarTypes[] columnDataTypes;
	private String primaryKeyName;
}
