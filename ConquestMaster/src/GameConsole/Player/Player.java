package gameConsole.player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gameConsole.army.Infantry;
import gameConsole.army.Troop;
import gameConsole.world.Card;
import gameConsole.world.Cards;
import gameConsole.world.Continent;
import gameConsole.world.Country;
import gameConsole.world.GameState;
import gameConsole.world.World;


/**
 * This class represents all of the data and functionality that a player would have.
 * All of the actions that a player would do eventually comes back to this class.
 */
public class Player {
	private String name;
	private Color color;
	private ArrayList<Troop> numTroops = new ArrayList<Troop>(); // all of the troops of a user
	private ArrayList<Country> countries = new ArrayList<Country>(); // all of the countries a user owns
	private ArrayList<Card> hand = new ArrayList<Card>(); // all of the cards in a player's hand
	private ArrayList<Cards> onhand = new ArrayList<Cards>(); // all of the cards in a player's hand
	private GameState game; // the state of the game
	private JFormattedTextField playerTextName; // the fields at the top that contains the player's name
	private static int totalCardsExchange=0; //Record how many times does a player has changed his cards
	private boolean hasMoved = false;

	/**
	 * constructor method with incoming parameters.
	 * @param name player's name with String type
	 * @param color player's color with Color type
	 * @param game the game state with GameState type
	 */
	public Player(String name, Color color, GameState game) { 
		this.name = name;
		this.color = color;
		this.game = game;
	}

	/**
	 * To justify whether the player has moved his army or not
	 * @return true if the player has moved his army otherwise false
	 */
	public boolean isHasMoved() {
		return hasMoved;
	}

	/**
	 * To set the status of the player's move action
	 * @param hasMoved the move status with boolean type
	 */
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	/**
	 * To get the player's name
	 * @return player's name with String type
	 */
	public String getName() {
		return name;
	}

	/**
	 * To set the player's name
	 * @param name the player name with String type
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * To get the player's color
	 * @return the player's color with Color type
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * To set the player's color
	 * @param color the color desired to be setted to the player
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * To get all the troops belong to the player
	 * @return the troops
	 */
	public ArrayList<Troop> getNumTroops() {
		return numTroops;
	}

	/**
	 * To set the troops
	 * @param numTroops  the troops belong to player
	 */
	public void setNumTroops(ArrayList<Troop> numTroops) {
		this.numTroops = numTroops;
	}

	/**
	 * To get all the countries which belong to the player
	 * @return the countries belong to the player
	 */
	public ArrayList<Country> getCountries() {
		return countries;
	}

	/**
	 * To set the countries list to belong to the player
	 * @param countries the countries list desired to be setted to belong to the player
	 */
	public void setCountries(ArrayList<Country> countries) {
		this.countries = countries;
	}

	/**
	 * To get the cards in player's hand
	 * @return the player's hand card list
	 */
	public ArrayList<Card> getHand() {
		return hand;
	}

	/**
	 * To set the player's hand card
	 * @param hand the desired hand cards list need to be setted to the player
	 */
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	/**
	 * To get the cards in player's hand
	 * @return the player's hand card list
	 */
	public ArrayList<Cards> getOnHand() {
		return onhand;
	}

	/**
	 * Add a certain type of card to player
	 * @param cards
	 * by LZ
	 */
	public void addCard(Cards cards)
	{
		this.onhand.add(cards);
	}

	/**
	 * Add all cards of the given player to receive player
	 * @param onhand
	 * by LZ
	 */
	public void addAllCard(ArrayList<Cards> onhand)
	{
		this.onhand.addAll(onhand);
	}

	/**
	 * To get the game state
	 * @return the game state with GameState type
	 */
	public GameState getGame() {
		return game;
	}

	/**
	 * To set the game state
	 * @param g the desired game state wanted to be setted with Gamestate type
	 */
	public void setgame(GameState g) {
		this.game = g;
	}

