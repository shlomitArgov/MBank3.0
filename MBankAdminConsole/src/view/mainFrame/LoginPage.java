package view.mainFrame;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import mbank.actions.AdminAction;
import mbankExceptions.MBankException;
import model.login.LoginAction;

import javax.swing.border.MatteBorder;

public class LoginPage extends JScrollPane{
	public LoginPage() {
	}
	private static final long serialVersionUID = -7435083609211160701L;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	
	private JPanel viewPortPanel;
	
	private AdminAction adminAction;
	
	/* Create the login page and register an event handler for the login button*/
	public JScrollPane createLoginPanel() {
		
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		viewPortPanel = new JPanel();
		viewPortPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		viewPortPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		this.setViewportView(viewPortPanel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{180, 80, 240, 180};
		gbl_panel.rowHeights = new int[]{10, 20, 30, 20, 30, 20, 30, 20, 10};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		viewPortPanel.setLayout(gbl_panel);
		
		/* create and add login components */
		JLabel loginHeaderText = new JLabel("Welcome to MBank Administration Console");
		loginHeaderText.setForeground(new Color(50, 100, 150));
		loginHeaderText.setFont(new Font("Cambria Math", Font.BOLD, 18));
		GridBagConstraints gbc_loginHeader = new GridBagConstraints();
		gbc_loginHeader.ipadx = 2;
		gbc_loginHeader.insets = new Insets(0, 0, 5, 5);
		gbc_loginHeader.gridx = 2;
		gbc_loginHeader.gridy = 1;
		viewPortPanel.add(loginHeaderText, gbc_loginHeader);
		
		JLabel usernameLabel = new JLabel("Username");
		GridBagConstraints gbc_usernameLabel = new GridBagConstraints();
		gbc_usernameLabel.anchor = GridBagConstraints.WEST;
		gbc_usernameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_usernameLabel.gridx = 1;
		gbc_usernameLabel.gridy = 3;
		viewPortPanel.add(usernameLabel, gbc_usernameLabel);
		
		txtUsername = new JTextField();
		txtUsername.setToolTipText("Enter username");
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsername.gridx = 2;
		gbc_txtUsername.gridy = 3;
		viewPortPanel.add(txtUsername, gbc_txtUsername);
		txtUsername.setColumns(20);
		
		JLabel passwordLabel = new JLabel("Password");
		GridBagConstraints gbc_pwdLabel = new GridBagConstraints();
		gbc_pwdLabel.anchor = GridBagConstraints.WEST;
		gbc_pwdLabel.ipadx = 2;
		gbc_pwdLabel.insets = new Insets(0, 0, 5, 5);
		gbc_pwdLabel.gridx = 1;
		gbc_pwdLabel.gridy = 5;
		viewPortPanel.add(passwordLabel, gbc_pwdLabel);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(20);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 5;
		viewPortPanel.add(passwordField, gbc_passwordField);
		
		JButton loginButton = new JButton("Login");
		loginButton.setMnemonic('L');
		loginButton.setAlignmentX(0.5f);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 7;
		viewPortPanel.add(loginButton, gbc_btnNewButton);
		
		loginButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {					
					/* Login */
					LoginAction.login(txtUsername.getText(), passwordField.getPassword());
				} catch (MBankException e) {	
					/* Display error message */
					JOptionPane.showMessageDialog(viewPortPanel, "Login failed:\n" + e.getLocalizedMessage(), "Login failure", JOptionPane.ERROR_MESSAGE);
				}
				/* Clean up login and password input fields after login action has been performed */
				passwordField.setText("");
				txtUsername.setText("");
			}
		});
		
		/* Save the adminAction for reuse */
		this.adminAction = LoginAction.getAdminAction();
		
		return this;
	}
	public AdminAction getAdminAction() {
		return adminAction;
	}
}
