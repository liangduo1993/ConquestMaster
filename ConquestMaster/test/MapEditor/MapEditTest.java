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

	@Test
	public void testAddContinent() {
		Continent newContinent = new Continent("newContinent", 1);
		map.addContinent(newContinent);
		assertEquals(7, map.continents.size());
	}

	@Test
	public void testAddTerritory() {
		Territory newTerritory = new Territory();
		newTerritory.setName("newTerritory");
		newTerritory.setCenter(1, 1);
		newTerritory.setCont(map.continents.get(0));
		map.addTerritory(newTerritory);
		assertEquals(true, map.territories.contains(newTerritory));
	}

	@Test
	public void testClear() {
		map.clear();
		assertEquals(true, map.territories.size() == 0);
		assertEquals(true, map.continents.size() == 0);
	}

	@Test
	public void testCountTerritories() {
		assertEquals(7, map.countTerritories(map.findContinent("Kala")));
	}

	@Test
	public void testDeleteContinent() {
		Continent kala = map.findContinent("Kala");
		map.deleteContinent(kala);
		assertEquals(false, map.continents.contains(kala));
		assertEquals(null, map.findTerritory("Forgoth").getContinent());
	}

	@Test
	public void testDeleteTerritory() {
		Territory forgoth = map.findTerritory("Forgoth");
		map.deleteTerritory(forgoth);
		assertEquals(false, map.territories.contains(forgoth));
		assertEquals(false, map.findTerritory("Rove").getLinks().contains(forgoth));
	}

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
