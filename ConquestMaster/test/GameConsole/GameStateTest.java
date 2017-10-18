package GameConsole;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Player.Group;
import GameConsole.Player.Player;
import GameConsole.Window.WindowMain;
import GameConsole.World.GameState;

public class GameStateTest {
	private GameState state;
	private WindowMain window;
	@Before
	public void setUp() throws Exception {
		window = new WindowMain();
		state = new GameState(window , "resources/ConquestMaps/Atlantis.map");
		
	}

	@Test
	public void testGetCurrPhase() {
		System.out.println("currentphase:"+state.getCurrPhase());
		assertEquals(0,state.getCurrPhase());
	}

}
