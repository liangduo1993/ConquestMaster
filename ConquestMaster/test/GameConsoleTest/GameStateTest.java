package GameConsoleTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Core.GameState;
import GameConsole.Core.Group;
import GameConsole.Model.Player.Player;
import GameConsole.View.WindowMain;

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
