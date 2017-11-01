package GameConsoleTest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Core.GameState;
import GameConsole.Core.Group;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;
import GameConsole.View.WindowMain;

/**
 * this class is a test class for testing if player owns the corresponding
 * counties and can correctly get bonus from initial phase.
 */
public class PlayerTest {
	private Player player;
	private Player player2;
	private GameState state;
	private Group group;
	private WindowMain window;

	/**
	 * setUp initial game, loading a new map and adding new players.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		// set gamestate
		// set group
		group = new Group();
		window = new WindowMain();
		ArrayList<Player> players = new ArrayList<Player>();

		for (int i = 0; i < 3; ++i) {
			players.add(new Player(null, null, null));
		}

		group.setPlayers(players);
		System.out.println(group.getPlayers().size());

		state = new GameState(window, "resources/ConquestMaps/Atlantis.map");
		state.setAllPlayers(group);

		// set player
		player = new Player("testPlayer", null, state);
		ArrayList<Country> temp = new ArrayList<Country>();
		for (int i = 0; i < 14; ++i) {
			temp.add(new Country());
		}
		player.setCountries(temp);
		
		// set player2
		player2 = new Player("testPlayerDefender", null, state);
		ArrayList<Country> temp2 = new ArrayList<Country>();
		for (int i = 0; i < 2; ++i) {
			temp2.add(new Country());
		}
		player2.setCountries(temp2);

	}

	/**
	 * test class: Player, function: getBonus(). check the new game have the
	 * correctly corresponding players. check if player can get the bonus in the
	 * initial phase.
	 */
	@Test
	public void testGetBonus() {
		System.out.println("gamestate:" + state.getAllPlayers().getPlayers().size());
		System.out.println(player.getCountries().size());
		System.out.println(player.getBonus());
		assertEquals(21, player.getBonus());
	}
	
	/**
	 * test class: Player, function: moveTroops(). check the valid move after player
	 * conquered a country
	 * 
	 */
	@Test
	public void testMoveTroops() {
		Country c1 = player.getCountries().get(0);
		Country c2 = player.getCountries().get(1);
		c1.addInfrantry(5);
		c2.addInfrantry(4);
		player.moveTroops(c1, c2, 2);
		assertEquals(3, c1.getTroops().size());
		assertEquals(6, c2.getTroops().size());
	}
	
	/**
	 * test class: Player, function: loseGame(). check the end of the game
	 * when a player became the only player
	 * 
	 */
	@Test
	public void testLoseGame() {
		for (int i=1; i<group.getPlayers().size();i++){
			group.getPlayers().remove(i);
		}
		state.setCurrPlayer(group.getPlayers().get(0));
		assertEquals(state.checkWinner(), state.getCurrPlayer());
	}
	
	/**
	 * test class: Player, function attack(). check if the attacker and
	 * defender are valid.
	 */
	@Test
	public void testAttack() {
		Country c1 = player.getCountries().get(0);
		Country c2 = player2.getCountries().get(0);
		
		try {
			player.attack(c1, c2, 1, 1);
		} catch (Exception ex) {
			assertThat(ex.getMessage(), containsString("The attacker and defender is not right!"));
		}
	}
	

}
