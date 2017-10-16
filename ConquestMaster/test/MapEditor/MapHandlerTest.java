package MapEditor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.containsString;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import MapEditor.Core.MainFrame;
import MapEditor.Model.ConquestMap;
import MapEditor.Model.Continent;

public class MapHandlerTest {
	private ConquestMap map;
	private String path;

	@Before
	public void setUp() throws Exception {
		map = new ConquestMap();
		path = this.getClass().getClassLoader().getResource("ConquestMaps/Atlantis.map").getPath().substring(1);
	}

	@Test
	public void testLoad() throws Exception {
		map.load(path);
		assertEquals(42, map.territories.size());
		assertEquals(2, map.findTerritory("Forgoth").getLinks().size());
		assertEquals("Kala", map.findTerritory("Forgoth").getContinent().getName());
	}

	@Test
	public void testLoadInvalidMap() {
		String invalidPath = this.getClass().getClassLoader().getResource("ConquestMaps/Atlantis(invalid).map")
				.getPath().substring(1);
		try {
			map.load(invalidPath);
		} catch (Exception ex) {
			assertThat(ex.getMessage(), containsString("didn't pass the validation!"));
		}
	}

	@Test
	public void testSaveString() throws Exception {
		map.load(path);
		map.addContinent(new Continent("newContinent", 1));
		map.save("f:\\1.map");
	}

}
