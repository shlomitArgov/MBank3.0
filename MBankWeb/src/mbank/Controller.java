package mbank;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbank.actions.Action;
import mbank.actions.ClientAction;
import mbank.actions.TableValue;
import mbank.database.beans.Account;
import mbank.database.beans.Client;
import mbank.database.beans.Deposit;
import mbank.database.beans.Property;
import mbank.database.beans.enums.ClientAttributes;
import mbank.database.beans.enums.SystemProperties;
import mbank.exceptions.MBankException;

/**
 * Servlet implementation class Controller
 */
@WebServlet(value = "/Controller", loadOnStartup = 1)
public class Controller extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private static final String COMMAND_PARAM = "command";
	private static final String LOGIN_COMMAND_PARAM = "Login";
	private static final String MY_DETAILS_COMMAND_PARAM = "my_details";
	private static final String UPDATE_CLIENT_DETAILS_PARAM = "updateClientDetails";
	private static final String DEPOSITS_COMMAND_PARAM = "my_deposits";
	private static final String RECENT_ACTIVITIES_COMMAND_PARAM = "my_recent_activities";
	private static final String ACCOUNT_COMMAND_PARAM = "my_account";
	private static final String WITHDRAW_COMMAND_PARAM = "withdraw";
	private static final String DEPOSIT_COMMAND_PARAM = "deposit";
	private static final String CREATE_DEPOSIT_COMMAND_PARAM = "createDeposit";
	private static final String MBANK_PROPERTIES_COMMAND_PARAM = "mbank_properties";
	private static final String PRE_OPEN_DEPOSIT_COMMAND_PARAM = "preOpenDeposit";
	private static final String LOGOUT_COMMAND_PARAM = "logout";
	private static final String UPDATED_CLIENT_ADDRESS_PARAM = "client_address";
	private static final String UPDATED_CLIENT_EMAIL_PARAM = "client_email";
	private static final String UPDATED_CLIENT_PHONE_PARAM = "client_phone";
	private static final String DEPOSIT_INITIAL_AMOUNT_PARAM = "initial_deposit_amount";
	private static final String DEPOSIT_CLOSING_DATE_PARAM = "deposit_end_date";
	private static final String DEPOSIT_ID_PARAM = "depositId";
	
	private static final String USERNAME_PARAM = "username";
	private static final String PASSWORD_PARAM = "password";	


	private static final String MY_DETAILS_JSP = "/my_details.jsp";
	private static final String DEPOSITS_JSP = "/my_deposits.jsp";
	private static final String MY_RECENT_ACTIVITIES_JSP = "/my_recent_activities.jsp";
	private static final String ACCOUNT_JSP = "/my_account.jsp";
	private static final String INDEX_JSP = "/index.jsp";
	private static final String MBANK_PROPERTIES_JSP = "/mbank_properties.jsp";
	private static final String PAGE_NOT_FOUND_HTML = "/pageNotFound.html";

	private static final String CLIENT_ACTION_ATTR = "client_action";
	private static final String ERROR_ATTR = "error";
	private static final String USERNAME_ATTR = "client_name";
	private static final String ACCOUNT_ATTR = "account";
	private static final String WITHDROW_AMMOUNT_PARAM = "withdraw_amount";
	private static final String CLIENT_ACTIVITIES_ATTR = "client_activities";
	private static final String DEPOSITS_LIST_ATTR = "client_deposits";
	private static final String CLIENT_ATTR = "client";
	private static final String MBABK_INSTANCE_ATTR = "mbank_instance";
	private static final String WITHDRAW_ERROR_ATTR = "withdraw_error";
	private static final String WITHDRAW_INFO_ATTR = "withdraw_info";
	private static final String WITHDRAWAL_COMMISSION_ATTR = "commission";
	private static final String DEPOSIT_AMOUNT_PARAM = "deposit_amount";
	private static final String DEPOSIT_INFO_ATTR = "deposit_info";
	private static final String DEPOSIT_ERROR_ATTR = "deposit_error";
	private static final String SYSTEM_PROPERTIES_ATTR = "system_properties";
	private static final String CREATE_DEPOSIT_ERROR_ATTR = "create_deposit_error";
	private static final String CREATE_DEPOSIT_INFO_ATTR = "create_deposit_info";
	private static final String CREATE_DEPOSIT_AMOUNT_ERROR_ATTR = "deposit_amount_error";
	private static final String CREATE_DEPOSIT_END_DATE_ERROR_ATTR = "deposit_end_date_error";
	private static final String DEPOSIT_ID_ERROR_ATTR = "deposit_id_error";
	private static final String PRE_OPEN_DEPOSIT_ERROR_ATTR = "pre_open_deposit_error";
	private static final String PRE_OPEN_DEPOSIT_INFO_ATTR = "pre_open_deposit_info";
	private static final String UPDATE_CLIENT_DETAILS_ERROR_ATTR = "update_client_details_error";

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		MBank mbankInstance = MBank.getInstance();
		// Create an instance of MBank and save it in the application context
		this.getServletContext().setAttribute(MBABK_INSTANCE_ATTR, mbankInstance);
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// At this point the filter method doFilter of the Request filter has
		// already run so we can be assured that a valid session
		// and LoginAction objects exist

		System.out.println("Controller.service()");
		// Handle command
		String nextPage = null;
		String command = request.getParameter(COMMAND_PARAM);
		
		if (command == null)
		{
			nextPage = PAGE_NOT_FOUND_HTML; // unknown command -> forward to 404
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
						// This case is not possible via intended use of the application.
						// It can be reached if the GET request is manually edited to pass the login action while a valid session is taking place
						nextPage = PAGE_NOT_FOUND_HTML;
					}
					break;
				}
				case ACCOUNT_COMMAND_PARAM:
				{
					try
					{
						nextPage = gotoMyAccount(request);
					} catch (MBankException e)
					{
					}
					break;
				}
				case WITHDRAW_COMMAND_PARAM:
				{
					try
					{
						nextPage = withdrawFromAccount(request);
					} catch (MBankException e)
					{
					}
					break;
				}
				case DEPOSIT_COMMAND_PARAM:
				{
					try
					{
						nextPage = depositToAccount(request);
					} catch (MBankException e)
					{
					}
					break;
				}
				case RECENT_ACTIVITIES_COMMAND_PARAM:
				{
					nextPage = gotoMyRecentActivities(request);
					break;
				}
				case DEPOSITS_COMMAND_PARAM:
				{
					nextPage = gotoMyDeposits(request);
					break;
				}
				case CREATE_DEPOSIT_COMMAND_PARAM:
				{
					nextPage = createNewDeposit(request);
					break;
				}
				case PRE_OPEN_DEPOSIT_COMMAND_PARAM:
				{
					nextPage = preOpenDeposit(request);
					break;
				}
				case MY_DETAILS_COMMAND_PARAM:
				{
					try
					{
						nextPage = gotoMyDetails(request);
					} catch (MBankException e)
					{
					}
					break;
				}
				case UPDATE_CLIENT_DETAILS_PARAM:
				{
					try
					{
						nextPage = updateClientDetails(request);
					} catch (MBankException e)
					{
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
					nextPage = PAGE_NOT_FOUND_HTML;
					break;
				}
			}
		}
		// forward the request
		this.getServletContext().getRequestDispatcher(nextPage).forward(request, response);
	}

	private String preOpenDeposit(HttpServletRequest request)
	{
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		String depositIdString = request.getParameter(DEPOSIT_ID_PARAM);
		long depositId = 0;
		try
		{
			if(depositIdString.trim().isEmpty())
			{
				throw new MBankException("Deposit ID cannot be empty");
			}
			depositId = Long.parseLong(depositIdString);			
		}catch (NumberFormatException e)
		{
			request.setAttribute(DEPOSIT_ID_ERROR_ATTR, "Deposit ID must be a whole number");
			return gotoMyDeposits(request);
		}
		catch (MBankException e) 
		{
			request.setAttribute(DEPOSIT_ID_ERROR_ATTR, e.getLocalizedMessage());
			return gotoMyDeposits(request);		
		}
		try
		{
			System.out.println("parsed depositID: " + depositId);
			clientAction.preOpenDeposit(depositId);
			request.setAttribute(PRE_OPEN_DEPOSIT_INFO_ATTR, "Deposit pre-opened successfully");
			return gotoMyDeposits(request);
		} catch (MBankException e)
		{
			request.setAttribute(PRE_OPEN_DEPOSIT_ERROR_ATTR, e.getLocalizedMessage());
			e.printStackTrace();
			gotoMyDeposits(request);
		}
		return gotoMyDeposits(request);
	}

	private String createNewDeposit(HttpServletRequest request)
	{
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		String amount = request.getParameter(DEPOSIT_INITIAL_AMOUNT_PARAM);
		String closingDate = request.getParameter(DEPOSIT_CLOSING_DATE_PARAM);

		double depositAmount = 0;
		try
		{
			depositAmount = validatePositiveDouble(amount);
		} catch (MBankException e1)
		{
			request.setAttribute(CREATE_DEPOSIT_AMOUNT_ERROR_ATTR, e1.getLocalizedMessage());
			e1.printStackTrace();
		}
		Date closeDate;
		try
		{
			System.out.println("Controller.createNewDeposit() - closingDate string: " + closingDate);
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			df.setLenient(false);
			closeDate = df.parse(closingDate);
			System.out.println("Controller.createNewDeposit()\ncloseDate: " + closeDate);
			clientAction.createNewDeposit(depositAmount, closeDate);
			request.setAttribute(CREATE_DEPOSIT_INFO_ATTR, "Deposit created successfuly");
		} catch (ParseException e)
		{
			request.setAttribute(CREATE_DEPOSIT_END_DATE_ERROR_ATTR, "Invalid date: End-date must be in the format: dd-MM-yyyy and be a real date");
		} catch (MBankException e)
		{
			request.setAttribute(CREATE_DEPOSIT_ERROR_ATTR, e.getLocalizedMessage());
			e.printStackTrace();
		}
		return gotoMyDeposits(request);
	}

	private String updateClientDetails(HttpServletRequest request) throws MBankException
	{
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		
		String updatedAddress = request.getParameter(UPDATED_CLIENT_ADDRESS_PARAM);
		String updatedEmail = request.getParameter(UPDATED_CLIENT_EMAIL_PARAM);
		String updatedPhone = request.getParameter(UPDATED_CLIENT_PHONE_PARAM);
		
		// Set null to empty properties in order for them not to be updated
		if(updatedAddress.trim().isEmpty())
		{
			updatedAddress = null;
		}
		if(updatedEmail.trim().isEmpty())
		{
			updatedEmail = null;
		}
		if(updatedPhone.trim().isEmpty())
		{
			updatedPhone = null;
		}
		
		if(updatedAddress == null && updatedEmail == null && updatedPhone == null)
		{// Nothing to update
			request.setAttribute(UPDATE_CLIENT_DETAILS_ERROR_ATTR, "At least one detail must be non-empty");
			return gotoMyDetails(request);
		}
		
		TableValue addressDetails = new TableValue(ClientAttributes.ADDRESS.getAttribute(), updatedAddress);
		TableValue emailDetails = new TableValue(ClientAttributes.EMAIL.getAttribute(), updatedEmail);
		TableValue phoneDetails = new TableValue(ClientAttributes.PHONE.getAttribute(), updatedPhone);
		
		clientAction.updateClientDetails(addressDetails, emailDetails, phoneDetails);
		
//		/* Update client bean in the request */
//		Client client = clientAction.viewClientDetails();
//		System.out.println("Controller.gotoUpdateClientDetails()\nclientAddress: " + client.getAddress());
//		request.setAttribute(CLIENT_ATTR, client);
		
		return gotoMyDetails(request);
	}

	private String depositToAccount(HttpServletRequest request) throws MBankException
	{
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		String inputAmount = request.getParameter(DEPOSIT_AMOUNT_PARAM);
		double depositAmount = 0;
		try
		{
			depositAmount = validatePositiveDouble(inputAmount);
		} catch (MBankException e)
		{
			String error = e.getLocalizedMessage();
			request.setAttribute(DEPOSIT_ERROR_ATTR, error);
		}
		if (request.getAttribute(DEPOSIT_ERROR_ATTR) == null)
		{
			try
			{
				clientAction.depositToAccount(depositAmount);
				request.setAttribute(DEPOSIT_INFO_ATTR, "Deposit executed successfuly");
			} catch (MBankException e)
			{
				request.setAttribute(DEPOSIT_ERROR_ATTR, e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
		setCommissionRateInRequest(request, clientAction);

		// refresh the account.jsp page (account balance has changed)
		return gotoMyAccount(request);
	}

	private String withdrawFromAccount(HttpServletRequest request) throws MBankException
	{
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		String inputAmount = request.getParameter(WITHDROW_AMMOUNT_PARAM);
		double withdrawAmount = 0;
		try
		{
			withdrawAmount = validatePositiveDouble(inputAmount);
		} catch (MBankException e)
		{
			String error = e.getLocalizedMessage();
			request.setAttribute(WITHDRAW_ERROR_ATTR, error);
		}
		if (request.getAttribute(WITHDRAW_ERROR_ATTR) == null)
		{
			try
			{
				clientAction.withdrawFromAccount(withdrawAmount);
				request.setAttribute(WITHDRAW_INFO_ATTR, "Withdrawal executed successfuly");
			} catch (MBankException e)
			{
				request.setAttribute(WITHDRAW_ERROR_ATTR, e.getLocalizedMessage());
			}
		}
		setCommissionRateInRequest(request, clientAction);

		// refresh the account.jsp page (account balance has changed)
		return gotoMyAccount(request);
	}

	private double validatePositiveDouble(String amount) throws MBankException
	{
		double amountValue = 0;
		if (amount.trim().isEmpty())
		{
			throw new MBankException("Amount cannot be empty");
		}
		try
		{
			amountValue = Double.parseDouble(amount);
		} catch (NumberFormatException e)
		{
			throw new MBankException("Amount must be a number\n");
		}
		if (amountValue < 0)
		{
			throw new MBankException("Amount must be non-negative\n");
		}
		return amountValue;
	}

	private String logout(HttpServletRequest request)
	{
		request.getSession().invalidate();
		return INDEX_JSP;
	}

	private String login(HttpServletRequest request) throws MBankException
	{
		// When logging in it is expected that no session exists (the index.jsp
		// is forwarded to the account
		// page is a session already exists

		// Create a new session
		request.getSession(true);

		Action clientAction = null;
		String username = request.getParameter(USERNAME_PARAM);
		String password = request.getParameter(PASSWORD_PARAM);
		String error = null;
		// TODO validations on username/password
		request.getSession().setAttribute(USERNAME_ATTR, username);
		// attempt to perform login with the provided credentials
		try
		{
			MBank mbank = (MBank) this.getServletContext().getAttribute(MBABK_INSTANCE_ATTR);
			clientAction = mbank.login(username, password);
		} catch (MBankException e)
		{
			// login failed
			error = e.getLocalizedMessage();
			request.setAttribute(ERROR_ATTR, error);
			request.getSession().invalidate();
			return INDEX_JSP;
		}

		if (clientAction != null)
		{
			// save the ClientAction object in the session
			request.getSession().setAttribute(CLIENT_ACTION_ATTR, clientAction);
			request.getSession().setAttribute(USERNAME_ATTR, username);
			System.out.println("Controller.login()");
			System.out.println("going to: " + ACCOUNT_JSP);
			setCommissionRateInRequest(request, clientAction);
			return gotoMyAccount(request);
		} else
		{
			request.getSession().invalidate();
		}
		request.setAttribute(ERROR_ATTR, "Failed to create client action");
		return INDEX_JSP;
	}

	public void setCommissionRateInRequest(HttpServletRequest request, Action clientAction)
	{
		try
		{
			String commissionRate = clientAction.viewSystemProperty(SystemProperties.COMMISSION_RATE.getPropertyName());
			request.setAttribute(WITHDRAWAL_COMMISSION_ATTR, commissionRate);
			System.out.println(commissionRate);
		} catch (MBankException e1)
		{
		}
	}

	private String gotoMBankProperties(HttpServletRequest request)
	{
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		List<Property> system_properties = null;
		try
		{
			system_properties = clientAction.viewSystemProperties();
			request.setAttribute(SYSTEM_PROPERTIES_ATTR, system_properties);
		} catch (Exception e)
		{
		}
		return MBANK_PROPERTIES_JSP; // next page

	}

	private String gotoMyDetails(HttpServletRequest request) throws MBankException
	{
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		Client client = clientAction.viewClientDetails();
		request.setAttribute(CLIENT_ATTR, client);
		return MY_DETAILS_JSP; // next page
	}

	private String gotoMyDeposits(HttpServletRequest request)
	{
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		List<Deposit> deposits = null;
		try
		{
			deposits = clientAction.viewClientDeposits();
			request.setAttribute(DEPOSITS_LIST_ATTR, deposits);
		} catch (Exception e)
		{
		}
		return DEPOSITS_JSP; // next page
	}

	private String gotoMyRecentActivities(HttpServletRequest request)
	{
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		try
		{
			request.setAttribute(CLIENT_ACTIVITIES_ATTR, clientAction.viewClientActivities());
		} catch (MBankException e)
		{
		}
		return MY_RECENT_ACTIVITIES_JSP; // next page
	}

	private String gotoMyAccount(HttpServletRequest request) throws MBankException
	{
		System.out.println("Controller.gotoAccount()");
		// Get ClientAction object from the session
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		Account account = clientAction.viewAccountDetails();
		System.out.println("account_id = " + account.getAccount_id());
		request.setAttribute(ACCOUNT_ATTR, account); 
		setCommissionRateInRequest(request, clientAction);
		return ACCOUNT_JSP; // next page
	}
}
