package GameConsoleTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Core.GameState;
import GameConsole.Core.Group;
import GameConsole.Model.Army.AbstractTroop;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;
import GameConsole.View.WindowMain;

public class CountryTest {
	private Country country;
	private ArrayList<AbstractTroop> troops;
	@Before
	public void setUp() throws Exception {
		country = new Country();
		troops = new ArrayList<AbstractTroop>();
		country.addInfrantry(3);
	}

	@Test
	public void testAddTroop() {
		assertEquals(3,country.getTroops().size());
		
		
	}

}
