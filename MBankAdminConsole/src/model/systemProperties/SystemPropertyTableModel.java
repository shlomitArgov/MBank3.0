package model.systemProperties;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import mbank.actions.AdminAction;
import mbank.database.beans.Property;
import model.login.LoginAction;
/**
 * @author Shlomit Argov
 * This class is used to:
 * 1. Update the UI based on model changes
 * 2. Update the server-side based on changes in the UI
 */
public class SystemPropertyTableModel extends AbstractTableModel{

	private static final long serialVersionUID = -8231856474081947406L;
	private AdminAction adminAction = LoginAction.getAdminAction();
	private List<Property> properties;
	
	public SystemPropertyTableModel() {
		this.properties = SystemPropertyModel.getInstance(adminAction).getProperties();
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return properties.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		/* Update properties from DB */
		this.properties = SystemPropertyModel.getInstance(adminAction).getProperties();
		
		/* Return the property at the requested location */
		Property property = this.properties.get(rowIndex);
		switch (columnIndex){
			case 0:
				return property.getProp_key();
				// break;
			case 1:
				return property.getProp_value();
				// break;
			}
		return null;
	}
	public Property getPropertyAtIndex(int row) {
		return properties.get(row);
	}
	@Override
	public String getColumnName(int column) {
		switch (column){
			case 0:
				return "Key";
				// break;
			case 1:
				return "Value";
				// break;
			}
		return null;
	}


}