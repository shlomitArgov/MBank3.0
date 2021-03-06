/**
 * 
 */
package mbank.database.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

import mbank.database.beans.enums.ActivityType;

/**
 * @author Shlomit Argov
 *
 */
public class Activity
{
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Activity [id=" + id + ", client_id=" + client_id + ", amount="
				+ amount + ", activity_date=" + activity_date + ", commission="
				+ commission + ", activityType=" + activityType
				+ ", description=" + description + "]\n";
	}

	public Activity()
	{
		// default constructor for JSP
	}

	private long  id;
	private long client_id;
	private double amount;
	private Date activity_date;
	private String activity_date_simple_format;
	private double commission;
	private ActivityType activityType;
	private String description;

	/**
	 * @param id - Activity unique id 
	 * @param client_id 
	 * @param amount 
	 * @param activity_date
	 * @param commission - commission charged for activity execution 
	 * @param description
	 */
	public Activity(long id, long client_id, double amount, Date activity_date,
			double commission, ActivityType activityType, String description)
	{
		super();
		this.id = id;
		this.client_id = client_id;
		this.amount = amount;
		this.activity_date = activity_date;
		this.commission = commission;
		this.description = description;
		this.activityType = activityType;
		updateActivityTypeSimpleFormat();
	}
	
	public Activity(long client_id, double amount, Date activity_date,
			double commission, ActivityType activityType, String description)
	{
		super();
		this.id = 0;
		this.client_id = client_id;
		this.amount = amount;
		this.activity_date = activity_date;
		this.commission = commission;
		this.description = description;
		this.activityType = activityType;
		updateActivityTypeSimpleFormat();
	}

	public long getClient_id()
	{
		return client_id;
	}

	public void setClient_id(long client_id)
	{
		this.client_id = client_id;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public Date getActivity_date()
	{
		return activity_date;
	}

	public void setActivity_date(Date activity_date)
	{
		this.activity_date = activity_date;
	}

	public double getCommission()
	{
		return commission;
	}

	public void setCommission(double commission)
	{
		this.commission = commission;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public long getId()
	{
		return id;
	}
	
	public void setActivityId(long id) {
		this.id = id;
	}

	public String getActivity_date_simple_format()
	{
		return activity_date_simple_format;
	}

	public void setActivity_date_simple_format(String activity_date_simple_format)
	{
		this.activity_date_simple_format = activity_date_simple_format;
	}
	
	public void updateActivityTypeSimpleFormat()
	{
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		activity_date_simple_format = df.format(activity_date);
	}
	
	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Activity)
		{
			if (((Activity)obj).getId() == this.id)
			{
				return true;
			}
		}
		return false;
	}	
}
