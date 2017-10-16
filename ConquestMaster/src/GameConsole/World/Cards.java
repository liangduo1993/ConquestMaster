package gameConsole.world;

import gameConsole.player.Player;

/**
 * This class defines the Cards that player can exchange to troops
 *
 */
public class Cards{

	   private int type; // 0, 1, 2 three types
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
	    * Gives all of the killed player's cards to their killer
	    * @param giver player who was killed
	    * @param receiver player who killed giver
	    */
	   public void giveCards(Player giver, Player receiver){
	         receiver.addAllCard(giver.getOnHand());
	   }
	   
	   public int getType()
	   {
	      return type;
	   }
	   
}
