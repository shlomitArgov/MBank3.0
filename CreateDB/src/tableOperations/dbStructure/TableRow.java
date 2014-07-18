package tableOperations.dbStructure;

public class TableRow {

	private String[] columnNames;
	private DBVarTypes[] columnTypes;
	private Object[] columnData;
	
	public TableRow(String[] columnNames, DBVarTypes[] columnTypes, Object[] columnData) {
		this.columnNames = columnNames;
		this.columnTypes = columnTypes;
		this.columnData = columnData;
	}

	public DBVarTypes[] getColumnTypes() {
		return columnTypes;
	}

	public Object[] getColumnData() {
		return columnData;
	}

	public String[] getColumnNames() {
		return columnNames;
	}
}
