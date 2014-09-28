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
	private static final String CONTROLLER_SERVLET ="/Controller";
	private static final String LOGIN_PATH = "/index.jsp";
	private static final String ACCOUNT_JSP = "/account.jsp";

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
		HttpSession session = req.getSession(true);
		HttpServletResponse res = (HttpServletResponse) response;
		
		if(session.isNew())
		{
			// make sure the only command that is passed to the controller for a new
			// session is the login command
			if(request.getParameter(COMMAND_PARAM).equals(LOGIN_COMMAND_PARAM))
			{
				System.out.println("forwarding to Controller - attempting to login");
				// forward to Controller servlet to try and perform login
				request.getServletContext().getRequestDispatcher(CONTROLLER_SERVLET).forward(request, response);
			}
			else
			{
				// trying to access an internal command without a valid session - redirect to  login page
				System.out.println("Tried to access internal command without active session");
				session.invalidate();
				res.sendRedirect(APPLICATION_NAME + LOGIN_PATH);
			}
		}
		else
		{
			// a valid session already exists - redirect to the Account page
			System.out.println("RequestFilter.doFilter()");
			System.out.println("Valid session exists - doing nothing");
//			res.sendRedirect(APPLICATION_NAME + ACCOUNT_JSP);
		}
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
