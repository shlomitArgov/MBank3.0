/**
 * 
 */
package mbankExceptions;

/**
 * @author Shlomit Argov
 *
 */
@SuppressWarnings("serial")
public class MBankException extends Exception
{
	public MBankException()
	{
		super();
	}
	
	public MBankException(String string)
	{
		super(string);
	}
	
	public MBankException(Throwable arg0)
	{
		super(arg0);
	}
	
	public MBankException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}
}