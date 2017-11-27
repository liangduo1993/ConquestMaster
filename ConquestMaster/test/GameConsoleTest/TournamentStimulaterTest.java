package GameConsoleTest;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Core.GameLoader;
import GameConsole.Core.GameState;
import GameConsole.Core.TournamentStimulater;
import GameConsole.Model.Player.Player;
import GameConsole.Strategy.AggressiveStrategy;
import GameConsole.Strategy.BenevolentStrategy;
import GameConsole.Strategy.CheaterStrategy;
import GameConsole.Strategy.RandomStrategy;

/**
 * 
 * This class is a test class for class TournamentStimulater.
 *
 */
public class TournamentStimulaterTest {
	GameState gs;
	GameLoader gl;
	Player p1;
	Player p2;
	Player p3;
	Player p4;

	/**
	 * Set up function, to do some initial work.
	 * 
	 * @throws Exception
	 *             If the target file is not valid, it would throw an exception.
	 */
	@Before
	public void setUp() throws Exception {

		gl = new GameLoader(null, "resources/GimpFiles/13.txt");

	}

	/**
	 * test function: execute(). Check if the tournament can be executed.
	 */
	@Test
	public void testExecute() {
			gs = gl.getGameState();
			p1 = new Player("p1", Color.magenta, gs, new AggressiveStrategy());
			p2 = new Player("p2", Color.green, gs, new BenevolentStrategy());
			p3 = new Player("p3", Color.blue, gs, new CheaterStrategy());
			p4 = new Player("p4", Color.red, gs, new RandomStrategy());
			List<Player> list = new ArrayList<>();
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);

			TournamentStimulater gameSt = new TournamentStimulater(gs, list, 10, false);
			gs.gameStart(false);

			List<String> result = new ArrayList<>();
			result.add("draw");
			result.add("Cheater");
			result.add("Aggresive");
			result.add("Random");
			result.add("Human");
			assertEquals(true, result.contains(gameSt.execute()));
	}
}
