package tableInfo;

import tableOperations.dbStructure.DBVarTypes;
import tableOperations.dbStructure.TableInfo;

/**
 * 
 * @author Shlomit Argov
 *
 */
public class ActivitiesTableInfo extends TableInfo {

	private static final String tableName = "Activity";
	private static final String[] columnNames = new String[]{"client_id","amount", "activity_date", "commission", "ACTIVITY_TYPE", "description"};
	private static final int numColumns = columnNames.length; // number of columns without the primary key
	private static final String primaryKeyName = "activity_id";
	private static final DBVarTypes[] columnDataTypes = new DBVarTypes[]
	{
		 DBVarTypes.BIGINT  // client id
		,DBVarTypes.DOUBLE // amount
		,DBVarTypes.DATE // activity_date
		,DBVarTypes.DOUBLE //commission
		,DBVarTypes.INTEGER // ACTIVITY_TYPE
		,DBVarTypes.LONGVARCHAR // description
	};	
	
	public ActivitiesTableInfo() {
		super(tableName ,numColumns, primaryKeyName, columnNames, columnDataTypes);
	}
}
