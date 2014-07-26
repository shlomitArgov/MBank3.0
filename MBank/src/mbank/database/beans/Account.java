/**
 * 
 */
package mbank.database.beans;

/**
 * @author Shlomit Argov
 *
 */
public class Account
{
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Account [account_id=" + account_id + ", client_id=" + client_id
				+ ", balance=" + balance + ", credit_limit=" + credit_limit
				+ ", comment=" + comment + "]";
	}

	private long account_id;
	private long client_id;
	private double balance;
	private double credit_limit;
	private String comment;

	public Account(long account_id, long client_id, double balance,
			double credit_limit, String comment)
	{
		this.account_id = account_id;
		this.client_id = client_id;
		this.balance = balance;
		this.credit_limit = credit_limit;
		this.comment = comment;
	}
	public Account(long client_id, double balance,
			double credit_limit, String comment)
	{
		this.account_id = 0;
		this.client_id = client_id;
		this.balance = balance;
		this.credit_limit = credit_limit;
		this.comment = comment;
	}

	public long getClient_id()
	{
		return client_id;
	}

	public void setClient_id(long client_id)
	{
		this.client_id = client_id;
	}

	public double getBalance()
	{
		return balance;
	}

	public void setBalance(double balance)
	{
		this.balance = balance;
	}

	public double getCredit_limit()
	{
		return credit_limit;
	}

	public void setCredit_limit(double credit_limit)
	{
		this.credit_limit = credit_limit;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public long getAccount_id()
	{
		return account_id;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Account)
		{
			if (((Account)obj).getAccount_id() == this.account_id)
			{
				return true;
			}
			
		}
		return false;
	}
	
	public void setAccount_id(long account_id)
	{
		this.account_id = account_id;
	}
}
