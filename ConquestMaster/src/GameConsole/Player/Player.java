package GameConsole.Player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GameConsole.Army.Infantry;
import GameConsole.Army.Troop;
import GameConsole.World.Card;
import GameConsole.World.Cards;
import GameConsole.World.Continent;
import GameConsole.World.Country;
import GameConsole.World.GameState;
import GameConsole.World.World;

/**
 * This class represents all of the data and funcionality that a player would have.
 * All of the actions that a player would do eventually comes back to this class.
 */
public class Player extends Observable {
	private String name;
	private Color color;
	private ArrayList<Troop> numTroops = new ArrayList<Troop>(); // all of the
																	// troops of
																	// a user
	private ArrayList<Country> countries = new ArrayList<Country>(); // all of
																		// the
																		// countries
																		// a
																		// user
																		// owns
	private ArrayList<Card> hand = new ArrayList<Card>(); // all of the cards in
															// a player's hand
	private ArrayList<Cards> onhand = new ArrayList<Cards>(); // all of the
																// cards in a
																// player's hand
	private GameState game; // the state of the game
	private JFormattedTextField playerTextName; // the fields at the top that
												// contains the player's name
	private int totalCardsExchange = 0; // Record how many times does a
										// player has changed his cards
	private boolean hasMoved = false;
	private int initTroop;

	/**
	 * Constructor method
	 * @param name the player name with String type
	 * @param color the color of the player with Color type
	 * @param game the game state with GameState type
	 */
	public Player(String name, Color color, GameState game) {
		this.name = name;
		this.color = color;
		this.game = game;
	}

	/**
	 * To check whether the player has moved the troop or not
	 * @return true if the palyer has moved otherwise return flase
	 */
	public boolean isHasMoved() {
		return hasMoved;
	}

	/**
	 * To set the status that the player has moved the troop or not
	 * @param hasMoved the status that the player has moved the troop or not with boolean type
	 */
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	/**
	 * To get the player name
	 * @return the player name with String type
	 */
	public String getName() {
		return name;
	}

	
	
	public int getInitTroop() {
		return initTroop;
	}

	public void setInitTroop(int initTroop) {
		this.initTroop = initTroop;
	}

	/**
	 * To set the player name
	 * @param name the desired the player name that want to be set with String type
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * To get the player's color
	 * @return the player color with Color type
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * To set the player's color
	 * @param color the desired the player color that want to be set with Color type
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * To get the player's troops list
	 * @return the player's troops list with ArrayList type
	 */
	public ArrayList<Troop> getNumTroops() {
		return numTroops;
	}

	/**
	 * To set the player's troops list
	 * @param numTroops the desired player's troops list with ArrayList type
	 */
	public void setNumTroops(ArrayList<Troop> numTroops) {
		this.numTroops = numTroops;
	}

	/**
	 * To get the player's countries list
	 * @return the player's countries list with ArrayList type
	 */
	public ArrayList<Country> getCountries() {
		return countries;
	}

	/**
	 * To set the countries list to belong to the player
	 * @param countries the countries list desired to be set to belong to the player with ArrayList type
	 */
	public void setCountries(ArrayList<Country> countries) {
		this.countries = countries;
	}

	/**
	 * To get the player's hand cards list
	 * @return the player's hand cards list with  ArrayList type
	 */
	public ArrayList<Card> getHand() {
		return hand;
	}

	/**
	 * To set the player's hand cards list
	 * @param hand the hand cards list desired to be set to belong to the player with ArrayList type
	 */
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	/**
	 * To get the player's hand cards list
	 * @return the player's hand cards list with  ArrayList type
	 */
	public ArrayList<Cards> getOnHand() {
		return onhand;
	}

	/**
	 * Add a certain type of card to player
	 * 
	 * @param cards
	 *            by LZ
	 */
	public void addCard(Cards cards) {
		this.onhand.add(cards);
	}

