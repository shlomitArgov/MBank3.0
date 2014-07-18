package createTables.data;

import tableOperations.dbStructure.DBVarTypes;
import tableOperations.dbStructure.TableRow;

public class PropertiesTableData {
	
	public static long regular_deposit_rate = 10000; //regular new client deposit rate
	public static long gold_deposit_rate = 100000; //gold new client deposit rate
	public static long platinum_deposit_rate = 1000000; //platinum new client deposit rate
	public static double regular_deposit_commission = 0.015; //regular commission rate for deposit opening
	public static double gold_deposit_commission = 0.01; //gold commission rate for deposit opening
	public static double platinum_deposit_commission = 0.005; //platinum commission rate for deposit opening
	public static long regular_credit_limit = 10000; //regular account overdraft limit (credit limit)
	public static long gold_credit_limit = 100000; //gold account overdraft limit (credit limit)
	public static long platinum_credit_limit = Long.MAX_VALUE; //platinum account overdraft limit (credit limit) - unlimited
	public static double commission_rate = 0.5; //commission rate for all withdrawals and deposits
	public static double regular_daily_interest = 5.0/365; //regular daily percentage added deposit value
	public static double gold_daily_interest = 7.0/365; //gold daily percentage added deposit value
	public static double platinum_daily_interest = 8.0/365; //platinum daily percentage added deposit value
	public static double pre_open_fee = 0.01; //commission rate for deposit pre-opening
	public static String admin_username = "system"; //Default username for all system administrators
	public static String admin_password = "admin"; //Default password for all system administrators

	static Object[] propertiesData = new Object[]{
			regular_deposit_rate, 
			gold_deposit_rate,
			platinum_deposit_rate,
			regular_deposit_commission,
			gold_deposit_commission,
			platinum_deposit_commission,
			regular_credit_limit,
			gold_credit_limit,
			platinum_credit_limit,
			commission_rate,
			regular_daily_interest,
			gold_daily_interest,
			platinum_daily_interest,
			pre_open_fee,
			admin_username,
			admin_password
			};

	private DBVarTypes[] columnTypes = new DBVarTypes[]{DBVarTypes.VARCHAR, DBVarTypes.VARCHAR};
	private String[][] columnData = new String[][]{
			{"regular_deposit_rate", String.valueOf(regular_deposit_rate)},
			{"gold_deposit_rate", String.valueOf(gold_deposit_rate)},
			{"platinum_deposit_rate", String.valueOf(platinum_deposit_rate)},
			{"regular_deposit_commission", String.valueOf(regular_deposit_commission)},
			{"gold_deposit_commission", String.valueOf(gold_deposit_commission)},
			{"platinum_deposit_commission", String.valueOf(platinum_deposit_commission)},
			{"regular_credit_limit", String.valueOf(regular_credit_limit)},
			{"gold_credit_limit", String.valueOf(gold_credit_limit)},
			{"platinum_credit_limit", String.valueOf(platinum_credit_limit)},
			{"commission_rate", String.valueOf(commission_rate)},
			{"regular_daily_interest", String.valueOf(regular_daily_interest)},
			{"gold_daily_interest", String.valueOf(gold_daily_interest)},
			{"platinum_daily_interest", String.valueOf(platinum_daily_interest)},
			{"pre_open_fee", String.valueOf(pre_open_fee)},
			{"admin_username", admin_username},
			{"admin_password", admin_password}
	};
	private String[] columnNames = new String[]{"prop_key", "prop_value"}; 
	private TableRow[] tableRows = new TableRow[columnData.length];
	
	public PropertiesTableData()
	{
		for(int i = 0; i < columnData.length; i++)
		{
			tableRows[i] = new TableRow(columnNames, columnTypes, columnData[i]);
		}
	}
	
	public TableRow[] getTableRows() {
		return tableRows;
	}
}