	/**
	 * To check whether the player owns the country
	 * @param countryName the country need to be checked
	 * @return the country if the country belongs to the player otherwise return null
	 */
	public Country checkIfOwned(String countryName) {
		for(Country c : this.countries) {
			if (c.getName().equals(countryName)) {
				return c;
			}
		}
		
		return null;
	}

	/**
	 * This class will take in a country as well as the country it is going to attack.
	 * It will return the object of the country it wants to attack if the player is allowed to
	 * attack that country
	 * @param origin the country the player wants to use to perform the attack action with Country type
	 * @param countryName the country name with String type
	 * @return
	 */
	public Country checkIfCanAttack(Country origin, String countryName) {
		
		if( (this.checkIfOwned(countryName) != null) || (origin.getTroops().size() == 1) ) { // will return null if the player already owns the country or if there is only one troop
			return null;
		}
		
		for(Country c1 : origin.getBorderingCountries()) { // checking to see if the country is bordering the country
			if (c1.getName().equals(countryName)) {
				return c1;
			}
		}
		
		return null;
	}
	


	/**
	 * To check the selected country is able to move the army to another
	 * @param origin the country the player wants to use to perform the move army action with Country type
	 * @param countryName the country name with String type
	 * @return the country if the country is qualified to move the army otherwise return null
	 */
	public Country checkIfCanMove(Country origin, String countryName) {
		if( (this.checkIfOwned(countryName) == null) || (origin.getTroops().size() == 1) ) { // will return null if the player already owns the country or if there is only one troop
			return null;
		}
		
		for(Country c1 : origin.getBorderingCountries()) { // checking to see if the country is bordering the country
			if (c1.getName().equals(countryName)) {
				return c1;
			}
		}
		
		return null;
	}

	/**
	 * To perform the attack action
	 * @param c1 the country to perform attack action with Country type
	 * @param c2 the target country will be attacked with Country type
	 */
	public void attack(Country c1, Country c2) {
		Random rand = new Random(); // so we can create random numbers to simulate rolling (omg I am such a raver)
		ArrayList<Integer> attackRoll = new ArrayList<Integer>(); // will contain all of the attacker's rolls
		ArrayList<Integer> defendRoll = new ArrayList<Integer>(); // will contain all of the defender's roll

		for(int i = 0; i < Math.min(c1.getTroops().size() - 1, 3); i++) { // populating the attacker's rolls
			if(c1.getPlayer().getName().equals("Sam")){
				Integer tempInt = new Integer(rand.nextInt(1) + 1);
				attackRoll.add(tempInt);
			}
			else{
				Integer tempInt = new Integer(rand.nextInt(6) + 1);
				attackRoll.add(tempInt);
			}
		}
		for(int i = 0; i < Math.min(c2.getTroops().size(), 2); i++) { // populating the defender's rolls
			if(c2.getPlayer().getName().equals("Sam")){
				Integer tempInt = new Integer(rand.nextInt(1) + 1);
				defendRoll.add(tempInt);
			}
			else{
				Integer tempInt = new Integer(rand.nextInt(6) + 1);
				defendRoll.add(tempInt);
			}
		}
		String diceString = "Attacker rolled:\n";
		for(int i = 0; i < attackRoll.size(); i++) {
			if(i !=  Math.min(c1.getTroops().size(), 3) - 1) {
				diceString += attackRoll.get(i) + ", ";
			}
			else {
				diceString += attackRoll.get(i);
			}
		}
		diceString += "\nDefender rolled:\n";
		for(int i = 0; i < defendRoll.size(); i++) {
			if(i != Math.min(c2.getTroops().size(), 2) - 1) {
				diceString += defendRoll.get(i) + ", ";				
			}
			else {
				diceString += defendRoll.get(i) + "\n";
			}
		}
		JOptionPane.showMessageDialog(null, diceString);

		while(!defendRoll.isEmpty() && !attackRoll.isEmpty()) { // comparing the rolls
			if(Collections.max(attackRoll) > Collections.max(defendRoll)) { // if attacker won
				c2.getPlayer().getNumTroops().remove(c2.getPlayer().getNumTroops().size() - 1); // remove one troop from defending player
				c2.getTroops().remove(c2.getTroops().size() - 1); // remove one troop from defending country
			}
			else { // if defender won
				this.numTroops.remove(this.numTroops.size() - 1); // remove one troop from attacking player
				c1.getTroops().remove(c1.getTroops().size() - 1); // remove one troop from defending player
			}
			attackRoll.remove((Integer)Collections.max(attackRoll)); //removing highest roll of attacker
			defendRoll.remove((Integer)Collections.max(defendRoll)); // removing highest roll of defender
		}
		
		if(c2.getTroops().size() == 0) { // if the defending country was taken over
			c2.getPlayer().removeCountry(c2);
			c2.setPlayer(this);
			this.addCountry(c2);
			
			int moveNum = 0;
			JPanel numPanel = new JPanel();
			numPanel.add(new JLabel("Congrats you conquered " + c2.getName() + " with " + c1.getName() + ". How many troops would you like to add?"));
			DefaultComboBoxModel<String> selection = new DefaultComboBoxModel<String>();
			for (int i = 1; i < c1.getTroops().size(); i++){
				selection.addElement(Integer.toString(i));
			}
			JComboBox<String> comboBox = new JComboBox<String>(selection);
			numPanel.add(comboBox);
			int result = JOptionPane.showConfirmDialog(null, numPanel, "Number of Troops", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.CANCEL_OPTION) {
				moveNum = 1; // if they cancel it will just move one
			}
			else {
				moveNum = Integer.parseInt(comboBox.getSelectedItem().toString());
			}
			moveNum = Integer.parseInt(comboBox.getSelectedItem().toString());
			c2.addInfrantry(moveNum); // adding the troops to the conquered country
			c1.removeTroops(moveNum); // removing troops from the origin country
		}
	}


