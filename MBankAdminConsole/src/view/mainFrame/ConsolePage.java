package view.mainFrame;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import mbankExceptions.MBankException;
import view.mainFrame.console.AddClientDialog;
import view.mainFrame.console.ViewAndEditSystemPropertyPanel;
import view.mainFrame.console.ViewClientDetailsOptionPane;
/**
 * 
 * @author Shlomit Argov
 * Console page - a JSplitPane.
 * This pane contains all administrative action options (as buttons) on the left pane (buttonsPane) 
 * and displays related action information on the right pane (displayPane)
 */
public class ConsolePage extends JSplitPane{
	
	private static final long serialVersionUID = -1341828517953520679L;
	
 	/* The layout for the display pane is used to switch between different pages (views) that correspond to different action related information
 	 * e.g. the viewClientDetailsCard corresponds to the viewClientDetails action */
	public static CardLayout displayCardLayout = new CardLayout();
	
	/* References to the different views (panels) in the display panel */
	public static final String emptyCard = "EMPTY_PANEL"; // An empty panel, used for "clearing" the display panel
	public static final String viewClientDetailsCard = "VIEW_CLIENT_DETAILS_PANEL"; // Refers to the view client details panel
	public static final String viewAndEditSystemPropertyCard = "VIEW_EDIT_SYSTEM_PROPERTY_PANEL"; //Refers to the view and edit system properties panel

	private JScrollPane viewClientDetailsPane;
	private JPanel viewAndEditSystemPropertyPane;
	private JPanel displayPane;

	private JScrollPane emptyPane;
		
	public ConsolePage() {
		/* Create the console panel */
		initConsolePanel();
	}
	
	public void initConsolePanel()
	{
		/* The console panel is a horizontal split panel with option buttons on the left panel and a results display on the right panel */
		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		/* Create the right panel - the action results display panel */
		createDisplayPane();

		/* Create the left pane - with different option buttons */
		JPanel buttonsPane = createLeftPanel();
		
		/* Set the left and right panel in the console pane */
		setLeftComponent(buttonsPane);
		setRightComponent(displayPane);
	}
	
	private JPanel createLeftPanel() {
		JPanel buttonsPane = new JPanel(new GridLayout(5, 1, 10, 10));
		
		/* Create button for the add client action */
		createButton(buttonsPane, "Add client", 'A', "Add Client" , new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog addClientPane = new AddClientDialog();
				addClientPane.setVisible(true);			
			}
		});
		
		/* Create button for the view client details action */
		createButton(buttonsPane, "View client details", 'V', "View client details", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewClientDetailsOptionPane viewClientOptionPane = new ViewClientDetailsOptionPane();
				try {
					viewClientDetailsPane =  viewClientOptionPane.getClientDetailsPanel();
				} catch ( MBankException e1) {
					/*
					 * Display error dialog in user with the requested ID is not found in the database
					 */
					JOptionPane
							.showMessageDialog(viewClientDetailsPane,e1.getLocalizedMessage(),"Error", JOptionPane.ERROR_MESSAGE);
//					viewClientDetailsPane = new noSuchClientPanel(e1.getLocalizedMessage());
				}
				if(viewClientDetailsPane == null) /* Failed to retrieve the client details --> set the panel to be the empty panel */
				{
					/* Update the display pane with the empty pane */
					ConsolePage.displayCardLayout.show(displayPane, ConsolePage.emptyCard);
				}
				else
				{
					/* Update the display pane with the client details pane */
					viewClientDetailsPane.setVisible(true);
					
					/* Add the client details panel to the display panel */
					displayPane.add(viewClientDetailsPane, ConsolePage.viewClientDetailsCard);
					
					/* Add the client details panel to the cardlayout */
					ConsolePage.displayCardLayout.addLayoutComponent(viewClientDetailsPane, ConsolePage.viewClientDetailsCard);

					/* Update the display pane with the client details */
					ConsolePage.displayCardLayout.show(displayPane, ConsolePage.viewClientDetailsCard);
				}
			}
		});
		
		/* Create button for the view and edit a system property action */
		createButton(buttonsPane, "View and edit a system property", 'P', "View and edit system property", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					viewAndEditSystemPropertyPane = new ViewAndEditSystemPropertyPanel();
				} catch (MBankException e1) {
					/* This should be unreachable code - it should be impossible to enter
					 * invalid values into the properties table */
					
					/* Display error message */
					JOptionPane.showMessageDialog(displayPane, "Failed to retrieve properties\n" + e1.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				viewAndEditSystemPropertyPane.setVisible(true);
				
				/* Add the system properties table panel to the display panel */
				displayPane.add(viewAndEditSystemPropertyPane, viewAndEditSystemPropertyCard);
				
				/* Add the system properties table panel to the cardlayout */
				displayCardLayout.addLayoutComponent(viewAndEditSystemPropertyPane, viewAndEditSystemPropertyCard);
				
				/* Update the display pane with the system properties table */
				displayCardLayout.show(displayPane, viewAndEditSystemPropertyCard);
			}
		});
		
		/* Create button for the clear results display pane action */
		createButton(buttonsPane, "Clear", 'C', "Clear", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayCardLayout.show(displayPane, ConsolePage.emptyCard);
			}
		});
		

		/* Create button for the clear results display pane action */
		createButton(buttonsPane, "Logout", 'L', "Logout", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/* Clear display pane in console */
				ConsolePage.displayCardLayout.show(displayPane, emptyCard);
				/* Display login page */
				MainFrame.mainCardLayout.show(MainFrame.getInstance().getContentPane(), MainFrame.loginCard);
			}
		});
		
		return buttonsPane;
	}

	private void createDisplayPane() {		
		/* Create the right pane - a card layout that will display different results (cards) for different chosen options from the left panel */
		displayPane = new JPanel();
		displayPane.setLayout(displayCardLayout);
		
		/* Create an empty panel which will be used to "clear" the results display panel */
		emptyPane = new JScrollPane();
		
		/* Add the empty panel to the display panel */
		displayPane.add(emptyPane, emptyCard);
		
		/* Add the empty panel to the display panel's card layout */
		displayCardLayout.addLayoutComponent(emptyPane, emptyCard);
		
		/* Set the current display to be an empty panel (before any option is chosen - an empty panel is displayed) */
		displayCardLayout.show(displayPane, emptyCard);
	}

	/*  Utility method for adding a button to a panel along with an actionListener */
	protected static void createButton(JPanel pane, String name, char mnemonic, String tooltip, ActionListener action) {
		JButton showTableButton = new JButton(name);
		showTableButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		showTableButton.setMnemonic(mnemonic);
		showTableButton.setToolTipText(tooltip);
		showTableButton.addActionListener(action);
		pane.add(showTableButton);
	}
}
