package GameConsole;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Test;

import GameConsole.Player.Group;
import GameConsole.Player.Player;
import GameConsole.World.Cards;
import GameConsole.World.Continent;
import GameConsole.World.Country;
import GameConsole.World.GameState;
import GameConsole.World.World;

/**
 * 
 * This class is a test class for class Cards and method getBonus().
 *
 */
public class CardsTest {
	private Player p1;
	private Player p2;
	private Cards c;

	/**
	 * test class: Cards. function: addRandomTypeCard(). Check if add a new
	 * random type of card is added.
	 */
	@Test
	public void testAddCards() {
		p1 = new Player("a", Color.cyan, null);
		c = new Cards(p1);
		p1.addCard(c);
		assertEquals(1, p1.getOnHand().size());
	}

	/**
	 * test class: Cards. function: handOverCards(). Check if receiver will
	 * receive all the cards from giver.
	 */
	@Test
	public void testgiveCards() {
		p1 = new Player("a", Color.cyan, null);
		p2 = new Player("a", Color.cyan, null);
		p1.giveCards();// giver will receive some cards
		p2.giveCards();// receiver will receive some cards
		int sum = p1.getOnHand().size() + p2.getOnHand().size();
		Cards c = new Cards(p1);
		c.handOverCards(p1, p2);
		assertEquals(sum, p2.getOnHand().size());

	}

	/**
	 * test class: Player. function: getBonus(). Check if the return of getBonus
	 * will correct in every cases.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testgetBonus() throws Exception {
		String path = "resources/ConquestMaps/Atlantis.map";
		GameState gs = new GameState(null, path);
		World w = gs.getWorld();
		p1 = new Player("a", Color.cyan, gs);
		p2 = new Player("b", Color.cyan, gs);

		for (Continent c : w.getContinents()) {
			for (Country country : c.getCountries()) {
				country.setPlayer(p2);
				p2.addCountry(country);
				System.out.println(country);
			}
		}

		Continent con = new Continent(0, "C");
		Country cou = new Country();
		Group group = new Group();

		w.addContinent(con);
		group.addPlayer(p1);
		group.addPlayer(p2);
		gs.setAllPlayers(group);
		cou.setPlayer(p1);
		con.addCountry(cou);
		p1.addCountry(cou);

		int rewardatR1 = p1.getBonus();
		assertEquals(39, rewardatR1);

		gs.firstRound = 2;
		int rewardatR2 = p1.getBonus();
		assertEquals(3, rewardatR2);

	}

}
