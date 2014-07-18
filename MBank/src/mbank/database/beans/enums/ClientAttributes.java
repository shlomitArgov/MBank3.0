/**
 * 
 */
package mbank.database.beans.enums;

/**
 * @author Shlomit Argov
 *
 */
public enum ClientAttributes
{
	ADDRESS("Adress"), PHONE("Phone"), EMAIL("Email"), CLIENT_TYPE("client_type"), COMMENT("comment");
	
	ClientAttributes(String attribute)
	{
		this.attribute = attribute;
	}

	private String attribute;

	public String getAttribute()
	{
		return attribute;
	}

	public void setAttribute(String attribute)
	{
		this.attribute = attribute;
	}
	
}
