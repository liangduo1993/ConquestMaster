package MapEditor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import MapEditor.Core.MainFrame;
import MapEditor.Model.ConquestMap;
import MapEditor.Model.Territory;

public class MapValidateTest {
	private ConquestMap map;
	private String path;

	@Before
	public void setUp() throws Exception {
		map = new ConquestMap();
		path = this.getClass().getClassLoader().getResource("ConquestMaps/Atlantis.map").getPath().substring(1);
		map.load(path);
	}

	/**
	 * test class: ConquestMap, function: hasOneWaylink(). Check if a territory has
	 * one way link connection, an check when we delete the link connection, the
	 * link connection is null or not.
	 */
	@Test
	public void testHasOneWayLinks() {
		Territory forgoth = map.findTerritory("Forgoth");
		Territory rove = map.findTerritory("Rove");
		assertEquals(false, map.hasOneWayLinks());
		forgoth.getLinks().remove(rove);
		assertEquals(true, map.hasOneWayLinks());
	}

	/**
	 * test class: ConquestMap, function eachTerReachable(), check the territories
	 * of the map is boarding each other when we load a map, check a new territory
	 * is boarding to each other we want when we add it in the map.
	 */
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
