package view.mainFrame.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import view.mainFrame.MainFrame;
import mbank.database.beans.Property;
import model.systemProperties.SystemPropertyTableModel;

public class ViewAndEditSystemPropertyPanel extends JPanel {
	private static final long serialVersionUID = 2963803665310191327L;
	private JTable propTable;
	private SystemPropertyTableModel propertyTableModel;
	
	public ViewAndEditSystemPropertyPanel() {
		BorderLayout borderLayout = new BorderLayout(10, 20);
		this.setLayout(borderLayout);
		
		init();	
		}
	private void init() {
		/* Add header text to north */
		JLabel headerText = new JLabel("System properties", JLabel.CENTER);
		headerText.setBorder(new EmptyBorder(5, 5, 5, 5));
		headerText.setForeground(new Color(50, 100, 150));
		headerText.setFont(new FontUIResource("Arial",Font.BOLD, 22));
		this.add(headerText, BorderLayout.NORTH);
		
		/* Add instruction text to south text */
		JLabel instructionText = new JLabel("*Click on a property in order to edit it");
		instructionText.setFont(new Font("Arial", Font.ITALIC, 16));
		instructionText.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.add(instructionText, BorderLayout.SOUTH);
		
		this.propertyTableModel = new SystemPropertyTableModel();
		
		this.propTable = new JTable(propertyTableModel);
		this.propTable.setFillsViewportHeight(true);
		
		/* Add properties table to center */
		JScrollPane tablePane = new JScrollPane(this.propTable);
		this.add(tablePane, BorderLayout.CENTER);
		
		/* Add listener to click actions on the table */
		this.propTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doubleClickTable(e);
			}
		});
		
		this.setVisible(true);
		
	}
	
	private void doubleClickTable(MouseEvent e){
		if (e.getClickCount() > 1){
			JTable source = (JTable) e.getSource();
			Point p = e.getPoint();
			int row = source.rowAtPoint(p);
			Property prop = propertyTableModel.getPropertyAtIndex(row);
			new EditPropertyDialog(MainFrame.getInstance(), prop, propertyTableModel);
		}		
	}
}
