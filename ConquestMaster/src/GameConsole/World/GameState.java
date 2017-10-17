package GameConsole.World;

import GameConsole.Player.Group;
import GameConsole.Player.Player;
import GameConsole.Window.WindowMain;
/**
 * Set up basic states of the game
 * Current phase, current player
 *
 */
public class GameState {
	public int firstRound = 1;
	private Player currPlayer;
	private Group allPlayers;
	private int currPhase; // 0 beggining, 1 attack, 2 move
	private World world;
	private Country country1;
	private Country country2;
	private WindowMain win;

	/**
	 * constructor method with incoming parameters
	 * @param win Window frame of the game
	 * @param path map path on hard driver
	 * @throws Exception
	 */
	public GameState(WindowMain win, String path) throws Exception {
		this.win = win;
		this.currPlayer = null; // will set this at the beggining of the turn
		this.allPlayers = new Group();
		this.currPhase = 0;
		this.world = new World(path);
	}

	/**
	 * To get window
	 * @return window with WindowMain type
	 */
	public WindowMain getWindow() {
		return this.win;
	}

	public String toString() {
		String retString = "Players playing: \n";
		for (Player p : this.allPlayers.getPlayers()) {
			retString += p.getName() + "\n";
		}
		return retString + "\nWorld Info:\n" + world.toString(); 
	}
	/**
	 * will start the game setting player 1 as the current player
	 */
	public void gameStart() {
		this.currPhase = 0;
		this.currPlayer = this.allPlayers.getPlayers().get(0);
		this.world.startGame(this.allPlayers);
		this.updateCountryLabels();
	}

	/**
	 * According to the selected map, update the country labels
	 */
	public void updateCountryLabels() {
		for (Continent con : this.world.getContinents()) {
			for (Country cou : con.getCountries()) {
				System.out.println(cou.getName());
				System.out.println(cou.getButton().getClass());
				System.out.println(cou.getButton().getLabel());
				System.out.println(cou.getPlayer());
				cou.getButton().updateLabel(cou.getPlayer());
			}
		}
	}
	/**
	 * method to get the current player.
	 * @return current player with Player type
	 */
	public Player getCurrPlayer() {
		return this.currPlayer;
	}
	/**
	 * method to get all the players
	 * @return all players with Group type
	 */
	public Group getAllPlayers() {
		return this.allPlayers;
	}
	/**
	 * method to get current phase
	 * @return current phase with int type
	 */
	public int getCurrPhase() {
		return this.currPhase;
	}

	/**
	 * To get world the players are in
	 * @return the world the players are in with World type
	 */
	public World getWorld() {
		return this.world;
	}

	/**
	 * To set current player
	 * @param p1 the player that will be set as the current player with Player type
	 */
	public void setCurrPlayer(Player p1) {
		this.currPlayer = p1;
	}

	/**
	 * To add a player to the player group
	 * @param p1 a player that will be added to the player group with Player type
	 */
	public void addPlayer(Player p1) {
		this.allPlayers.addPlayer(p1);
	}

	/**
	 * To set the current game phase
	 * @param p the phase that will be set with int type
	 */
	public void setCurrPhase(int p) {
		this.currPhase = p;
	}

	/**
	 * To check is there a player a winner
	 * @return the player if the player is winner otherwise return null
	 */
	public Player checkWinner() {
		boolean won = false;
		if (this.allPlayers.getPlayers().size() == 1) { // if there is only one
			// player they already
			// win
			return this.allPlayers.getPlayers().get(0);
		}
		for (Player p : this.allPlayers.getPlayers()) {
			won = this.world.checkIfWorldOwned(p);
			if (won) {
				return p;
			}
		}
		return null;
	}

	/**
	 * To set the next player after the current player
	 */
	public void setNextPlayer() {
		int currPlayerIndex = this.getAllPlayers().getPlayers().indexOf(this.currPlayer);
		this.currPlayer = this.getAllPlayers().getPlayers()
				.get((currPlayerIndex + 1) % this.getAllPlayers().getPlayers().size());
	}


	/**
	 * To get a country
	 * @return country with Country type
	 */
	public Country getCountry1() {
		return country1;
	}

	/**
	 * To set a country
	 * @param country1 the desired country that will be set
	 */
	public void setCountry1(Country country1) {
		this.country1 = country1;
	}

	/**
	 * To get a country
	 * @return country with Country type
	 */
	public Country getCountry2() {
		return country2;
	}

	/**
	 * To set a country
	 * @param country2 the desired country that will be set with Country type
	 */
	public void setCountry2(Country country2) {
		this.country2 = country2;
	}


	/**
	 * To set a world
	 * @param world the desired world that will be set with World type
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	/**
	 * To set all players
	 * @param allPlayers a desired group of players that need to be set
	 */
	public void setAllPlayers(Group allPlayers) {
		this.allPlayers = allPlayers;
	}

}

