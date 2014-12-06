package mbank.ejb.logging.ws;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import mbank.ejb.logging.persistence.Log;
import mbank.ejb.logging.persistence.LogDAORemote;

@Stateless
@WebService
public class LogWsdlWsBean
{
	@EJB
	private LogDAORemote daoLocalStub;
	
	@WebMethod
	@WebResult (name = "Log")
	public Log[] getAllLogs()
	{
		return daoLocalStub.getAllLog().toArray(new Log[0]);
	}
	
	@WebMethod
	@WebResult (name = "Log")
	public Log[] getLogsByClientId(@WebParam(name = "clientId") Long clientId)
	{
		return daoLocalStub.getLogsByClientId(clientId).toArray(new Log[0]);
	}
}
