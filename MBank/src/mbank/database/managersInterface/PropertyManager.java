package mbank.database.managersInterface;

import java.sql.Connection;
import java.util.ArrayList;

import mbank.database.beans.Property;
import mbankExceptions.MBankException;

public interface PropertyManager
{
	void insert(Property property, Connection con) throws MBankException;

	void update(Property property, Connection con) throws MBankException;

	void delete(Property property, Connection con) throws MBankException;

	Property query(String propertyName, Connection con) throws MBankException;
	
	ArrayList<Property> queryAllProperties(Connection con) throws MBankException;
}