	/**
	 * To perform remove troops action
	 * @param numToRemove the number of the troop that need to be remove with int type
	 */
	public void removeTroops(int numToRemove) {
		for(int i = 0; i < numToRemove; i++) {
			this.numTroops.remove(this.numTroops.size() - 1);
		}
	}

	/**
	 * To perform move troop from one country to another action
	 * @param c1 the country that perform move troop with Country type
	 * @param c2 the target country receive the troop moved from c1
	 * @param toMove the number of the troop that want to be moved
	 */
	public void moveTroops(Country c1, Country c2, int toMove) {
		c1.removeTroops(toMove);
		c2.addInfrantry(toMove);
	}

	/**
	 * If the player failed, remove this player from the players list, game is over
	 */
	public void loseGame() {
		JOptionPane.showMessageDialog(null, this.name + " has lost the game!");
		this.game.getAllPlayers().getPlayers().remove(this);
		this.game.setNextPlayer();
		System.out.println("Current player is at this end game point is " + game.getCurrPlayer().getName() );
		if (game.checkWinner() == game.getCurrPlayer()) {
			game.getWindow().initializeEndGame();
			System.out.println("End game was called");		}
		
	}

	/**
	 * To give the player the hand cards
	 */
	public void giveCards(){
		for (int i=0;i<4;i++){
			Cards c = new Cards(this);
			c.addRandomTypeCard();
			this.onhand.add(c);
			System.out.println(c);
		}
	}

