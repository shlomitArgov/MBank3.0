package view.mainFrame.console;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import model.addClient.AddClientAction;
import view.mainFrame.MainFrame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;

public class AddClientDialog extends JDialog {

	private static final long serialVersionUID = 2160145370553977688L;

	public AddClientDialog()
	{
		super(MainFrame.getInstance(), "Add client", true);
		setMinimumSize(new Dimension(500, 200));
		setSize(new Dimension(600, 350));
		setLocationRelativeTo(null); /* center of screen */
		
		init();
	}
	
	/* Create the add client pop-up dialog */
	private void init() {
		BorderLayout borderLayout = new BorderLayout();
		JPanel addClientPane = new JPanel();
		
		getContentPane().setLayout(borderLayout);
		getContentPane().add(addClientPane, BorderLayout.CENTER);
		
		final JTextField txtName = new JTextField();
		final JPasswordField Password = new JPasswordField();
		final JTextField txtAddress = new JTextField();
		final JTextField txtEmail = new JTextField();
		final JTextField txtPhone = new JTextField();
		final JTextField txtDeposit = new JTextField();
		
		JLabel lblName = new JLabel("Client name: ");
		JLabel lblPassword = new JLabel("Password: ");
		JLabel lblAddress = new JLabel("Addresss: ");
		JLabel lblEmail = new JLabel("Email: ");
		JLabel lblPhone = new JLabel("Phone number: ");
		JLabel lblDeposit = new JLabel("Deposit [$]: ");
		
		addClientPane.setLayout(new GridLayout(6, 2, 20, 10));
		Border paddingBorder = BorderFactory.createEmptyBorder(10,20,10,20);
		addClientPane.setBorder(paddingBorder);
		
		addClientPane.add(lblName);
		addClientPane.add(txtName);
		addClientPane.add(lblPassword);
		addClientPane.add(Password);
		addClientPane.add(lblAddress);
		addClientPane.add(txtAddress);
		addClientPane.add(lblEmail);
		addClientPane.add(txtEmail);
		addClientPane.add(lblPhone);
		addClientPane.add(txtPhone);
		addClientPane.add(lblDeposit);
		addClientPane.add(txtDeposit);
		
		JButton btnCreate = new JButton("Add");
		btnCreate.setMnemonic('A');
		btnCreate.setToolTipText("Add");
		JPanel south = new JPanel();
		south.add(btnCreate);
		getContentPane().add(south, BorderLayout.SOUTH);
		
		btnCreate.addActionListener(new AddClientAction(this.getContentPane(), txtName, Password, txtAddress, txtEmail, txtPhone, txtDeposit)); 
		}
	}