package suites;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tableOperations.CreateMBankTablesTest;
import tableOperations.DropMBankTablesTest;
import tableOperations.InsertAdminClientTest;
import tableOperations.PopulatePropertiesTableTest;


@RunWith(Suite.class)
@SuiteClasses({DropMBankTablesTest.class, CreateMBankTablesTest.class, PopulatePropertiesTableTest.class, InsertAdminClientTest.class})
public class CreateCleanDBAndPopulateBasicTableData {

}
