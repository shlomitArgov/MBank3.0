package mbank;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mbank.actions.Action;
import mbank.exceptions.MBankException;
/**
 * Servlet implementation class Controller
 */
@WebServlet(value = "/Controller", loadOnStartup = 1)

public class Controller extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private static final String COMMAND_PARAM = "command";
	private static final String USERNAME_PARAM = "username";
	private static final String PASSWORD_PARAM = "password";
	
	private static final String LOGIN_COMMAND_PARAM = "Login";
	private static final String MY_DETAILS_COMMAND_PARAM = "myDetails";
	private static final String DEPOSITS_COMMAND_PARAM = "deposits";
	private static final String RECENT_ACTIVITIES_COMMAND_PARAM = "recent_activities";
	private static final String ACCOUNT_COMMAND_PARAM = "account";
	private static final String MBANK_PROPERTIES_COMMAND_PARAM = "mbank_properties";
	
	private static final String MY_DETAILS_JSP = "/my_details.jsp";
	private static final String DEPOSITS_JSP = "/deposits.jsp";
	private static final String RECENT_ACTIVITIES_JSP = "/recent_activities.jsp";
	private static final String ACCOUNT_JSP = "/account.jsp";
	private static final String INDEX_JSP = "/index.jsp";

	private static final String CLIENT_ACTION_ATTR = "client_action";
	private static final String ERROR_ATTR = "error";

	private static final String USERNAME_ATTR = "username";

	private MBank mbankInstance;


	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		mbankInstance = MBank.getInstance();
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// handle command
		String nextPage = null;
		String command = request.getParameter(COMMAND_PARAM);
		System.out.println("command = " + command);
		switch (command) 
		{
			case LOGIN_COMMAND_PARAM:
			{
				System.out.println("Controller.service()");
				nextPage = login(request);
				System.out.println("nextPage after login command is: " + nextPage);
				break;
			}
			case ACCOUNT_COMMAND_PARAM: 
			{
				nextPage = gotoAccount();
				break;
			}
			case RECENT_ACTIVITIES_COMMAND_PARAM: 
			{
				nextPage = gotoRecentActivities();
				break;
			}
			case DEPOSITS_COMMAND_PARAM: 
			{
				nextPage = gotoDeposits();
				break;
			}
			case MY_DETAILS_COMMAND_PARAM: 
			{
				nextPage = gotoMyDetails();
				break;
			}
			case MBANK_PROPERTIES_COMMAND_PARAM: 
			{
				nextPage = gotoMBankProperties();
				break;
			}
			default:
				break;
		}
		// forward the request
		this.getServletContext().getRequestDispatcher(nextPage).forward(request, response);
	}

	private String login(HttpServletRequest request) 
	{
		// When logging in it is expected that no session exists (the index.jsp is forwarded to the account
		// page is a session already exists
		
		// Create a new session
		request.getSession(true);
		
		Action clientAction = null;
		
		String username = request.getParameter(USERNAME_PARAM);
		String password = request.getParameter(PASSWORD_PARAM);
		String error = null;
		
		// attempt to perform login with the provided credentials
		try 
		{
			clientAction = mbankInstance.login(username, password);
		} catch (MBankException e) 
		{
			error = e.getLocalizedMessage();
			request.setAttribute(ERROR_ATTR, error);
			request.getSession().invalidate();
			return INDEX_JSP;
		}
		
		if(clientAction != null)
		{
			// save the ClientAction object in the session
			request.getSession().setAttribute(CLIENT_ACTION_ATTR, clientAction);
			request.getSession().setAttribute(USERNAME_ATTR, username);
			System.out.println("Controller.login()");
			System.out.println("going to: " + ACCOUNT_JSP);
			return ACCOUNT_JSP;
		}
		else
		{
			request.getSession().invalidate();
		}
		request.setAttribute(ERROR_ATTR, "Failed to create client action");
		return INDEX_JSP;
	}

	private String gotoMBankProperties() 
	{
		return MBANK_PROPERTIES_COMMAND_PARAM;	//next page
		
	}

	private String gotoMyDetails() 
	{
		return MY_DETAILS_JSP;	//next page
	}

	private String gotoDeposits() 
	{
		return DEPOSITS_JSP;	//next page
	}

	private String gotoRecentActivities() 
	{
		return RECENT_ACTIVITIES_JSP;	//next page
	}

	private String gotoAccount() 
	{
		return ACCOUNT_JSP;	//next page
	}
}
