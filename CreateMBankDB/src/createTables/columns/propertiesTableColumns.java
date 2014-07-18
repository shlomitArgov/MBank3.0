/**
 * 
 */
package createTables.columns;

import createTables.DBVarTypes;

/**
 * @author Shlomit Argov
 *
 */
public class propertiesTableColumns extends tableColumns {

	private static final String tableName = "Properties";
	private static final String[] columnNames = new String[]{"prop_key","prop_value"};
	private static final int numColumns = columnNames.length; // number of columns without the primary key
	private static final String primaryKeyName = "property_id";
	private static final DBVarTypes[] columnDataTypes = new DBVarTypes[]
	{
		DBVarTypes.VARCHAR // prop_key
		,DBVarTypes.VARCHAR // prop_value
	};	
	
	public propertiesTableColumns() {
		super(tableName ,numColumns, primaryKeyName, columnNames, columnDataTypes);
	}
}