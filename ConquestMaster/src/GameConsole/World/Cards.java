package GameConsole.World;

import java.util.*;

import GameConsole.Player.Player;

/**
 * This class defines the Cards that player can exchange to troops
 *
 */
public class Cards{

	   private int type;
	   private Player player;
	   
	   public Cards(Player player){
	      this.player = player;
	   }
	   
	   public String toString(){
		return "This is a "+type+" card";
		   
	   }
	   
	   /**
	    * If player conquered at least one territory during the attack phase, he will be given a card
	    * 
	    */
	   public void addRandomTypeCard(){
		   this.type=(int) (Math.random()*3);
	   }
	   

	   /**
	    * Gives all of the killed Player's cards to their killer
	    * @param giver Player who was killed
	    * @param receiver Player who killed giver
	    */
	   public void giveCards(Player giver, Player receiver){
	         receiver.addAllCard(giver.getOnHand());
	   }
	   
	   public int getType()
	   {
	      return type;
	   }
	   
}
