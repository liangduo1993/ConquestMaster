package MapEditor;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import MapEditor.Core.mainFrame;
import MapEditor.Model.ConquestMap;
import MapEditor.Model.Continent;

public class MapHandlerTest {
	private ConquestMap map;
	private mainFrame mainFrame;
	private String path;
	@Before
	public void setUp() throws Exception {
		mainFrame = new mainFrame();
		map = new ConquestMap();
		path = this.getClass().getClassLoader().getResource("ConquestMaps/Atlantis.map").getPath().substring(1);
	}

	@Test
	public void testLoad() throws IOException {
		map.load(path);
		assertEquals(42, map.territories.size());
		assertEquals(2, map.findTerritory("Forgoth").getLinks().size());
		assertEquals("Kala", map.findTerritory("Forgoth").getContinent().getName());
	}

	
	@Test
	public void testSaveString() throws Exception {
		map.load(path);
		map.addContinent(new Continent("newContinent", 1));
		map.save("f:\\1.map");
	}


}
