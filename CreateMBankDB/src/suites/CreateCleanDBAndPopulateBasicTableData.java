package suites;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import data.PopulateSampleClientData;
import tableOperations.CreateMBankTablesTest;
import tableOperations.DropMBankTablesTest;
import tableOperations.InsertAdminClientTest;
import tableOperations.PopulatePropertiesTableTest;


@RunWith(Suite.class)
@SuiteClasses({DropMBankTablesTest.class, CreateMBankTablesTest.class, 
	PopulatePropertiesTableTest.class, InsertAdminClientTest.class, PopulateSampleClientData.class})
public class CreateCleanDBAndPopulateBasicTableData {

}
