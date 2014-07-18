package mbank.database.beans.enums;

import mbankExceptions.MBankException;

public enum ClientType
{

	REGULAR("REGULAR"), GOLD("GOLD"), PLATINUM("PLATINUM");

	private String type;

	private ClientType(String type)
	{
		this.type = type;
	}

	public String getTypeStringValue()
	{
		return type;
	}
	
	public static ClientType getEnumFromString(String str) throws MBankException
	{
		switch (str)
		{
			case "REGULAR": return ClientType.REGULAR;
			case "GOLD": return ClientType.GOLD;
			case "PLATINUM": return ClientType.PLATINUM;
			default: throw new MBankException("Unknown client type: " + str);
		}
	}
}
