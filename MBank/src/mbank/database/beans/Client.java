/**
 * 
 */
package mbank.database.beans;

import mbank.database.beans.enums.ClientType;
import mbankExceptions.MBankException;

/**
 * @author Shlomit Argov
 * 
 */
public class Client
{
	
	private long client_id;
	private String client_name;
	private String password;
	private ClientType type;
	private String address;
	private String email;
	private String phone;
	private String comment;



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Client [client_id=" + client_id + ", client_name="
				+ client_name + ", type=" + type + ", address=" + address
				+ ", email=" + email + ", phone=" + phone + "]";
	}

	public Client(long client_id, String client_name, String password,
			ClientType type, String address, String email, String phone,
			String comment) throws MBankException
	{
		if(client_name.length() == 0)
		{
			throw new MBankException("Client name must not be empty");
		}
		if(password.length() == 0)
		{
			throw new MBankException("Password must not be empty");
		}
		this.client_id = client_id;
		this.client_name = client_name;
		this.password = password;
		this.type = type;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.comment = comment;
	}
	
	public Client(String client_name, String password,
			ClientType type, String address, String email, String phone,
			String comment) throws MBankException
	{
		if(client_name.length() == 0)
		{
			throw new MBankException("Client name must not be empty");
		}
		if(password.length() == 0)
		{
			throw new MBankException("Password must not be empty");
		}
		this.client_id = 0;
		this.client_name = client_name;
		this.password = password;
		this.type = type;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.comment = comment;
	}
	
	public String getClient_name()
	{
		return client_name;
	}

	public void setClient_name(String client_name)
	{
		this.client_name = client_name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public ClientType getType()
	{
		return type;
	}

	public void setType(ClientType type)
	{
		this.type = type;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public long getClient_id()
	{
		return client_id;
	}

	public void setClient_id(long id)
	{
		this.client_id = id;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Client))
			{
			return false;
			}
		else
		{
			Client c = (Client)obj;
			if(this.client_id == ((Client)obj).getClient_id() 
					&& this.client_name.equals((c.getClient_name()))
					&& this.password.equals(c.getPassword())
					&& this.address.equals(c.getAddress())
					&& this.comment.equals(c.getComment())
					&& this.email.equals(c.getEmail())
					&& this.phone.equals(c.getPhone())
					&& this.type.getTypeStringValue().equals(c.getType().getTypeStringValue()))
			{
				return true;
			}
			else
			{
				return false;
			}	
		}
	}
}