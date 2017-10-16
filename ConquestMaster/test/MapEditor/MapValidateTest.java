package MapEditor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import MapEditor.Core.mainFrame;
import MapEditor.Model.ConquestMap;
import MapEditor.Model.Territory;

public class MapValidateTest {
	private ConquestMap map;
	private mainFrame mainFrame;
	private String path;

	@Before
	public void setUp() throws Exception {
		mainFrame = new mainFrame();
		map = new ConquestMap();
		path = this.getClass().getClassLoader().getResource("ConquestMaps/Atlantis.map").getPath().substring(1);
		map.load(path);
	}
	

	@Test
	public void testHasOneWayLinks() {
		Territory forgoth = map.findTerritory("Forgoth");
		Territory rove = map.findTerritory("Rove");
		assertEquals(false, map.hasOneWayLinks());
		forgoth.getLinks().remove(rove);
		assertEquals(true, map.hasOneWayLinks());
	}


	
	@Test
	public void testEachTerReachable() {
		assertEquals(true, map.eachTerReachable());
		Territory newTerritory = new Territory();
		newTerritory.setName("newTerritory");
		newTerritory.setCenter(1, 1);
		newTerritory.setCont(map.continents.get(0));
		map.addTerritory(newTerritory);
		assertEquals(false, map.eachTerReachable());
		
	}



}
