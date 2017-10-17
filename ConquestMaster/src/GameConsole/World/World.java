package GameConsole.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import GameConsole.Army.Infantry;
import GameConsole.Player.Group;
import GameConsole.Player.Player;
import GameConsole.Window.MapLoader;

/**
 * This class handles with world the player are in and mange the corresponding behaviors
 */
public class World {
	private ArrayList<Continent> continents;
	private ArrayList<Card> cards;
	private ArrayList<Card> deck = new ArrayList<>();
	private MapLoader mapLoader;

	/**
	 * Contruction method
	 * @param path a passing parameter with String type
	 * @throws Exception
	 */
	public World(String path) throws Exception {
		/*
		 * Hardcoded countries, cards, and continent. Will hopefully be able to
		 * programatically read this from json file but in case we don't have
		 * time to design that just making this.
		 */

		this.initialWorld();

		mapLoader = new MapLoader();
		mapLoader.load(path);
		System.out.println("map load");
		mapLoader.setWorld(this);

		if (mapLoader.isWarn() && !mapLoader.validityCheck()) {
			throw new RuntimeException("the map is not valid!");
		}

		for (Continent continent : this.continents) {
			System.out.println("continents:" + continent.getName());
			for (Country country : continent.getCountries()) {
				System.out.println("country:" + country.getName());
			}
		}


	}

	/**
	 * Method to initila the world the player are in
	 */
	private void initialWorld() {
		this.continents = new ArrayList<Continent>();
	}

	public String toString() {
		String retString = "";
		for (Continent c : this.continents) {
			retString += c.toString();
		}
		return retString;
	}

	/**
	 * To add a continent to the continent list
	 * @param c the continent that will be added to the continent list with Continent type
	 */
	public void addContinent(Continent c) {
		continents.add(c);
	}

	/**
	 * To remove a continent to the continent list
	 * @param c the selected continent that will be removed from the continent list with Continent type
	 */
	public void removeContinent(Continent c) {
		for (Continent A : continents) {
			if (A.equals(c)) {
				continents.remove(A);
				break;
			}
		}
	}

	/**
	 * To get the continent list
	 * @return the continent list with ArrayList type
	 */
	public ArrayList<Continent> getContinents() {
		return continents;
	}

	/**
	 * Add a card to the hand card list
	 * @param c the card that will be added to the hand card list with Card type
	 */
	public void addToCards(Card c) {
		cards.add(c);
	}

	/**
	 *Remove a card from the hand card list
	 * @param c the selected card that will be removed from the hand card list with Card type
	 */
	public void removeFromCards(Card c) {
		for (Card A : cards) {
			if (A.equals(c)) {
				cards.remove(A);
				break;
			}
		}
	}

	/**
	 * To get the cards list
	 * @return the cards list with ArrayList type
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}

	/**
	 * To add the card to the deck
	 * @param c the card will be added to the deck with Card type
	 */
	public void addToDeck(Card c) {
		deck.add(c);
	}

	/**
	 * To remove the card from the deck
	 * @param c the selected card will be remove from the deck with Card type
	 */
	public void removeFromDeck(Card c) {
		for (Card A : deck) {
			if (A.equals(c)) {
				deck.remove(A);
				break;
			}
		}
	}

	/**
	 * To get deck
	 * @return the deck with ArrayList type
	 */
	public ArrayList<Card> getDeck() {
		return deck;
	}

	/**
	 * Method to start the game
	 * @param players the group of players who will participate in the game with Group type
	 */
	public void startGame(Group players) {

		int count = players.getPlayers().size();
		/*
		 * Creating a card for every country, giving it a country and a star
		 * amount from 1 - 3
		 */
		for (Continent con : this.continents) {
			for (Country cou : con.getCountries()) {
				Card tempCard = new Card(cou, new Infantry().getStrength());
				this.deck.add(tempCard);
				// count++;
			}
		}

		/*
		 * Shuffling the deck and then giving each player a card unit there are
		 * no cards left.
		 */
		this.shuffleDeck();
		for (int i = 0; i < this.deck.size(); i++) {
			players.getPlayers().get(i % players.getPlayers().size()).getHand().add(this.deck.get(i));
		}

		/*
		 * Iterating through the player's hands and assigning them the country
		 * Still need to add giving the troops equal to the number of stars to
		 * that country.
		 */
		for (Player p : players.getPlayers()) {
			for (Card c : p.getHand()) {
				c.getCountry().setPlayer(p);
				c.getCountry().addInfrantry(c.getNumStars());
				p.addInfrantry(c.getNumStars());
				p.addCountry(c.getCountry());
			}
			p.getHand().clear(); // putting it back in the deck
		}
	}


	/**
	 * To check if the player owns the world
	 * @param p the selected player who will be checked if he owns the world with Playe type
	 * @return true if the player owns the world otherwise return false
	 */
	public boolean checkIfWorldOwned(Player p) {
		for (Continent con : this.continents) {
			for (Country cou : con.getCountries()) {
				if (cou.getPlayer() == p) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * To shuffle the deck
	 */
	public void shuffleDeck() {
		long seed = System.nanoTime(); // shuffling
		Collections.shuffle(this.deck, new Random(seed));
	}

	/**
	 * To get map loader
	 * @return map loader with Maploader type
	 */
	public MapLoader getMapLoader() {
		return mapLoader;
	}

	/**
	 * To set mapLoader
	 * @param mapLoader the selected maploader will be set with MapLoader type
	 */
	public void setMapLoader(MapLoader mapLoader) {
		this.mapLoader = mapLoader;
	}

}
