package mbank.database.beans.enums;

import mbank.exceptions.MBankException;

public enum DepositType
{
	SHORT("SHORT"), LONG("LONG");
	
	private String typeStringValue;
	
	public String getTypeStringValue()
	{
		return typeStringValue;
	}

	private DepositType(String type)
	{
		this.typeStringValue = type;
	}
	
	public static DepositType getEnumFromString(String str) throws MBankException
	{
		switch (str)
		{
			case "SHORT": return DepositType.SHORT;
			case "LONG": return DepositType.LONG;
			default: throw new MBankException("Unknown client type: " + str);
		}
	}

}
