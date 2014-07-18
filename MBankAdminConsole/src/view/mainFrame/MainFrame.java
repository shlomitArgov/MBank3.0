package view.mainFrame;
import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

/**
 * @author Shlomit Argov
 * Main MBank console application frame: contains all information apart for dialogs with the user which will appear in separate pop-ups
 */
public class MainFrame extends JFrame{
	private static final long serialVersionUID = 2229923235856134544L;
	private static MainFrame instance = new MainFrame();
	
	/* The layout for the main frame is used to switch between different pages (views) in the application */
	public static CardLayout mainCardLayout;
	
	/* The following strings serve as identifiers which refer to different "cards" (views) in the mainframe's card layout */
	public static final String loginCard = "LOGIN_PANEL";
	public static final String consoleCard = "CONSOLE_PANEL"; //Refers to the main console panel  
	
	private MainFrame() {
		super("MBank Administration Console");
		MainFrame.mainCardLayout = new CardLayout();
		getContentPane().setLayout(MainFrame.mainCardLayout);
		this.setSize(1000, 600);
		/* Add an icon to the main frame */
		String iconPath = "./images/Business-Money-bag-icon.png";
		ImageIcon mainframeIcon = new ImageIcon(iconPath);
		this.setIconImage(mainframeIcon.getImage());
		
		this.setLocationRelativeTo(null); //Open on center on screen
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitFrame();
			}
		});
		
		/* Create the login page */
		JScrollPane loginPane = new LoginPage().createLoginPanel();
		
		/* Add the login page to the main frame along with a string to identify it for the card layout*/
		getContentPane().add(loginPane, MainFrame.loginCard);
		
		/* Add the login page to the main frame's card layout */
		MainFrame.mainCardLayout.addLayoutComponent(loginPane, MainFrame.loginCard);
		
		/* Create the console page */
		ConsolePage consolePage = new ConsolePage();

		/* Add the console page to the main frame along with a string to identify it for the card layout */
		getContentPane().add(consolePage, MainFrame.consoleCard);
		
		/* Add the console page to the main frame's card layout */
		MainFrame.mainCardLayout.addLayoutComponent(consolePage, MainFrame.consoleCard);

		/* Display the login page from the card layout - this is the first page displayed in the application */
		MainFrame.mainCardLayout.show(getContentPane(), MainFrame.loginCard);		
	}
	
	/* Exit gracefully if the window is closed */
	private void exitFrame() {
		System.exit(0);
	}
	
	public static MainFrame getInstance()
	{
		return instance;
	}
		
	/**
	 * @param 
	 */
	public static void main(String[] args) {
		/* Create the application's main frame */
		MainFrame mainFrame = MainFrame.getInstance();
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    new JDialog(mainFrame,"Failed to set system look and feel");
		}
		mainFrame.setVisible(true);
	}
}
