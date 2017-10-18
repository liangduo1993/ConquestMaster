package GameConsole;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Test;

import GameConsole.World.Continent;
import GameConsole.World.World;
import GameConsole.World.Cards;
import GameConsole.World.GameState;
import GameConsole.World.Country;
import GameConsole.Player.Player;
import GameConsole.Player.Group;

/**
 * 
 * This class is a test class for class Cards and method getBonus().
 *
 */
public class CardsTest {
	/**
	* test class: Cards. function: addRandomTypeCard(). Check if add a new
	* random type of card is added.
	*/
	@Test
	public void testAddCards() {
		Player p1 = new Player("a", Color.cyan, null);;
		Cards c = new Cards(p1);
		p1.addCard(c);
		assertEquals(1, p1.getOnHand().size());
	}
	
	/**
	* test class: Cards. function: handOverCards(). Check if receiver will
	* receive all the cards from giver.
	*/
	@Test
	public void testgiveCards() {
		Player giver = new Player("a", Color.cyan, null);
		Player receiver = new Player("a", Color.cyan, null);
		giver.giveCards();//giver will receive some cards
		receiver.giveCards();// receiver will receive some cards
		int sum=giver.getOnHand().size()+receiver.getOnHand().size();
		Cards c = new Cards(receiver);
		c.handOverCards(giver,receiver);
		assertEquals(sum, receiver.getOnHand().size());
		
	}
	
	/**
	* test class: Player. function: getBonus(). Check if the return of 
	* getBonus will correct in every cases.
	 * @throws Exception 
	*/
	@Test
	public void testgetBonus() throws Exception{
		String path = "resources/ConquestMaps/Atlantis.map";
		World w	= new World(path);
		Continent con = new Continent(0, "C");
		GameState gs = new GameState(null, path);
		Group group = new Group();
		Country cou = new Country();
		Player p1 = new Player("a", Color.cyan, gs);
		Player p2 = new Player("b", Color.cyan, gs);
		w.addContinent(con);
		group.addPlayer(p1);
		group.addPlayer(p2);
		gs.setAllPlayers(group);
		cou.setPlayer(p1);
		for (int i=0;i<5;i++){
			con.addCountry(cou);
			p1.addCountry(cou);
		}
		
		int rewardatR1=p1.getBonus();
		assertEquals(35, rewardatR1);
		
		/*
		gs.firstRound = 2;
		int rewardatR2 = p1.getBonus();
		assertEquals(20, rewardatR2);
		*/
		
	}

}
