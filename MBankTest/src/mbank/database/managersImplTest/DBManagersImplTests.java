package mbank.database.managersImplTest;
import mbank.database.managersImplTest.ClientDBManagerImplTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ClientDBManagerImplTest.class,AccountsDBManagerImplTest.class, ActivityDBManagerImplTest.class})
public class DBManagersImplTests {

}
