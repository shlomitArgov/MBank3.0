package mbank;
import mbank.actions.ActionTest;
import mbank.actions.AdminActionTest;
import mbank.database.connectionTest.ConnectionPoolTest;
import mbank.database.managersImplTest.ClientDBManagerImplTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({MBankTest.class,ConnectionPoolTest.class, ClientDBManagerImplTest.class,AdminActionTest.class,ActionTest.class })
public class AllTests {

}
