package mbank;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbank.actions.Action;
import mbank.actions.ClientAction;
import mbank.database.beans.Account;
import mbank.database.beans.Client;
import mbank.database.beans.Property;
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
	private static final String WITHDRAW_COMMAND_PARAM = "withdraw";
	private static final String SYSTEM_PROPERTIES_COMMAND_PARAM = "system_properties";
	private static final String DEPOSIT_COMMAND_PARAM = "deposit";
	private static final String MBANK_PROPERTIES_COMMAND_PARAM = "mbank_properties";
	private static final String LOGOUT_COMMAND_PARAM = "logout";

	
	private static final String MY_DETAILS_JSP = "/my_details.jsp";
	private static final String DEPOSITS_JSP = "/deposits.jsp";
	private static final String RECENT_ACTIVITIES_JSP = "/recent_activities.jsp";
	private static final String ACCOUNT_JSP = "/account.jsp";
	private static final String INDEX_JSP = "/index.jsp";
	private static final String MBANK_PROPERTIES_JSP = "/mbank_properties.jsp";

	private static final String CLIENT_ACTION_ATTR = "client_action";
	private static final String ERROR_ATTR = "error";
	private static final String USERNAME_ATTR = "client_name";
	private static final String ACCOUNT_ATTR = "account";
	private static final String WITHDROW_AMMOUNT_PARAM = "withdraw_ammount";
	private static final String CLIENT_ACTIVITIES_ATTR = "client_activities";
	private static final String CLIENT_ATTR = "client";

	private static final String MBABK_INSTANCE_ATTR = "mbank_instance";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//TODO move the mbank instance to the application instance
		MBank mbankInstance = MBank.getInstance();
		this.getServletContext().setAttribute(MBABK_INSTANCE_ATTR, mbankInstance);
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
			System.out.println("Controller.service()");
			// handle command
			String nextPage = null;
			String command = request.getParameter(COMMAND_PARAM);
			System.out.println("command = " + command);

			if (command == null)
				{
					nextPage = ACCOUNT_JSP; // default (there should always
											// be a valid session at this
											// point)
				} else
				{
					switch (command)
						{
						case LOGIN_COMMAND_PARAM:
							{
								try
								{
									nextPage = login(request);
								} catch (MBankException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out
										.println("nextPage after login command is: "
												+ nextPage);
								break;
							}
						case ACCOUNT_COMMAND_PARAM:
							{
								try
								{
									nextPage = gotoAccount(request);
								} catch (MBankException e)
								{
									// TODO Auto-generated catch block
									
								}
								break;
							}
						case WITHDRAW_COMMAND_PARAM:
						{
							//TODO
							nextPage = withdrawFromAccount(request);
							break;
						}
						case DEPOSIT_COMMAND_PARAM:
						{
							//TODO
							nextPage = depositToAccount(request);
							break;
						}
						case RECENT_ACTIVITIES_COMMAND_PARAM:
							{
								nextPage = gotoRecentActivities(request);
								break;
							}
						case DEPOSITS_COMMAND_PARAM:
							{
								//TODO
								nextPage = gotoDeposits();
								break;
							}
						case MY_DETAILS_COMMAND_PARAM:
							{
								try
								{
									nextPage = gotoMyDetails(request);
								} catch (MBankException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								break;
							}
						case MBANK_PROPERTIES_COMMAND_PARAM:
							{
								nextPage = gotoMBankProperties(request);
								break;
							}
						case LOGOUT_COMMAND_PARAM:
							{
								nextPage = logout(request);
								break;
							}
						default:
							{
								nextPage = ACCOUNT_JSP;
								break;
							}
						}
				}
// forward the request
	this.getServletContext().getRequestDispatcher(nextPage).forward(request, response);
	}

	private String depositToAccount(HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private String withdrawFromAccount(HttpServletRequest request)
	{
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		double withdrawAmount = 0;
		try
		{
			withdrawAmount = Double.parseDouble(request.getParameter(WITHDROW_AMMOUNT_PARAM));
		}
		catch(NumberFormatException e)
		{
			request.getSession().setAttribute(ERROR_ATTR, "Invalid input, withdrawal amount must be a number");
		}
		try
		{
			clientAction.withdrawFromAccount(withdrawAmount);
		} catch (MBankException e)
		{
			request.getSession().setAttribute(ERROR_ATTR, e.getLocalizedMessage());
			e.printStackTrace();
		}
		return ACCOUNT_JSP; 
	}

	private String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return INDEX_JSP;
	}

	private String login(HttpServletRequest request) throws MBankException 
	{
		// When logging in it is expected that no session exists (the index.jsp is forwarded to the account
		// page is a session already exists
		
		// Create a new session
		request.getSession(true);
		
		Action clientAction = null;
		String username = request.getParameter(USERNAME_PARAM);
		String password = request.getParameter(PASSWORD_PARAM);
		String error = null;
		//TODO validations on username/password
		request.getSession().setAttribute(USERNAME_ATTR, username);
		// attempt to perform login with the provided credentials
		try 
		{
			MBank mbank = (MBank) this.getServletContext().getAttribute(MBABK_INSTANCE_ATTR);
			clientAction = mbank.login(username, password);
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
//			return ACCOUNT_JSP;
			return gotoAccount(request);
		}
		else
		{
			request.getSession().invalidate();
		}
		request.setAttribute(ERROR_ATTR, "Failed to create client action");
		return INDEX_JSP;
	}

	private String gotoMBankProperties(HttpServletRequest request) 
	{
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		List<Property> system_properties = null;
		try
		{
			system_properties = clientAction.viewSystemProperties();
			request.getSession().setAttribute(SYSTEM_PROPERTIES_COMMAND_PARAM, system_properties);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MBANK_PROPERTIES_JSP;	//next page
		
	}

	private String gotoMyDetails(HttpServletRequest request) throws MBankException 
	{
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		Client client = clientAction.viewClientDetails();
		request.getSession().setAttribute(CLIENT_ATTR, client);
		return MY_DETAILS_JSP;	//next page
	}

	private String gotoDeposits() 
	{
		return DEPOSITS_JSP;	//next page
	}

	private String gotoRecentActivities(HttpServletRequest request) 
	{
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		try
		{
			request.getSession().setAttribute(CLIENT_ACTIVITIES_ATTR, clientAction.viewClientActivities());
		} catch (MBankException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return RECENT_ACTIVITIES_JSP;	//next page
	}

	private String gotoAccount(HttpServletRequest request) throws MBankException 
	{
		System.out.println("Controller.gotoAccount()");
		// Get ClientAction object from the session
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		Account account = clientAction.viewAccountDetails();
		System.out.println("account_id = " + account.getAccount_id());
		request.getSession().setAttribute(ACCOUNT_ATTR, account);
		
		return ACCOUNT_JSP;	//next page
	}
}
