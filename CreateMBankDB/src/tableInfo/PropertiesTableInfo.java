/**
 * 
 */
package tableInfo;

import tableOperations.dbStructure.DBVarTypes;
import tableOperations.dbStructure.TableInfo;

/**
 * @author Shlomit Argov
 *
 */
public class PropertiesTableInfo extends TableInfo {

	private static final String tableName = "Properties";
	private static final String[] columnNames = new String[]{"prop_key","prop_value"};
	private static final int numColumns = columnNames.length; // number of columns without the primary key
	private static final String primaryKeyName = null;
	private static final DBVarTypes[] columnDataTypes = new DBVarTypes[]
	{
		DBVarTypes.VARCHAR // prop_key
		,DBVarTypes.VARCHAR // prop_value
	};	
	
	public PropertiesTableInfo() {
		super(tableName ,numColumns, primaryKeyName, columnNames, columnDataTypes);
	}
}