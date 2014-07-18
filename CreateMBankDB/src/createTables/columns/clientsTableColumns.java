package createTables.columns;

import createTables.DBVarTypes;

public class clientsTableColumns extends tableColumns {

	private static final int numColumns = 7; //number of columns without the primary key
	private static final String[] columnNames = new String[]{"client_name","password", "client_type", "address","email", "phone", "comment"};
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
	
	public clientsTableColumns() {
		super(numColumns, primaryKeyName, columnNames, columnDataTypes);
	}
}
