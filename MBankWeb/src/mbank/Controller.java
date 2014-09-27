package mbank;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class Controller
 */
@WebServlet(value = "/Controller", loadOnStartup = 1)

public class Controller extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private static final String COMMAND_PARAM = "command";

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// handle command
		String nextPage = null;
		String command = request.getParameter(COMMAND_PARAM);
		switch (command) 
		{
			case "":
			{
				nextPage = login();
				break;
			}
			case "account": 
			{
				nextPage = gotoAccount();
				break;
			}
			case "recent_activities": 
			{
				nextPage = gotoRecentActivities();
				break;
			}
			case "deposits": 
			{
				nextPage = gotoDeposits();
				break;
			}
			case "myDetails": 
			{
				nextPage = gotoMyDetails();
				break;
			}
			case "mbank_properties": 
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

	private String login() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	private String gotoMBankProperties() 
	{
		return "mbank_properties";	//next page
		
	}

	private String gotoMyDetails() 
	{
		return "my_details.jsp";	//next page
	}

	private String gotoDeposits() 
	{
		return "deposits.jsp";	//next page
	}

	private String gotoRecentActivities() 
	{
		return "recent_activities.jsp";	//next page
	}

	private String gotoAccount() 
	{
		return "account.jsp";	//next page
	}
}
