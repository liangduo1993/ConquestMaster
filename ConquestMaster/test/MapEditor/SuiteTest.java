package MapEditor;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * 
 * Using Suite as a runner to manually build a suite containing tests from many classes.
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ MapEditTest.class, //
		MapHandlerTest.class, //
		MapValidateTest.class, //
		MyStringUtilTest.class, //
		TablePanelTest.class//
})
public class SuiteTest {

}
