package model.viewClient;

import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import mbank.actions.AdminAction;
import mbank.database.beans.Client;
import mbankExceptions.MBankException;
import model.login.LoginAction;

public class DisplayClientInfoAction {
	private static AdminAction adminAction;
	private Client client;

	public DisplayClientInfoAction(String clientId) throws NumberFormatException, MBankException {
		adminAction = LoginAction.getAdminAction();
		initializeClientFromDB(clientId);
	}
	
	public void initializeClientFromDB(String clientId) throws MBankException {
			Client c = adminAction.viewClientDetails(Long.parseLong(clientId));
			this.client = c;
	}

	public JScrollPane getClientDetailsPane() {

		// Create columns names
		String columnNames[] = { "Name", "ID", "Address", "Phone #", "email",
				"Account type", "Comment" };

		// Create some data
		String dataValues[][] = { { client.getClient_name(),
				String.valueOf(client.getClient_id()), client.getAddress(),
				client.getPhone(), client.getEmail(),
				client.getType().getTypeStringValue(), client.getComment() } };

		// Create a new table to display the client details
		JTable clientTable = new JTable(dataValues, columnNames)
		{
			private static final long serialVersionUID = 2353889227742069842L;

			//Implement table cell tool tips.  
			@Override
            public String getToolTipText(MouseEvent e) {
                String tooltip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                      tooltip = getValueAt(rowIndex, colIndex).toString();
                } catch (RuntimeException e1) {
                    //catch null pointer exception if mouse is over an empty line
                }
                return tooltip;
            }
		};
		/* Table is for display only */
		clientTable.setEnabled(false);

		// Add the table to a panel
		JScrollPane clientDetailsPane = new JScrollPane();
		clientDetailsPane.setViewportView(clientTable);
		return clientDetailsPane;
	}
}
