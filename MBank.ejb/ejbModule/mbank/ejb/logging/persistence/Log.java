/**
 * 
 */
package mbank.ejb.logging.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Shlomit Argov
 *
 */

@Entity
@Table(name = "Logs")
@NamedQueries({
	  @NamedQuery(
	    name="LogGetAll",
	    query="SELECT l FROM Log AS l ORDER BY l.timestamp DESC"
	  ),
	  @NamedQuery(
	    name="LogGetByClientId",
	    query="SELECT l FROM Log AS l WHERE l.clientId = :clientId ORDER BY l.timestamp DESC"
	  )
	})
@XmlRootElement(name = "Logs")
public class Log implements Serializable //serializable in order to support sending objects from remote EJB container
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

	public void setClientId(Long clientId)
	{
		this.clientId = clientId;
	}

	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}
	
	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(Date timestamp)
	{
		this.timestamp = timestamp;
	}
}
