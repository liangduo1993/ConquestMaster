package GameConsoleTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Core.GameState;
import GameConsole.Core.GameLoader;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;
import GameConsole.Strategy.AggressiveStrategy;
/**
 * 
 * This class is a test class for class AggressiveStrategy, for the methods in it.
 *
 */
public class AggressiveStrategyTest {
	GameLoader gl;
	GameState gs;
	Player p1;
	AggressiveStrategy s1;
	/**
	 * Set up function, to do some initial work.
	 * 
	 * @throws Exception
	 *             If the target file is not valid, it would throw an exception.
	 */
	@Before
	public void setUp() throws Exception {
		gl = new GameLoader(null, "resources/GimpFiles/13.txt");
		gs = gl.getGameState();
		p1 = gs.getAllPlayers().getPlayers().get(0);
		
		s1 = new AggressiveStrategy();
		s1.setGameState(gs);
		s1.setPlayer(p1);
		
		p1.setStrategy(s1);
		
	}
	/**
	 * test function: attack(). Check if the attacker
	 * keeps attack until can't attack anymore.
	 */
	@Test
	public void testAttack() {
		p1.attack();
		assertEquals(false, p1.checkIfCanAttack());
		
		for(Country c: p1.getCountries()){
			System.out.println(c.getName() + ": " + c.getTroopNum());
		}	
		System.out.println("======");
		
		for(Country c: p1.getCountries()){
			if(c.getName().equals("Arvi")){
				assertEquals(20, c.getTroopNum());
			}
		}	
		
	}

}
