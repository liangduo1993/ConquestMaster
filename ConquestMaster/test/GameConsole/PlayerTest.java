package GameConsole;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Player.Group;
import GameConsole.Player.Player;
import GameConsole.Window.WindowMain;
import GameConsole.World.Country;
import GameConsole.World.GameState;

public class PlayerTest {
	private Player player;
	private GameState state;
	private Group group;
	private WindowMain window;
	@Before
	public void setUp() throws Exception {
		//set gamestate
		//set group
		group = new Group();
		window = new WindowMain();
		ArrayList<Player> players = new ArrayList<Player>();
		
		for(int i = 0 ; i < 3 ;++i){
			players.add(new Player(null , null, null));
		}
		
		group.setPlayers(players);
		System.out.println(group.getPlayers().size());
		
		state = new GameState(window , "resources/ConquestMaps/Atlantis.map");
		state.setAllPlayers(group);
		
		
		//set player
		player = new Player("testPlayer" , null , state);
		ArrayList<Country> temp = new ArrayList<Country>();
		for(int i = 0 ; i < 14 ;++i){
			temp.add(new Country());
		}
		player.setCountries(temp);

	}

	@Test
	public void testGetBonus() {
		System.out.println("gamestate:"+ state.getAllPlayers().getPlayers().size());
		System.out.println(player.getCountries().size());
		System.out.println(player.getBonus());
		assertEquals(21,player.getBonus());
	}

}
