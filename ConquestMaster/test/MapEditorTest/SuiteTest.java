package MapEditorTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * 
 * Using Suite as a runner to manually build a suite containing tests from many classes.
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
		ConquestMapTest.class, //
		MyStringUtilTest.class, //
		TablePanelTest.class,//
		ContinentTest.class,//
		FileChooserTest.class,//
		InputContinentFrameTest.class,//
		InputTerritoryFrameTest.class,//
		LogPanelTest.class,//
		MainFrameTest.class,//
		NewMapFrameTest.class,//
		SettingsFrameTest.class,//
		StringUtilTest.class,//
		TerritoryTest.class//
		
})
public class SuiteTest {

}
