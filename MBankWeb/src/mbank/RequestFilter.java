package mbank;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class RequestFiler
 */
@WebFilter(urlPatterns = "/Controller")
public class RequestFilter implements Filter 
{   
	private static final String COMMAND_PARAM = "command";
	private static final String LOGIN_COMMAND_PARAM = "Login";
	
	private static final String APPLICATION_NAME = "/MBankWeb"; 
	private static final String CONTROLLER_PATH_ ="/Controller";
	private static final String LOGIN_PATH = "/index.jsp";
	private static final String CLIENT_ACTION_ATTR = "client_action";
	/**
     * Default constructor. 
     */
    public RequestFilter() {
    	super();
    }
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		System.out.println("RequestFilter.doFilter()");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		HttpServletResponse res = (HttpServletResponse) response;
		
		// login command
		if(req.getParameter(COMMAND_PARAM) != null && req.getParameter(COMMAND_PARAM).equals(LOGIN_COMMAND_PARAM))
		{
			// Forward to Controller for handling login action
			System.out.println("Forwarding to the Controller for handling login action");
			request.getServletContext().getRequestDispatcher(CONTROLLER_PATH_).forward(request, response);
		}
		else
		{
			// no valid session or no ClientAction object saved in the session - redirect to login page
			if(session == null || req.getSession().getAttribute(CLIENT_ACTION_ATTR) == null)
			{
				System.out.println("No valid session - redirecting to login page");
				res.sendRedirect(APPLICATION_NAME + LOGIN_PATH);
			}
			// valid session with ClientAction object saved in the session
			else
			{
				// Forward to Controller for handling login action
				System.out.println("Forwarding to the Controller for handling login action");
				request.getServletContext().getRequestDispatcher(CONTROLLER_PATH_).forward(request, response);
			}
		}
//				{
//					System.out.println(req.getParameter(COMMAND_PARAM));
//					if(req.getParameter(COMMAND_PARAM) != null && req.getParameter(COMMAND_PARAM).equals(LOGIN_COMMAND_PARAM))
//					{
//						// Forward to Controller for handling login action
//						System.out.println("Forwarding to the Controller for handling login action");
//						request.getServletContext().getRequestDispatcher(CONTROLLER_PATH_).forward(request, response);
//					}
//		}
		
//		if(session == null)
//		{
//			System.out.println(req.getParameter(COMMAND_PARAM));
//			if(req.getParameter(COMMAND_PARAM) != null && req.getParameter(COMMAND_PARAM).equals(LOGIN_COMMAND_PARAM))
//			{
//				// Forward to Controller for handling login action
//				System.out.println("Forwarding to the Controller for handling login action");
//				request.getServletContext().getRequestDispatcher(CONTROLLER_PATH_).forward(request, response);
//			}
//			else
//			{
//				// No valid session exists - redirect to the login page
//				System.out.println("No valid session - redirecting to login page");
//				res.sendRedirect(APPLICATION_NAME + LOGIN_PATH);
//			}
//		}
//		else
//		{
//			// A valid session exists
//			//TODO check that there is a Valid clientAction object in the session
//			// Forward to the Controller for handling
//			System.out.println("forwarding to Controller for handling");
//			request.getServletContext().getRequestDispatcher(CONTROLLER_PATH_).forward(request, response);
//		}
	}
			@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}


}
