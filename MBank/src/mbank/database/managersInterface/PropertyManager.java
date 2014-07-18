package mbank.database.managersInterface;

import java.sql.Connection;
import java.util.ArrayList;
import mbank.database.beans.Property;

public interface PropertyManager
{
	boolean insert(Property property, Connection con);

	boolean update(Property property, Connection con);

	boolean delete(Property property, Connection con);

	Property query(String propertyName, Connection con);
	
	ArrayList<Property> queryAllProperties(Connection con);
}