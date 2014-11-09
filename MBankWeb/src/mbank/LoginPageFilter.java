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
import javax.servlet.http.HttpSession;

@WebFilter("/index.jsp")
public class LoginPageFilter implements Filter 
{
    private static final String ACCOUNT_JSP = "/account.jsp";
	private static final String APPLICATION_NAME = "/MBankWeb";
	private static final String CLIENT_ACTION_ATTR = "client_action";
	private static final String FORWARD_PATH = "/Controller?command=account"; 

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	// When valid session and clientAction objects already exist, forward requests to the login page to the default landing page (ACCOUNT_JSP) 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		// TODO remove trace message
		System.out.println("LoginPageFilter.doFilter()");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession(false);

		// If a valid session already exists and reached this filter (url is index.jsp) and the session context already contains a valid clientAction attribute - 
		// forward to the default landing page (ACCOUNT_JSP)
		if(session != null && req.getSession().getAttribute(CLIENT_ACTION_ATTR) != null)
		{
			// TODO remove trace message
			System.out.println("Valid session exists - forwarding to " + APPLICATION_NAME + ACCOUNT_JSP);
			req.getRequestDispatcher(FORWARD_PATH).forward(request, response);
		}
		else
		{
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}
	
	/**
     * Default constructor. 
     */
    public LoginPageFilter() 
    {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() 
	{
	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}
}
