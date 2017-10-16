package MapEditor;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MapEditSuiteTest {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(SuiteTest.class);
		System.out.println("//=====================");
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
		System.out.println("//=====================");
	}
}
