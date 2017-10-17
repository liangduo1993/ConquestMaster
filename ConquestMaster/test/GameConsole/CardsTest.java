package GameConsole;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

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

}
