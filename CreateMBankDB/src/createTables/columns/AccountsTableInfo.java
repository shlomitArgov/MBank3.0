/**
 * 
 */
package createTables.columns;

import createTables.DBVarTypes;

/**
 * @author Shlomit Argov
 *
 */
public class AccountsTableInfo extends TableInfo {

	private static final String tableName = "Accounts";
	private static final String[] columnNames = new String[]{"client_id","balance", "credit_limit", "comment"};
	private static final int numColumns = columnNames.length; // number of columns without the primary key
	private static final String primaryKeyName = "account_id";
	private static final DBVarTypes[] columnDataTypes = new DBVarTypes[]
	{
		DBVarTypes.BIGINT  // client id
		,DBVarTypes.DOUBLE // balance
		,DBVarTypes.DOUBLE // credit_limit
		,DBVarTypes.LONGVARCHAR // comment
	};	
	
	public AccountsTableInfo() {
		super(tableName ,numColumns, primaryKeyName, columnNames, columnDataTypes);
	}
}
