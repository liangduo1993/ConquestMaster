package MapEditor;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import MapEditor.Core.MainFrame;
import MapEditor.Model.ConquestMap;
import MapEditor.Model.Continent;
import MapEditor.Model.Territory;
import MapEditor.View.TablePanel;

public class TablePanelTest {
	private ConquestMap map;
	private String path;
	private TablePanel table;
	
	
	@Before
	public void setUp() throws Exception {
		map = new ConquestMap();
		path = this.getClass().getClassLoader().getResource("ConquestMaps/Atlantis.map").getPath().substring(1);
		map.load(path);
		table = new TablePanel(map);
		map.addObserver(table);
	}


	@Test
	public void testUpdate() {
		Territory newTerritory = new Territory();
		newTerritory.setName("newTerritory");
		newTerritory.setCenter(1, 1);
		newTerritory.setCont(map.continents.get(0));
		map.addTerritory(newTerritory);
		Object[][] terNames = table.getTerNames();
		int length = terNames.length;
		assertEquals(43, length);
		
		Continent newContinent = new Continent("newContinent", 1);
		map.addContinent(newContinent);
		assertEquals(7, table.getContNames().length);
	}

}
