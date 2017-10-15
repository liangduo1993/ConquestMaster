package MapEditor;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import MapEditor.Util.MyStringUtil;

public class MyStringUtilTest {


	@Test
	public void testIsNumeric() {
		assertEquals(true, MyStringUtil.isNumeric("12"));
		assertEquals(false, MyStringUtil.isNumeric("abc"));
	}

	
	@Test
	public void testCheckType() {
		String mapPath = this.getClass().getClassLoader().getResource("ConquestMaps/Atlantis.map").getPath().substring(1);
		String imagePath = this.getClass().getClassLoader().getResource("ConquestMaps/Atlantis.bmp").getPath().substring(1);
		File file1 = new File(mapPath);
		File file2 = new File(imagePath);
		assertEquals(false, MyStringUtil.checkType(file1));
		assertEquals(true, MyStringUtil.checkType(file2));
	}

	@Test
	public void testHasLength() {
		assertEquals(true, MyStringUtil.hasLength("12"));
		assertEquals(false, MyStringUtil.hasLength(""));
		assertEquals(false, MyStringUtil.hasLength(null));
	}

	@Test
	public void testJoinString() {
		String[] array = new String[]{"a", "b", "c"};
		assertEquals("a,b,c", MyStringUtil.joinString(array, ","));
	}

}
