package view.mainFrame.console;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class noSuchClientPanel extends JPanel {

	private static final long serialVersionUID = 4215251316347491070L;
	private String errorString;
	
	public noSuchClientPanel(String errorString) {
		this.errorString = errorString;
		init();
		}

	private void init() {
		JLabel errorLabel = new JLabel(errorString, JLabel.CENTER);
		errorLabel.setFont(new Font("Arial", Font.ITALIC, 18));
		errorLabel.setForeground(Color.RED);

		GridLayout gridLayout = new GridLayout(1,1,10, 10);
		this.setLayout(gridLayout);
		
		this.add(errorLabel);
	}
	
}
