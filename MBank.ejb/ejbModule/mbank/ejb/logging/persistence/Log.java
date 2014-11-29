/**
 * 
 */
package mbank.ejb.logging.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Local;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Shlomit Argov
 *
 */
@Entity
@NamedQueries({
	  @NamedQuery(
	    name="getAllLogs",
	    query="SELECT l FROM Ship Log AS l ORDER BY l.timestamp DESC"
	  ),
	  @NamedQuery(
	    name="getLogById",
	    query="SELECT l FROM Ship Log AS l WHERE l.id = :id ORDER BY l.timestamp DESC"
	  )
	})
public class Log implements Serializable
{
	@Override
	public String toString()
	{
		return "Log [id=" + id + ", clientId=" + clientId + ", operation=" + operation + ", timestamp=" + timestamp + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@GeneratedValue
	private Long id;
	private Long clientId;
	private String operation;
	private Date timestamp;
	
	public Log(){}; // Default constructor 
	
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
	
	public String getOperation()
	{
		return operation;
	}
}
