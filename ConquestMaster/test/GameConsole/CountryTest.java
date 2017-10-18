package GameConsole;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Army.Troop;
import GameConsole.Player.Group;
import GameConsole.Player.Player;
import GameConsole.Window.WindowMain;
import GameConsole.World.Country;
import GameConsole.World.GameState;

public class CountryTest {
	private Country country;
	private ArrayList<Troop> troops;
	@Before
	public void setUp() throws Exception {
		country = new Country();
		troops = new ArrayList<Troop>();
		country.addInfrantry(3);
	}

	@Test
	public void testAddTroop() {
		assertEquals(3,country.getTroops().size());
		
		
	}

}