	/**
	 * Add all cards of the given player to receive player
	 * 
	 * @param onhand
	 *            by LZ
	 */
	public void addAllCard(ArrayList<Cards> onhand) {
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
	 * @param g the desired game state wanted to be set with Gamestate type
	 */
	public void setgame(GameState g) {
		this.game = g;
	}

	/**
	 * To check if the country is belong to the player
	 * @param countryName the country name with String type
	 * @return country if the player owns the country otherwise return null
	 */
	public Country checkIfOwned(String countryName) {
		for (Country c : this.countries) {
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
	 * @param origin the country that selected with Country type
	 * @param countryName the country name with String type
	 * @return country if the country is able to perform attack otherwise return null
	 */
	public Country checkIfCanAttack(Country origin, String countryName) {

		if ((this.checkIfOwned(countryName) != null) || (origin.getTroops().size() == 1)) { // will
																							// return
																							// null
																							// if
																							// the
																							// player
																							// already
																							// owns
																							// the
																							// country
																							// or
																							// if
																							// there
																							// is
																							// only
																							// one
																							// troop
			return null;
		}

		for (Country c1 : origin.getBorderingCountries()) { // checking to see
															// if the country is
															// bordering the
															// country
			if (c1.getName().equals(countryName)) {
				return c1;
			}
		}

		return null;
	}

	/**
	 * To check the selected country is able to move the troop to another
	 * @param origin the selected country with Country type
	 * @param countryName the country name with String type
	 * @return country with Country type if the selected country is able move troop otherwise return null
	 */
	public Country checkIfCanMove(Country origin, String countryName) {
		if ((this.checkIfOwned(countryName) == null) || (origin.getTroops().size() == 1)) { // will
																							// return
																							// null
																							// if
																							// the
																							// player
																							// already
																							// owns
																							// the
																							// country
																							// or
																							// if
																							// there
																							// is
																							// only
																							// one
																							// troop
			return null;
		}

		for (Country c1 : origin.getBorderingCountries()) { // checking to see
															// if the country is
															// bordering the
															// country
			if (c1.getName().equals(countryName)) {
				return c1;
			}
		}

		return null;
	}

	/**
	 * To perform attack action
	 * @param c1 the selected country to perform attack action with Country type
	 * @param c2 the selected target to be attacked with Country type
	 */
	public void attack(Country c1, Country c2) {
		Random rand = new Random(); // so we can create random numbers to
									// simulate rolling (omg I am such a raver)
		ArrayList<Integer> attackRoll = new ArrayList<Integer>(); // will
																	// contain
																	// all of
																	// the
																	// attacker's
																	// rolls
		ArrayList<Integer> defendRoll = new ArrayList<Integer>(); // will
																	// contain
																	// all of
																	// the
																	// defender's
																	// roll

		for (int i = 0; i < Math.min(c1.getTroops().size() - 1, 3); i++) { // populating
																			// the
																			// attacker's
																			// rolls
			if (c1.getPlayer().getName().equals("Sam")) {
				Integer tempInt = new Integer(rand.nextInt(1) + 1);
				attackRoll.add(tempInt);
			} else {
				Integer tempInt = new Integer(rand.nextInt(6) + 1);
				attackRoll.add(tempInt);
			}
		}
		for (int i = 0; i < Math.min(c2.getTroops().size(), 2); i++) { // populating
																		// the
																		// defender's
																		// rolls
			if (c2.getPlayer().getName().equals("Sam")) {
				Integer tempInt = new Integer(rand.nextInt(1) + 1);
				defendRoll.add(tempInt);
			} else {
				Integer tempInt = new Integer(rand.nextInt(6) + 1);
				defendRoll.add(tempInt);
			}
		}
		String diceString = "Attacker rolled:\n";
		for (int i = 0; i < attackRoll.size(); i++) {
			if (i != Math.min(c1.getTroops().size(), 3) - 1) {
				diceString += attackRoll.get(i) + ", ";
			} else {
				diceString += attackRoll.get(i);
			}
		}
		diceString += "\nDefender rolled:\n";
		for (int i = 0; i < defendRoll.size(); i++) {
			if (i != Math.min(c2.getTroops().size(), 2) - 1) {
				diceString += defendRoll.get(i) + ", ";
			} else {
				diceString += defendRoll.get(i) + "\n";
			}
		}
		JOptionPane.showMessageDialog(null, diceString);

		while (!defendRoll.isEmpty() && !attackRoll.isEmpty()) { // comparing
																	// the rolls
			if (Collections.max(attackRoll) > Collections.max(defendRoll)) { // if
																				// attacker
																				// won
				c2.getPlayer().getNumTroops().remove(c2.getPlayer().getNumTroops().size() - 1); // remove
																								// one
																								// troop
																								// from
																								// defending
																								// player
				c2.getTroops().remove(c2.getTroops().size() - 1); // remove one
																	// troop
																	// from
																	// defending
																	// country
			} else { // if defender won
				this.numTroops.remove(this.numTroops.size() - 1); // remove one
																	// troop
																	// from
																	// attacking
																	// player
				c1.getTroops().remove(c1.getTroops().size() - 1); // remove one
																	// troop
																	// from
																	// defending
																	// player
			}
			attackRoll.remove((Integer) Collections.max(attackRoll)); // removing
																		// highest
																		// roll
																		// of
																		// attacker
			defendRoll.remove((Integer) Collections.max(defendRoll)); // removing
																		// highest
																		// roll
																		// of
																		// defender
		}

		if (c2.getTroops().size() == 0) { // if the defending country was taken
											// over
			c2.getPlayer().removeCountry(c2);
			c2.setPlayer(this);
			this.addCountry(c2);

			int moveNum = 0;
			JPanel numPanel = new JPanel();
			numPanel.add(new JLabel("Congrats you conquered " + c2.getName() + " with " + c1.getName()
					+ ". How many troops would you like to add?"));
			DefaultComboBoxModel<String> selection = new DefaultComboBoxModel<String>();
			for (int i = 1; i < c1.getTroops().size(); i++) {
				selection.addElement(Integer.toString(i));
			}
			JComboBox<String> comboBox = new JComboBox<String>(selection);
			numPanel.add(comboBox);
			int result = JOptionPane.showConfirmDialog(null, numPanel, "Number of Troops", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.CANCEL_OPTION) {
				moveNum = 1; // if they cancel it will just move one
			} else {
				moveNum = Integer.parseInt(comboBox.getSelectedItem().toString());
			}
			moveNum = Integer.parseInt(comboBox.getSelectedItem().toString());
			c2.addInfrantry(moveNum); // adding the troops to the conquered
										// country
			c1.removeTroops(moveNum); // removing troops from the origin country
		}
	}

	/**
	 * To remove troop from troop list
	 * @param numToRemove the number the troops that need to be removed with int type
	 */
	public void removeTroops(int numToRemove) {
		for (int i = 0; i < numToRemove; i++) {
			this.numTroops.remove(this.numTroops.size() - 1);
		}
	}

	/**
	 * To perform move troop action
	 * @param c1 the selected country to perform move troops action with Country type
	 * @param c2 the selected target country to receive the moved troop with Country type
	 * @param toMove the number of the troops that need to be moved
	 */
	public void moveTroops(Country c1, Country c2, int toMove) {
		c1.removeTroops(toMove);
		c2.addInfrantry(toMove);
	}

	/**
	 * Method to claim the player fails and remove the player from the players list
	 */
	public void loseGame() {
		JOptionPane.showMessageDialog(null, this.name + " has lost the game!");
		this.game.getAllPlayers().getPlayers().remove(this);
		this.game.setNextPlayer();
		System.out.println("Current Player is at this end game point is " + game.getCurrPlayer().getName());
		if (game.checkWinner() == game.getCurrPlayer()) {
			game.getWindow().initializeEndGame();
			System.out.println("End game was called");
		}

	}

	/**
	 * To give the player the hand cards
	 */
	public void giveCards() {
		for (int i = 0; i < 4; i++) {
			Cards c = new Cards(this);
			c.addRandomTypeCard();
			this.onhand.add(c);
			System.out.println(c);
		}
	}

	/**
	 * To get the initial army bonus in the setup phase
	 * @return the number of the got armies
	 */
	public int getBonus() {
		int firstRound = game.firstRound;
		if (firstRound == 1) {
			if (game.getAllPlayers().getPlayers().size() == 2) {
				return 40 - this.getCountries().size();
			} else if (game.getAllPlayers().getPlayers().size() == 3) {
				return 35 - this.getCountries().size();
			} else if (game.getAllPlayers().getPlayers().size() == 4) {
				return 30 - this.getCountries().size();
			} else if (game.getAllPlayers().getPlayers().size() == 5) {
				return 25 - this.getCountries().size();
			} else {
				return 20 - this.getCountries().size();
			}

		} else {

			int reward = this.getCountries().size() / 3;
			boolean isLoop = true;

			while (this.onhand.size() >= 3 && isLoop) {
				// System.out.println("Must exchange 3 of them");
				int cardType0 = 0;
				int cardType1 = 0;
				int cardType2 = 0;

				for (Cards c : this.onhand) {
					if (c.getType() == 0) {
						cardType0 = cardType0 + 1;
					}
					if (c.getType() == 1) {
						cardType1 = cardType1 + 1;
					}
					if (c.getType() == 2) {
						cardType2 = cardType2 + 1;
					}
				}

				JPanel numPanel = new JPanel();
				numPanel.add(new JLabel("Chose Cards!"));
				DefaultComboBoxModel<String> selection = new DefaultComboBoxModel<String>();
				if (cardType0 > 2) {
					selection.addElement("Change 3*Type0");
				}
				if (cardType1 > 2) {
					selection.addElement("Change 3*Type1");
				}
				if (cardType2 > 2) {
					selection.addElement("Change 3*Type2");
				}
				if (cardType0 > 0 && cardType1 > 0 && cardType2 > 0) {
					selection.addElement("Change 1*Type0&Type1&Type2");
				}

				JComboBox<String> comboBox = new JComboBox<String>(selection);
				numPanel.add(comboBox);

				if (selection.getSize() > 0) {
					int result = JOptionPane.showConfirmDialog(null, numPanel, "Use Cards to Exchange Troops",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == 1) {
						if (this.onhand.size() < 5)
							isLoop = false;
					} else {

						String selected = comboBox.getSelectedItem().toString();

						totalCardsExchange = totalCardsExchange + 1;
						reward = reward + (totalCardsExchange * 5);

						switch (selected) {
						case ("Change 3*Type0"): {
							int i = 0;
							Iterator<Cards> it = onhand.iterator();
							while (it.hasNext()) {
								Cards c = it.next();
								if (c.getType() == 0 && i < 3) {
									it.remove();
									i++;
								}
							}
							break;
						}
						case ("Change 3*Type1"): {
							int i = 0;
							Iterator<Cards> it = onhand.iterator();
							while (it.hasNext()) {
								Cards c = it.next();
								if (c.getType() == 1 && i < 3) {
									it.remove();
									i++;
								}
							}
						}
						case ("Change 3*Type2"): {
							int i = 0;
							Iterator<Cards> it = onhand.iterator();
							while (it.hasNext()) {
								Cards c = it.next();
								if (c.getType() == 2 && i < 3) {
									it.remove();
									i++;
								}
							}
							break;
						}
						case ("Change 1*Type0&Type1&Type2"): {
							int i = 0;
							int j = 0;
							int[] deleted = { 9, 9, 9 };
							Iterator<Cards> it = onhand.iterator();
							while (it.hasNext()) {
								Cards c = it.next();
								if ((c.getType() != deleted[0] && c.getType() != deleted[1]) && i != 3) {
									// System.out.println("j="+j);
									i++;
									deleted[j] = c.getType();
									j++;
									it.remove();
								}
							}
							break;
						}
						}
						System.out.println("after change===========");
						for (Cards c : onhand) {
							System.out.println(c);
						}
						System.out.println("after change===========");
						setChanged();
						notifyObservers();
					}
				} else
					break;
			}
			// if(this.onhand.size()>=3){
			//
			// int cardType0 = 0;
			// int cardType1 = 0;
			// int cardType2 = 0;
			//
			// for (Cards c:this.onhand){
			// if(c.getType()==0){
			// cardType0=cardType0+1;
			// }
			// if(c.getType()==1){
			// cardType1=cardType1+1;
			// }
			// if(c.getType()==2){
			// cardType2=cardType2+1;
			// }
			// }
			//
			// if((cardType0<3&&cardType1<3&&cardType2<3)&&(cardType0==0||cardType1==0||cardType2==0)){
			// System.out.println("Dont have valid card type to exchange
			// troops!");
			// }
			// else{
			// System.out.println("Can exchange 3 of them");
			//
			// JPanel numPanel = new JPanel();
			// numPanel.add(new JLabel("Chose Cards!"));
			// DefaultComboBoxModel<String> selection = new
			// DefaultComboBoxModel<String>();
			// if(cardType0>2){
			// selection.addElement("Change 3*Type0");
			// }
			// if(cardType1>2){
			// selection.addElement("Change 3*Type1");
			// }
			// if(cardType2>2){
			// selection.addElement("Change 3*Type2");
			// }
			// if(cardType0>0&&cardType1>0&&cardType2>0){
			// selection.addElement("Change 1*Type0&Type1&Type2");
			// }
			//
			// JComboBox<String> comboBox = new JComboBox<String>(selection);
			// numPanel.add(comboBox);
			// int result = JOptionPane.showConfirmDialog(null, numPanel, "Use
			// Cards to Exchange Troops", JOptionPane.OK_CANCEL_OPTION,
			// JOptionPane.QUESTION_MESSAGE);
			// String selected = comboBox.getSelectedItem().toString();
			// if(result == JOptionPane.OK_OPTION) {
			// totalCardsExchange=totalCardsExchange+1;
			// reward=reward+(totalCardsExchange*5);
			//
			// switch(selected){
			// case ("Change 3*Type0"):{
			// int i=0;
			// Iterator<Cards> it = onhand.iterator();
			// while (it.hasNext())
			// {
			// Cards c = it.next();
			// if (c.getType()==0&&i<3)
			// {
			// it.remove();
			// i++;
			// }
			// }
			// break;
			// }
			// case ("Change 3*Type1"):{
			// int i=0;
			// Iterator<Cards> it = onhand.iterator();
			// while (it.hasNext())
			// {
			// Cards c = it.next();
			// if (c.getType()==1&&i<3)
			// {
			// it.remove();
			// i++;
			// }
			// }
			// }
			// case ("Change 3*Type2"):{
			// int i=0;
			// Iterator<Cards> it = onhand.iterator();
			// while (it.hasNext())
			// {
			// Cards c = it.next();
			// if (c.getType()==2&&i<3)
			// {
			// it.remove();
			// i++;
			// }
			// }
			// break;
			// }
			// case ("Change 1*Type0&Type1&Type2"):{
			// int i=0;
			// int j=0;
			// int [] deleted={9,9,9};
			// Iterator<Cards> it = onhand.iterator();
			// while (it.hasNext())
			// {
			// Cards c = it.next();
			// if ((c.getType()!=deleted[0]||c.getType()!=deleted[1])&&i!=3)
			// {
			// System.out.println("j="+j);
			// i++;
			// deleted[j]=c.getType();
			// j++;
			// it.remove();
			// }
			// }
			// break;
			// }
			// }
			//
			//// for (Cards c:onhand){System.out.println(c);}
			// }
			// else {
			// System.out.println("Do nothing!");
			// }
			// }
			// }
			// else{
			// System.out.println("Not enough cards!");
			// }
			boolean owned = true;
			if (this.countries.size() == 0) {
				this.loseGame();
				return 0;
			}
			// World world = this.countries.get(0).getContinent().getWorld();
			World world = game.getWorld();
			System.out.println(world.getContinents().size());

			for (Continent con : world.getContinents()) {
				owned = true;
				for (Country cou : con.getCountries()) {
					if (!(cou.getPlayer().equals(this))) {
						owned = false;
						// break;
					}
					// System.out.println(cou.getPlayer().getName());
					// System.out.println(this.name);
				}
				System.out.println(con.getName());
				if (owned) {
					reward += con.getBonus();
				}
			}

			System.out.println("reward " + reward);
			System.out.println("size " + this.countries.size());
			return reward;
		}
	}

	/**
	 * To add the troop to the troop list
	 * @param numTroops the number of the troop that need to be added
	 */
	public void addInfrantry(int numTroops) {
		for (int i = 0; i < numTroops; i++) {
			Infantry temp = new Infantry();
			this.numTroops.add(temp);
		}
	}

	/**
	 * To add a country to the player's countries list
	 * @param c a country that need to be added with Country type
	 */
	public void addCountry(Country c) {
		this.countries.add(c);
	}

	/**
	 * To remove a country from the player's countries list
	 * @param c a country that need to be removed with Country type
	 */
	public void removeCountry(Country c) {
		this.countries.remove(c);
	}

	/**
	 * To get the playerName in the text
	 * @return playertextname with JFormattedTextField type
	 */
	public JFormattedTextField getPlayerTextName() {
		return playerTextName;
	}

	/**
	 * To set the playerTextName
	 * @param playerTextName the playerTextName need to be set
	 */
	public void setPlayerTextName(JFormattedTextField playerTextName) {
		this.playerTextName = playerTextName;
	}
}
