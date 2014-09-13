package mbank.database.beans;

import mbank.exceptions.MBankException;

public class Property {
	private String prop_key;
	private String prop_value;

	public Property(String prop_key, String prop_value) throws MBankException {
		validatePropertyType(prop_key, prop_value);
		this.prop_key = prop_key;
		this.prop_value = prop_value;
	}

	private void validatePropertyType(String prop_key2, String prop_value2) throws MBankException
	{
		switch (prop_key2) {
		// properties of type "long"
		case "regular_deposit_rate":
		case "gold_deposit_rate":
		case "platinum_deposit_rate":
		case "regular_credit_limit":
		case "gold_credit_limit":
		case "platinum_credit_limit":
			try {
				Long.valueOf(prop_value2);
			} catch (NumberFormatException e) {
				throw new MBankException("Invalid value for property '" + prop_key2 + "'\nValue: '" + prop_value2+ "'");
			}
			break;
		// properties of type "double"
		case "regular_deposit_commission":
		case "gold_deposit_commission":
		case "platinum_deposit_commission":
		case "commission_rate":
		case "regular_daily_interest":
		case "gold_daily_interest":
		case "platinum_daily_interest":
		case "pre_open_fee":
			try {
				Double.valueOf(prop_value2);
			} catch (NumberFormatException e) {
				throw new MBankException(
						"Invalid value for property '" + prop_key2	+ "'\nValue: '" + prop_value2+ "'");
			}
			break;
		// properties of type "String"
		case "admin_username":
		case "admin_password":
			if (!(prop_value2 instanceof String))
			{
				throw new MBankException(
						"Invalid property value for property '" + prop_key2	+ "'\n");
			}
			break;
		}
	}

	public String getProp_key() {
		return prop_key;
	}

	public String getProp_value() {
		return prop_value;
	}

	public void setProp_key(String prop_key) {
		this.prop_key = prop_key;
	}

	public void setProp_value(String prop_value) {
		this.prop_value = prop_value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Property) {
			if (((Property) obj).getProp_key().equals(this.prop_key)
					&& ((Property) obj).getProp_value().equals(this.prop_value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Property [prop_key=" + prop_key + ", prop_value=" + prop_value
				+ "]\n";
	}
}