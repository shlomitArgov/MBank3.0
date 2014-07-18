package tableInfo;

import tableOperations.dbStructure.DBVarTypes;
import tableOperations.dbStructure.TableInfo;

public class ClientsTableInfo extends TableInfo {
	private static final String tableName = "clients";
	private static final String[] columnNames = new String[]{"client_name","password", "client_type", "address","email", "phone", "comment"};
	private static final int numColumns = columnNames.length; //number of columns without the primary key
	private static final String primaryKeyName = "client_id";
	private static final DBVarTypes[] columnDataTypes = new DBVarTypes[]
	{
		DBVarTypes.VARCHAR //name
		,DBVarTypes.VARCHAR //password
		,DBVarTypes.VARCHAR //type
		,DBVarTypes.VARCHAR //address
		,DBVarTypes.VARCHAR //email
		,DBVarTypes.VARCHAR //phone
		,DBVarTypes.LONGVARCHAR //comment
	};	
	
	public ClientsTableInfo() {
		super(tableName, numColumns, primaryKeyName, columnNames, columnDataTypes);
	}
}
