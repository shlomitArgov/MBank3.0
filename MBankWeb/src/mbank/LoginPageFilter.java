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
 * Servlet Filter implementation class LoginPageFilter
 */
@WebFilter("/index.jsp")
public class LoginPageFilter implements Filter 
{
    private static final String ACCOUNT_JSP = "/account.jsp";
	private static final String APPLICATION_NAME = "/MBankWeb"; 

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
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		System.out.println("LoginPageFilter.doFilter()");
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession(false);

		// If a valid session already exists and reached this filter (url is index.jsp)
		if(session != null)
		{

			System.out.println("Valid session exists - forwarding to " + APPLICATION_NAME + ACCOUNT_JSP);
			req.getRequestDispatcher("/Controller?command=account.jsp");
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
