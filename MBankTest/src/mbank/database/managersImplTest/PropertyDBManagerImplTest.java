/**
 * 
 */
package mbank.database.managersImplTest;

import java.sql.DriverManager;
import java.sql.SQLException;
import mbank.Util;
import mbank.database.beans.Property;
import mbank.database.managersImpl.PropertyDBManager;
import mbankExceptions.MBankException;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Shlomit Argov
 * 
 */
public class PropertyDBManagerImplTest {
	private static PropertyDBManager propertyDBManager;

	private static Property property1;
	private static Property property2;
	
	/**
	 * @throws MBankException 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws SQLException, MBankException {
		
		String url=Util.DB_URL;
		DriverManager.getConnection(url);

		propertyDBManager = new PropertyDBManager();
		
		property1 = new Property("prop_key_1", "prop_value_1");
		property2 = new Property("prop_key_2", "prop_value_2");
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	//cleanup
	public void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testPropertyDBManager() 
	{
		// test insert
		try
		{
			propertyDBManager.insert(property1);
			propertyDBManager.insert(property2);
		}
		
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Insert into Properties table failed");
		}
		
		// test query
		try
		{
			propertyDBManager.query(property1.getProp_key());
			propertyDBManager.queryAllProperties();
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Query Properties table failed");
		}

		// test update
		try
		{
			property1.setProp_value("updatePropValue");
			propertyDBManager.update(property1);
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Update Properties table failed");
		}
		
		// test delete
		try
		{
			propertyDBManager.delete(property1);
			propertyDBManager.delete(property2);
		}
		catch(MBankException e)
		{
			e.printStackTrace();
			Assert.fail("Delete property from Properties table failed");
		}
	}
}
