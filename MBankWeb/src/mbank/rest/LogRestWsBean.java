package mbank.rest;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import mbank.ejb.logging.persistence.Log;
import mbank.ejb.logging.persistence.LogDAORemote;

@Stateless
@Path("/")
public class LogRestWsBean 
{
	public LogRestWsBean(){}
	@EJB
	private LogDAORemote daoRemoteStub;
	
  @PostConstruct
    @WebMethod(exclude=true) //do not expose as web-method
    public void init(){
    	System.out.println("LogRestWsBean.init() # of logs in database is: " + daoRemoteStub.getAllLog().size());
    }
    
	
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllLogs")
	@WebResult(name="Log")
	@GET
	@POST
	public Log[] getAllLogs()
	{
		return daoRemoteStub.getAllLog().toArray(new Log[0]);
	}
	
	@GET
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getLogsByClientId/{clientId}")
	@WebResult(name="Log")
	public Log[] getLogsByClientId(@PathParam("clientId") Long clientId)
	{
		return daoRemoteStub.getLogsByClientId(clientId).toArray(new Log[0]);
	}
	
   @ApplicationPath("/rest")
   public static class RestApp extends Application
   {
      public RestApp(){}
   }
}