	/**
	 * In the game setup phase to initial the number of troop of the players
	 * @return the number the troops that the player will have
	 */
	public int getBonus() {
		int firstRound = game.firstRound;
		if(firstRound ==1){
			if(game.getAllPlayers().getPlayers().size() == 2){
				return 40- this.getCountries().size();
			}else if(game.getAllPlayers().getPlayers().size() == 3){
				return 35- this.getCountries().size();
			}else if(game.getAllPlayers().getPlayers().size() == 4){
				return 30- this.getCountries().size();
			}else if(game.getAllPlayers().getPlayers().size() == 5){
				return 25- this.getCountries().size();
			}else {
				return 20- this.getCountries().size();
			}
		}else{
		
		int reward = this.getCountries().size()/3;
		
		while (this.onhand.size()>=5){
			System.out.println("Must exchange 3 of them");
			int cardType0 = 0;
			int cardType1 = 0;
			int cardType2 = 0;

			for (Cards c:this.onhand){
				if(c.getType()==0){
					cardType0=cardType0+1;
				}
				if(c.getType()==1){
					cardType1=cardType1+1;
				}
				if(c.getType()==2){
					cardType2=cardType2+1;
				}
			}
			
			JPanel numPanel = new JPanel();
			numPanel.add(new JLabel("Chose Cards!"));
			DefaultComboBoxModel<String> selection = new DefaultComboBoxModel<String>();
			if(cardType0>2){
				selection.addElement("Change 3*Type0");
			}
			if(cardType1>2){
				selection.addElement("Change 3*Type1");
			}
			if(cardType2>2){
				selection.addElement("Change 3*Type2");
			}
			if(cardType0>0&&cardType1>0&&cardType2>0){
				selection.addElement("Change 1*Type0&Type1&Type2");
			}
			
			JComboBox<String> comboBox = new JComboBox<String>(selection);
			numPanel.add(comboBox);

			int result = JOptionPane.showConfirmDialog(null, numPanel, "Use Cards to Exchange Troops", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
			String selected = comboBox.getSelectedItem().toString();

			totalCardsExchange=totalCardsExchange+1;
			reward=reward+(totalCardsExchange*5);
			
			switch(selected){
			case ("Change 3*Type0"):{
				int i=0;
				Iterator<Cards> it = onhand.iterator();
				while (it.hasNext()) 
				{
					Cards c = it.next();
					if (c.getType()==0&&i<3) 
					{
						it.remove();
						i++;
					}
				}
				break;
			}
			case ("Change 3*Type1"):{
				int i=0;
				Iterator<Cards> it = onhand.iterator();
				while (it.hasNext()) 
				{
					Cards c = it.next();
					if (c.getType()==1&&i<3) 
					{
						it.remove();
						i++;
					}
				}
			}
			case ("Change 3*Type2"):{
				int i=0;
				Iterator<Cards> it = onhand.iterator();
				while (it.hasNext()) 
				{
					Cards c = it.next();
					if (c.getType()==2&&i<3) 
					{
						it.remove();
						i++;
					}
				}
				break;
			}
			case ("Change 1*Type0&Type1&Type2"):{
				int i=0;
				int j=0;
				int [] deleted={9,9,9};
				Iterator<Cards> it = onhand.iterator();
				while (it.hasNext()) 
				{
					Cards c = it.next();
					if ((c.getType()!=deleted[0]&&c.getType()!=deleted[1])&&i!=3) 
					{
						System.out.println("j="+j);
						i++;
						deleted[j]=c.getType();
						j++;
						it.remove();
					}
				}
				break;
			}
		}
			for (Cards c:onhand){System.out.println(c);}
		}
		if(this.onhand.size()>=3){

			int cardType0 = 0;
			int cardType1 = 0;
			int cardType2 = 0;

			for (Cards c:this.onhand){
				if(c.getType()==0){
					cardType0=cardType0+1;
				}
				if(c.getType()==1){
					cardType1=cardType1+1;
				}
				if(c.getType()==2){
					cardType2=cardType2+1;
				}
			}
			
			if((cardType0<3&&cardType1<3&&cardType2<3)&&(cardType0==0||cardType1==0||cardType2==0)){
				System.out.println("Dont have valid card type to exchange troops!");
			}
			else{
				System.out.println("Can exchange 3 of them");
			
				JPanel numPanel = new JPanel();
				numPanel.add(new JLabel("Chose Cards!"));
				DefaultComboBoxModel<String> selection = new DefaultComboBoxModel<String>();
				if(cardType0>2){
					selection.addElement("Change 3*Type0");
				}
				if(cardType1>2){
					selection.addElement("Change 3*Type1");
				}
				if(cardType2>2){
					selection.addElement("Change 3*Type2");
				}
				if(cardType0>0&&cardType1>0&&cardType2>0){
					selection.addElement("Change 1*Type0&Type1&Type2");
				}
			
				JComboBox<String> comboBox = new JComboBox<String>(selection);
				numPanel.add(comboBox);
				int result = JOptionPane.showConfirmDialog(null, numPanel, "Use Cards to Exchange Troops", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				String selected = comboBox.getSelectedItem().toString();
				if(result == JOptionPane.OK_OPTION) {
				totalCardsExchange=totalCardsExchange+1;
				reward=reward+(totalCardsExchange*5);
			
				switch(selected){
				case ("Change 3*Type0"):{
					int i=0;
					Iterator<Cards> it = onhand.iterator();
					while (it.hasNext()) 
					{
						Cards c = it.next();
						if (c.getType()==0&&i<3) 
						{
							it.remove();
							i++;
						}
					}
					break;
				}
				case ("Change 3*Type1"):{
					int i=0;
					Iterator<Cards> it = onhand.iterator();
					while (it.hasNext()) 
					{
						Cards c = it.next();
						if (c.getType()==1&&i<3) 
						{
							it.remove();
							i++;
						}
					}
				}
				case ("Change 3*Type2"):{
					int i=0;
					Iterator<Cards> it = onhand.iterator();
					while (it.hasNext()) 
					{
						Cards c = it.next();
						if (c.getType()==2&&i<3) 
						{
							it.remove();
							i++;
						}
					}
					break;
				}
				case ("Change 1*Type0&Type1&Type2"):{
					int i=0;
					int j=0;
					int [] deleted={9,9,9};
					Iterator<Cards> it = onhand.iterator();
					while (it.hasNext()) 
					{
						Cards c = it.next();
						if ((c.getType()!=deleted[0]||c.getType()!=deleted[1])&&i!=3) 
						{
							System.out.println("j="+j);
							i++;
							deleted[j]=c.getType();
							j++;
							it.remove();
						}
					}
					break;
				}
				}
		
				for (Cards c:onhand){System.out.println(c);}
				}
				else {
					System.out.println("Do nothing!");
				}
			}
		}
		else{
			System.out.println("Not enough cards!");
		}
		boolean owned = true;
		if(this.countries.size() == 0) {
			this.loseGame();
			return 0;
		}
		//world world = this.countries.get(0).getContinent().getWorld();
		World world = game.getWorld();
		System.out.println(world.getContinents().size());
		
		for(Continent con : world.getContinents()) {
			owned = true;
			for(Country cou : con.getCountries()) {
				if(!(cou.getPlayer().equals(this))) {
					owned = false;
					//break;
				}
				System.out.println(cou.getPlayer().getName());
				System.out.println(this.name);
			}
			System.out.println(con.getName());
			if(owned) {
				reward += con.getBonus();
			}
		}

		System.out.println("reward " + reward);
		System.out.println("size " + this.countries.size());
		return reward;
		}
	}

	/**
	 * To add the infrantry
	 * @param numTroops the number of troops that want to be added to the player
	 */
	public void addInfrantry(int numTroops) {
		for(int i = 0; i < numTroops; i++) {
			Infantry temp = new Infantry();
			this.numTroops.add(temp);
		}
	}

	/**
	 * To add a country to the player's owned countries list
	 * @param c the country need to be added
	 */
	public void addCountry(Country c) {
		this.countries.add(c);
	}

	/**
	 * To remove a country from the player's owned countries list
	 * @param c the country need to be removed
	 */
	public void removeCountry(Country c){
		this.countries.remove(c);
	}

	/**
	 * To get the player name in the textField
	 * @return the playerTextname with JFormattedTextField type
	 */
	public JFormattedTextField getPlayerTextName() {
		return playerTextName;
	}

	/**
	 * To set playerTextName
	 * @param playerTextName the playername need to be setted in the fields with JFormattedTextField type
	 */
	public void setPlayerTextName(JFormattedTextField playerTextName) {
		this.playerTextName = playerTextName;
	}
}
