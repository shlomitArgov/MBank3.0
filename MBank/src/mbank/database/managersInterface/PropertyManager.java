package mbank.database.managersInterface;

import java.util.ArrayList;

import mbank.database.beans.Property;
import mbank.exceptions.MBankException;

public interface PropertyManager
{	
	void insert(Property property) throws MBankException;

	void update(Property property) throws MBankException;

	void delete(Property property) throws MBankException;

	Property query(String propertyName) throws MBankException;
	
	ArrayList<Property> queryAllProperties() throws MBankException;	
}