package mbank.database.beans;

public class Property
{
	public Property(String prop_key, String prop_value)
	{
		this.prop_key = prop_key;
		this.prop_value = prop_value;
	}
	private String prop_key;
	private String prop_value;
	public String getProp_key()
	{
		return prop_key;
	}
	public String getProp_value()
	{
		return prop_value;
	}
	public void setProp_key(String prop_key)
	{
		this.prop_key = prop_key;
	}
	public void setProp_value(String prop_value)
	{
		this.prop_value = prop_value;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Property)
		{
			if (((Property)obj).getProp_key().equals(this.prop_key) && ((Property)obj).getProp_value().equals(this.prop_value))
			{
				return true;
			}		
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Property [prop_key=" + prop_key + ", prop_value=" + prop_value
				+ "]";
	}	
}