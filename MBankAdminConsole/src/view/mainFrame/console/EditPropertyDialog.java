package view.mainFrame.console;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import mbank.database.beans.Property;
import mbankExceptions.MBankException;
import model.login.LoginAction;
import model.systemProperties.SystemPropertyModel;
import model.systemProperties.SystemPropertyTableModel;

public class EditPropertyDialog extends JDialog {
	private static final long serialVersionUID = -8512623627797004130L;
	
	private Property prop;
	private SystemPropertyTableModel tableModel;
	private JTextField propertyValue;
	private JFrame frame;
	
	public EditPropertyDialog(JFrame frame, Property prop, SystemPropertyTableModel tableModel) {
		super(frame, "Edit property", true);
		this.prop = prop;
		this.tableModel = tableModel;
		this.frame = frame;
		setMinimumSize(new Dimension(300, 200));
		setMaximumSize(new Dimension(300, 200));
		setLocationRelativeTo(null); /* center of screen */
		
		init();	
		
	}
	private void init() {
		BorderLayout borderLayout = new BorderLayout();
		this.setLayout(borderLayout);
		
		JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		Border paddingBorder = BorderFactory.createEmptyBorder(10,20,10,20);
		centerPanel.setBorder(paddingBorder);
		
		JLabel propertyKey = new JLabel(prop.getProp_key());
		propertyValue = new JTextField(prop.getProp_value());
		
		centerPanel.add(propertyKey, BorderLayout.CENTER);
		centerPanel.add(propertyValue, BorderLayout.CENTER);
		
		this.add(centerPanel, BorderLayout.CENTER);
		
		JButton okBtn = new JButton("OK");
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					/* Update the system property in the DB */
					SystemPropertyModel.getInstance(LoginAction.getAdminAction()).setProperty(new Property(prop.getProp_key(), propertyValue.getText()));
					/* This will only take place if not exception was thrown when trying to edit a property */
					tableModel.fireTableDataChanged();
					closeDialog();
				} catch (MBankException e) {
					closeDialog(); // Close the edit property dialog
					JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		this.add(okBtn, BorderLayout.SOUTH);
	this.setVisible(true);	
	}
	
	private void closeDialog() {
		this.dispose();
	}
}
