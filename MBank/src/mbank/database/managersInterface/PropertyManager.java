package mbank.database.managersInterface;

import java.sql.Connection;
import java.util.ArrayList;

import mbank.database.beans.Property;
import mbankExceptions.MBankException;

public interface PropertyManager
{
	boolean insert(Property property, Connection con);

	void update(Property property, Connection con) throws MBankException;

	boolean delete(Property property, Connection con);

	Property query(String propertyName, Connection con) throws MBankException;
	
	ArrayList<Property> queryAllProperties(Connection con) throws MBankException;
}