/**
 * 
 */
package mbank.actions;

/**
 * @author Shlomit Argov
 *
 */
public class TableValue
{
	private String columnName;
	private String columnValue;
	
	public TableValue(String columnName, String columnValue)
	{
		this.columnName = columnName;
		this.columnValue = columnValue;
	}
	
	public String getColumnName()
	{
		return columnName;
	}
	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}
	public String getColumnValue()
	{
		return columnValue;
	}
	public void setColumnValue(String columnValue)
	{
		this.columnValue = columnValue;
	}
}
