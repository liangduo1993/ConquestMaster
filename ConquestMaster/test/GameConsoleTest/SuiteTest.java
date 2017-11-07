package GameConsoleTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import MapEditorTest.ConquestMapTest;
import MapEditorTest.ContinentTest;
import MapEditorTest.FileChooserTest;
import MapEditorTest.InputContinentFrameTest;
import MapEditorTest.InputTerritoryFrameTest;
import MapEditorTest.LogPanelTest;
import MapEditorTest.MainFrameTest;
import MapEditorTest.MyStringUtilTest;
import MapEditorTest.NewMapFrameTest;
import MapEditorTest.SettingsFrameTest;
import MapEditorTest.StringUtilTest;
import MapEditorTest.TablePanelTest;
import MapEditorTest.TerritoryTest;


/**
 * 
 * Using Suite as a runner to manually build a suite containing tests from many classes.
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
		CountryTest.class, //
		GroupTest.class, //
		PlayerTest.class,//
		GameStateTest.class,//
		CardsTest.class,//
		MapLoaderTest.class,//
		DriverTest.class,//
		WorldTest.class,//
		AbstractTroopTest.class,//
		TroopTest.class,//
		ContinentTest.class,//
		CountryDecoratorTest.class,//
		ConquestRatioTest.class,//
		CountryButtonTest.class,//
		DomiInfoPanelTest.class,//
		LogPanelTest.class,//
		MapDisplayerTest.class,//
		WindowMainTest.class,//
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
