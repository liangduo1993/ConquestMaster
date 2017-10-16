package MapEditor;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import MapEditor.Model.ConquestMap;
import MapEditor.Model.Continent;

/**
 * 
 * This class is a test class for class ConquestMap, for the functions which can load, save, saveas map.
 *
 */
public class MapHandlerTest {
	private ConquestMap map;
	private String path;

	/**
	 * Set up function, to do some initial work.
	 * 
	 * @throws Exception
	 *             If the target map is not valid, it would throw an exception.
	 */
	@Before
	public void setUp() throws Exception {
		map = new ConquestMap();
		path = this.getClass().getClassLoader().getResource("ConquestMaps/Atlantis.map").getPath().substring(1);
	}

	/**
	 * test class: ConquestMap, function: ConquestMap.territories().size(), test
	 * class: Territory, function: getContinent(), test class: Continent,
	 * function: getName(). check if a map including a specific terrtories list,
	 * and if there is a specific territory.
	 */
	@Test
	public void testLoad() throws Exception {
		map.load(path);
		assertEquals(42, map.territories.size());
		assertEquals(2, map.findTerritory("Forgoth").getLinks().size());
		assertEquals("Kala", map.findTerritory("Forgoth").getContinent().getName());
	}

	/**
	 * test class: ConquestMap, function load(). check if there is a error
	 * message when we load a invalid map file.
	 */
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

	/**
	 * test class ConquestMap, function: addContinent(), function save(). check
	 * if we can add a new continent and save as a new map file in the local
	 */
	@Test
	public void testSaveString() throws Exception {
		map.load(path);
		map.addContinent(new Continent("newContinent", 1));
		map.save("f:\\1.map");
	}

}
