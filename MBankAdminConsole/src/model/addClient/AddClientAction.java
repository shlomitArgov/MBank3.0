package model.addClient;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import mbankExceptions.MBankException;
import model.login.LoginAction;

public class AddClientAction implements ActionListener {

	private JTextField name;
	private JPasswordField password;
	private JTextField address;
	private JTextField email;
	private JTextField phone;
	private JTextField deposit;
	private Container container;

	public AddClientAction(Container contentPane, JTextField name,
			JPasswordField password, JTextField address, JTextField email,
			JTextField phone, JTextField deposit) {
		this.name = name;
		this.password = password;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.deposit = deposit;
		this.container = contentPane;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String clientName = this.name.getText();
		char[] clientPassword = this.password.getPassword();
		String clientAddress = this.address.getText();
		String clientEmail = this.email.getText();
		String clientPhone = this.phone.getText();
		double deposit = 0;
		long clientId;
		try {
			/* Validate name and password fields are not empty */
			if(clientName.length() == 0)
			{
				throw new MBankException("Client name must not be empty");
			}
			if(clientPassword.length == 0)
			{
				throw new MBankException("Password must not be empty");
			}
			/* Check that only digits and possibly a single '.' was entered into the deposit field */
			char[] depositCharArray = this.deposit.getText().toCharArray();
			int countDots = 0;
			for (int i = 0; i < depositCharArray.length; i++) {
				if ((!(Character.isDigit(depositCharArray[i]))) && (countDots  < 2)) 
				{
					if(depositCharArray[i] == '.')
					{
						countDots++;
					}
					else
					{
						throw new MBankException("Deposit value must be a non-negative number.\nValue entered: " + this.deposit.getText());
					}
				}
			}
			
			if(countDots < 2)
			{
				deposit = Double.valueOf(this.deposit.getText()); /* Throws a NumberFormatException */
				
				clientId = LoginAction.getAdminAction().addNewClient(clientName,
						clientPassword, clientAddress, clientEmail, clientPhone,
						deposit);
				/*
				 * If we made it here - the client was added successfully Display a
				 * message indicating success and update the page
				 */
				JOptionPane.showMessageDialog(container,
						"Client was successfuly added with ID: " + clientId, "Confirmation",
						JOptionPane.INFORMATION_MESSAGE);

				/*
				 * Close the add client dialog once a client has been successfully
				 * added
				 */
				SwingUtilities.getWindowAncestor(container).dispose();

			}
			else
			{
				throw new MBankException("Deposit value must be a non-negative number.\nValue entered: " + this.deposit.getText());
			}
			} catch (NumberFormatException e) {
			/*
			 * Display error dialog in case invalid value is inserted into the
			 * deposit field
			 */
			JOptionPane
					.showMessageDialog(
							container,
							"Failed to add client:\nInvalid deposit value. Deposit amount must be a positive number.",
							"Error", JOptionPane.ERROR_MESSAGE);
		} catch (MBankException e) {
			/* Display error dialog when server-side validation fails */
			JOptionPane.showMessageDialog(container, "Failed to add client:\n"
					+ e.getLocalizedMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

}
//