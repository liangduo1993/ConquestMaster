package MapEditor;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import MapEditor.Core.MainFrame;
import MapEditor.Model.ConquestMap;
import MapEditor.Model.Continent;
import MapEditor.Model.Territory;

public class MapEditTest {
	private ConquestMap map;
	private String path;

	@Before
	public void setUp() throws Exception {
		map = new ConquestMap();
		path = this.getClass().getClassLoader().getResource("ConquestMaps/Atlantis.map").getPath().substring(1);
		map.load(path);
	}

	/**
	 * test class: ConquestMap. function: addContinent(). Check if add a new
	 * continent the size of the continents list is changed or not.
	 */
	@Test
	public void testAddContinent() {
		Continent newContinent = new Continent("newContinent", 1);
		map.addContinent(newContinent);
		assertEquals(7, map.continents.size());
	}

	/**
	 * test class: ConquestMap. Function: addTerritory(). Check if when adding a
	 * new territory to the conquest map, the territory list contains the new
	 * territory.
	 */
	@Test
	public void testAddTerritory() {
		Territory newTerritory = new Territory();
		newTerritory.setName("newTerritory");
		newTerritory.setCenter(1, 1);
		newTerritory.setCont(map.continents.get(0));
		map.addTerritory(newTerritory);
		assertEquals(true, map.territories.contains(newTerritory));
	}

	/**
	 * test class: ConquestMap, function clear(). check when we clear the map,
	 * if the continent list and the territories list size are 0.
	 */
	@Test
	public void testClear() {
		map.clear();
		assertEquals(true, map.territories.size() == 0);
		assertEquals(true, map.continents.size() == 0);
	}

	/**
	 * test class: ConquestMap, function: countTerritories(). check if the
	 * specific continent"Kala" has 7 territories.
	 */
	@Test
	public void testCountTerritories() {
		assertEquals(7, map.countTerritories(map.findContinent("Kala")));
	}

	/**
	 * test class: ConquestMap, function: deleteContinent(). Check if there is
	 * not Continent for territories when deleting the continent "Kala".
	 */
	@Test
	public void testDeleteContinent() {
		Continent kala = map.findContinent("Kala");
		map.deleteContinent(kala);
		assertEquals(false, map.continents.contains(kala));
		assertEquals(null, map.findTerritory("Forgoth").getContinent());
	}

	/**
	 * test class: ConquestMap, functionL: findTerritory(), check if there is
	 * the specific territory in the territories list when deleting it.
	 */
	@Test
	public void testDeleteTerritory() {
		Territory forgoth = map.findTerritory("Forgoth");
		map.deleteTerritory(forgoth);
		assertEquals(false, map.territories.contains(forgoth));
		assertEquals(false, map.findTerritory("Rove").getLinks().contains(forgoth));
	}
	/**
	 * test class: ConquestMap, function: FindTerritory(), ch
	 */
	@Test
	public void testFindTerritory() {
		Territory forgoth = map.findTerritory("Forgoth");
		assertEquals(true, map.territories.contains(forgoth));
		assertEquals("Forgoth", forgoth.getName());
		assertEquals("Kala", forgoth.getContinent().getName());
	}

	@Test
	public void testFindContinent() {
		Continent kala = map.findContinent("Kala");
		assertEquals(true, map.continents.contains(kala));
		assertEquals("Kala", kala.getName());
		assertEquals(6, kala.getBonus());
	}

	@Test
	public void testBuildTerritoryLinks() {
		Territory forgoth = map.findTerritory("Forgoth");
		Territory rove = map.findTerritory("Rove");
		map.buildTerritoryLinks(forgoth);
		assertEquals(2, forgoth.getLinkNames().size());
		assertEquals(2, forgoth.getLinks().size());
		assertEquals(true, forgoth.getLinks().contains(rove));
	}

	@Test
	public void testUpdateContinent() {
		Continent kala = map.findContinent("Kala");
		map.updateContinent("kala", "newKala", 1);
		assertEquals("newKala", kala.getName());
		assertEquals(1, kala.getBonus());
	}

}
