import mbank.actionsTest.ActionTest;
import mbank.actionsTest.AdminActionTest;
import mbank.database.connectionTest.ConnectionPoolTest;
import mbank.database.managersImplTest.DBManagersTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({MBankTest.class,ConnectionPoolTest.class, DBManagersTest.class,AdminActionTest.class,ActionTest.class })
public class AllTests {

}
