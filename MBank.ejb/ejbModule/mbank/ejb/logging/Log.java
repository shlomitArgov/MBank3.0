/**
 * 
 */
package mbank.ejb.logging;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Local;
import javax.persistence.Entity;

/**
 * @author Shlomit Argov
 *
 */
//@Entity
//@Local
public class Log implements Serializable
{
	private Long id;
	private Long clientId;
	private String operation;
	private Date timestamp;
	
	public Log(Long clientId, String operation)
	{
		this.clientId = clientId;
		this.operation = operation;
		this.timestamp = new Date();
	}
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getClientId()
	{
		return clientId;
	}
}
