package GameConsoleTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


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
		MapLoaderTest.class,
		DriverTest.class
})
public class SuiteTest {

}
