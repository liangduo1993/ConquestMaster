package GameConsole.World;

import java.util.*;

import GameConsole.Player.Player;

public class Cards{

	   private int type;
	   //private boolean clicked;
	   private Player player;
	   private int x;
	   private int y;
	   private boolean visible;
	   //private static BufferedImage[] images = getImages();
	   
	   public Cards(Player player){
	      this.player = player;
	   }
	   
	   public String toString(){
		return "This is a "+type+" card";
		   
	   }
	   
	   /**
	    * If player conquered at least one territory during the attack phase, he will be given a card
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
		  //for(int i = giver.getHand().getList().size() - 1; i >= 0; i--)
			    // receiver.getHand().addCard(giver.getHand().drawCard());
	         receiver.addAllCard(giver.getOnHand());
	   }
	   
	   /**
	    * owns 3 cards (same/different) sorts can exchange armies. Start at 5, 5 more each time
	    * if owns more than 5 cards, must exchange 3 of them
	    */
	   public void exchangeCards(Player player){
		   
	   }
	   
	   /*
	   //------------------------------------------------------
	   //Returns whether the card is selected
	   //@return  clicked
	   //------------------------------------------------------
	   public boolean isClicked()
	   {
	      return(clicked);
	   }
	   
	   */
	   
	   //------------------------------------------------------
	   //Returns the card's type
	   //@return type[int]
	   //------------------------------------------------------
	   public int getType()
	   {
	      return type;
	   }
	   //------------------------------------------------------
	   //Sets the card to be visible
	   //@param x the x coordinate to display the card at
	   //@param y the y coordinate to display the card at
	   //POSTCONDITION: Card is visible and clickable when drawn
	   //------------------------------------------------------
	   public void setVisible(int x, int y)
	   {
	      visible = true;
	      this.x = x;
	      this.y = y;
	   }
	   //------------------------------------------------------
	   //Sets the card to be invisible
	   //POSTCONDITION: Card is invisible and will not be drawn
	   //------------------------------------------------------
	   public void setInvisible()
	   {
	      visible = false;
	   }
	   
	   /*
	   
	   //------------------------------------------------
	   //@Override
	   //Check and take care of actions when the card is clicked on
	   //------------------------------------------------
	   public void click()
	   {
	      if(visible)
	      {
	         if(Mouse.getX() > x && Mouse.getX() < x + images[type].getWidth() && Mouse.getY() > y && Mouse.getY() < y + images[type].getHeight()&&Mouse.mouseClicked())
	         {
	            clicked = !clicked;
	         }
	      }
	   }
	   //------------------------------------------------
	   //@Override
	   //Draw the card on the screen if visible.
	   //------------------------------------------------
	   public void draw(Graphics g)
	   {
	      if(visible)
	      {
	         g.drawImage(images[type], x, y, null);
	         g.setColor(Color.BLACK);
	         if(country!=null)
	            g.drawString(country.getName(),x+65-2*country.getName().length(),y+200);
	         if(Mouse.getX() > x && Mouse.getX() < x + images[type].getWidth() && Mouse.getY() > y && Mouse.getY() < y + images[type].getHeight())
	         {
	            g.setColor(new Color(100, 100, 100, 100));
	            g.fillRect(x,y,images[type].getWidth(), images[type].getHeight());
	         }
	         if(clicked)
	         {
	            g.setColor(Color.YELLOW);
	            for(int i = 0; i < 6; i++)
	               g.drawRect(x+i,y+i,images[type].getWidth()-2*i, images[type].getHeight()-2*i);
	         }
	      }
	   }
	   
	  
	   
	   
	   //------------------------------------------------
	   //Loads the static images
	   //@return the images of the cards
	   //PRECONDITION: Files card[1-4].png exist
	   //------------------------------------------------
	   private static BufferedImage[] getImages()
	   {
	      BufferedImage[] bufferedimages = new BufferedImage[4];
	      try
	      {
	         for(int i = 1; i <= 4; i++)
	            bufferedimages[i-1] = ImageIO.read(new File("Card"+i+".png"));
	      }
	      catch(Exception e)
	      {e.printStackTrace();}
	      return(bufferedimages);
	   }
	    */
	   



}
