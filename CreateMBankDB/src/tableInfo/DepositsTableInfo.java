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
public class DepositsTableInfo extends TableInfo {
	private static final String tableName = "Deposits";
	private static final String[] columnNames = new String[]{"client_id","balance", "deposit_type", "estimated_balance", "opening_date", "closing_date"};
	private static final int numColumns = columnNames.length; // number of columns without the primary key
	private static final String primaryKeyName = "deposit_id";
	private static final DBVarTypes[] columnDataTypes = new DBVarTypes[]
	{
		DBVarTypes.BIGINT  // client id
		,DBVarTypes.DOUBLE // balance
		,DBVarTypes.VARCHAR // deposit_type
		,DBVarTypes.DOUBLE // estimated_balance
		,DBVarTypes.BIGINT // opening_date in milliseconds
		,DBVarTypes.BIGINT // closing_date in milliseconds
	};	
	
	public DepositsTableInfo() {
		super(tableName ,numColumns, primaryKeyName, columnNames, columnDataTypes);
	}
}
