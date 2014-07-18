package view.mainFrame.console;

import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import mbankExceptions.MBankException;
import model.viewClient.DisplayClientInfoAction;

public class ViewClientDetailsOptionPane extends JOptionPane {
	private static final long serialVersionUID = -2962900714739409231L;
	private JScrollPane viewClientDetailsPanel;
	public ViewClientDetailsOptionPane() {
		super("View client details", JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		setMinimumSize(new Dimension(500, 200));
		setSize(new Dimension(600, 350));
	}

	/* Create the enter client details for display pop-up dialog */
	public JScrollPane getClientDetailsPanel() throws MBankException {
	    String message = "Enter client ID#";		
	    setVisible(true);
	    
	    /* Get client ID from the user */
	    String clientId = JOptionPane.showInputDialog(this,message);
	    if (clientId != null)
	    {
	    	 DisplayClientInfoAction displayClientInfoAction= new DisplayClientInfoAction(clientId);
	    	 viewClientDetailsPanel = displayClientInfoAction.getClientDetailsPane();
	    }
	   return viewClientDetailsPanel;
	}
}
