package GameConsoleTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Core.Group;
import GameConsole.Model.Player.Player;

public class GroupTest {
	private Group group;
	private ArrayList<Player> players;
	
	@Before
	public void setUp() throws Exception {
		group = new Group();
		players =  new ArrayList<Player>(); 
		for(int i = 0 ; i < 3 ;++i){
			players.add(new Player(null , null, null));
		}
		group.setPlayers(players);
		
	}

	@Test
	public void testAddPlayer() {
		System.out.println(players.size());
		assertEquals(3,players.size());
	}

}
