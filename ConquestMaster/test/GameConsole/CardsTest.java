package GameConsole;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import GameConsole.World.Cards;
import GameConsole.Player.Player;

/**
 * 
 * This class is a test class for class Cards and the actions toward cards.
 *
 */
public class CardsTest {
	/**
	 * Set up function, to do some initial work. 
	 * @throws Exception If the target map is not valid, it would throw an exception.
	 */
		@Before
		public void setUp(){
			Player p1 = new Player("a", Color.cyan, null);
			Player p2 = new Player("b", Color.magenta, null);
			
		}
		
		/**
		 * test class: ConquestMap. function: addContinent(). Check if add a new
		 * continent the size of the continents list is changed or not.
		 */
		@Test
		public void testAddCards() {

		}

}
