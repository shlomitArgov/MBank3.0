/**
 * 
 */
package mbank.database.beans;

import java.util.Date;

import mbank.database.beans.enums.DepositType;

/**
 * @author Shlomit Argov
 *
 */
public class Deposit
{
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Deposit [deposit_id=" + deposit_id + ", client_id=" + client_id
				+ ", balance=" + balance + ", type=" + type
				+ ", estimated_balance=" + estimated_balance
				+ ", opening_date=" + opening_date + ", closing_date="
				+ closing_date + "]";
	}

	private long deposit_id;
	private long client_id;
	private double balance;
	private DepositType type;
	private double estimated_balance;
	private Date opening_date;
	private Date closing_date;

	public Deposit(long deposit_id, long client_id, double balance,
			DepositType type, double estimated_balance, Date opening_date,
			Date closing_date)
	{
		this.deposit_id = deposit_id;
		this.client_id = client_id;
		this.balance = balance;
		this.type = type;
		this.estimated_balance = estimated_balance;
		this.opening_date = opening_date;
		this.closing_date = closing_date;
	}
	
	public Deposit(long client_id, double balance,
			DepositType type, double estimated_balance, Date opening_date,
			Date closing_date)
	{
		this.deposit_id = 0;
		this.client_id = client_id;
		this.balance = balance;
		this.type = type;
		this.estimated_balance = estimated_balance;
		this.opening_date = opening_date;
		this.closing_date = closing_date;
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

	public DepositType getType()
	{
		return type;
	}

	public void setType(DepositType type)
	{
		this.type = type;
	}

	public double getEstimated_balance()
	{
		return estimated_balance;
	}

	public void setEstimated_balance(double estimated_balance)
	{
		this.estimated_balance = estimated_balance;
	}

	public Date getOpening_date()
	{
		return opening_date;
	}

	public void setOpening_date(Date opening_date)
	{
		this.opening_date = opening_date;
	}

	public Date getClosing_date()
	{
		return closing_date;
	}

	public void setClosing_date(Date closing_date)
	{
		this.closing_date = closing_date;
	}

	public long getDeposit_id()
	{
		return deposit_id;
	}
	
	public void setDeposit_id(long deposit_id) {
		this.deposit_id = deposit_id;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Deposit)
		{
			if (((Deposit)obj).getDeposit_id() == this.deposit_id &&
					((Deposit)obj).getBalance() == this.balance &&
					((Deposit)obj).getClient_id()== this.client_id &&
					((Deposit)obj).getEstimated_balance() == this.estimated_balance &&
					((Deposit)obj).getType().getTypeStringValue().equals(this.type.getTypeStringValue()));
			{
				return true;
			}		
		}
		return false;
	}
}

