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

// Filters all requests to the servlet. All requests that do no contain valid ClientAction and session objects are redirected to the login page unless the
// command passed in the request is the login command
@WebFilter(urlPatterns = "/Controller")
public class RequestFilter implements Filter 
{   
	private static final String COMMAND_PARAM = "command";
	private static final String LOGIN_COMMAND_PARAM = "Login";
	
	private static final String APPLICATION_NAME = "/MBankWeb"; 
	private static final String CONTROLLER_PATH_ ="/Controller";
	private static final String LOGIN_PATH = "/index.jsp";
	
	private static final String CLIENT_ACTION_ATTR = "client_action";
	
    public RequestFilter() {
    	super();
    }
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		// TODO remove trace message
		System.out.println("RequestFilter.doFilter()");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false); // Do not create a new session if one does not already exist
		HttpServletResponse res = (HttpServletResponse) response;
		
		// login command
		if(req.getParameter(COMMAND_PARAM) != null && req.getParameter(COMMAND_PARAM).equals(LOGIN_COMMAND_PARAM))
		{
			// Forward to Controller for handling login action
			// TODO remove trace message
			System.out.println("Forwarding to the Controller for handling login action");
			request.getServletContext().getRequestDispatcher(CONTROLLER_PATH_).forward(request, response);
		}
		else
		{
			if(session != null)
			{
				if(req.getSession().getAttribute(CLIENT_ACTION_ATTR) != null)
				{
					// Valid session with ClientAction object saved in the session
					// Forward to Controller for handling login action
					// TODO remove trace message
					System.out.println("Forwarding to the Controller for handling login action");
					request.getServletContext().getRequestDispatcher(CONTROLLER_PATH_).forward(request, response);
				}
			}
			else
			{
				// No valid session or no ClientAction object saved in the session - redirect to login page
				// TODO remove trace message
				System.out.println("No valid session/ClientAction - redirecting to login page");
				res.sendRedirect(APPLICATION_NAME + LOGIN_PATH);
			}
		}
	}
			@Override
	public void destroy() {
		
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
